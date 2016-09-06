package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.store.dao.base.po.SalesReturnReason;
import com.club.web.store.dao.extend.SalesReturnReasonExtendMapper;
import com.club.web.store.domain.SalesReturnReasonDo;
import com.club.web.store.service.SalesReturnReasonService;
import com.club.web.store.vo.SalesReturnReasonVo;
@Service("salesReturnReasonService")
public class SalesReturnReasonServiceImpl implements SalesReturnReasonService{
@Autowired
SalesReturnReasonExtendMapper salesReturnReasonExtendMapper;
	@Override
	public Map<String, Object> saveOrUpdateSalesReturnReason(SalesReturnReasonVo salesReturnReasonVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count=salesReturnReasonExtendMapper.selectByPageCount(salesReturnReasonVo.getReason());
		
		SalesReturnReasonDo salesReturnReasonDo=new SalesReturnReasonDo();
		BeanUtils.copyProperties(salesReturnReasonVo, salesReturnReasonDo);
		if (!StringUtils.isEmpty(salesReturnReasonVo.getId())) {
			salesReturnReasonDo.setId(Long.parseLong(salesReturnReasonVo.getId()));
			salesReturnReasonDo.update();
		}else{
			if (count>0) {
				result.put("success", false);
				result.put("msg", "该原因已有记录");
				return result;
			}
			salesReturnReasonDo.insert();
		}
		result.put("success", true);
		result.put("msg", "保存成功");
		return result;
	}

	@Override
	public Page getSalesReturnReason(Page page) {
		int total=0;
		List<SalesReturnReasonVo> salesReturnReasonVoList=new ArrayList<SalesReturnReasonVo>();
		SalesReturnReasonVo salesReturnReasonVo=null;
		total=salesReturnReasonExtendMapper.selectByPageCount(null);
		page.setTotalRecords(total);
		List<SalesReturnReason> salesReturnReasonList= salesReturnReasonExtendMapper.selectByPage(page.getStart(),page.getLimit());
		if (total>0&&salesReturnReasonList.size()>0) {
			for (SalesReturnReason salesReturnReason:salesReturnReasonList) {
				salesReturnReasonVo=new SalesReturnReasonVo();
				BeanUtils.copyProperties(salesReturnReason, salesReturnReasonVo);
				if (!StringUtils.isEmpty(salesReturnReason.getId())) {
					salesReturnReasonVo.setId(salesReturnReason.getId()+"");
					salesReturnReasonVoList.add(salesReturnReasonVo);
				}
			}
		}
		page.setResultList(salesReturnReasonVoList);
		return page;
	}

	@Override
	public Map<String, Object> deletSalesReturnReason(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idList=ids.split(",");
		SalesReturnReasonDo salesReturnReasonDo=null;
		for (String id:idList) {
			if (!StringUtils.isEmpty(id)) {
				try {
					salesReturnReasonDo=new SalesReturnReasonDo();
					salesReturnReasonDo.setId(Long.parseLong(id));
					salesReturnReasonDo.delet();
					result.put("success", true);
					result.put("msg", "保存成功");
				} catch (Exception e) {
					e.printStackTrace();
					result.put("success", false);
					result.put("msg", e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public List<SalesReturnReasonVo> findAll() {
		return salesReturnReasonExtendMapper.findAll();
	}

}
