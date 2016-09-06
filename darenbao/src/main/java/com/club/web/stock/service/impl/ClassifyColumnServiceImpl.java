package com.club.web.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.ListUtils;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.stock.dao.base.ClassifyColumnMapper;
import com.club.web.stock.dao.base.po.ClassifyColumn;
import com.club.web.stock.service.ClassifyColumnService;
import com.club.web.stock.vo.ClassifyColumnVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * Cargo Service
 * 
 * @author yunpeng.xiong
 *
 */
@Service
public class ClassifyColumnServiceImpl implements ClassifyColumnService {

	@Autowired
	private ClassifyColumnMapper repository;
	
	@Override
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page) throws Exception {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		Map<String, Object> con = page.getConditons();
		List<ClassifyColumnVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			total = repository.queryTotalByMap(con);
			page.setTotalRecords(total);
			if (total > 0) {
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = repository.queryPoByMap(con);
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}

	@Override
	public List<ClassifyColumnVo> findListByIds(String ids) throws Exception {
		List<ClassifyColumnVo> list = new ArrayList<>();
		if(StringUtils.isNotEmpty(ids)){
			Map<String, Object> con = new HashMap<>();
			con.put("ids",ListUtils.strToLongList(ids));
			list = repository.queryPoByMap(con);
		}
		return list;
	}

	@Override
	public Map<String, Object> saveOrUpdate(ClassifyColumnVo classifyColumn) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		if(classifyColumn == null){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "无更新数据!");
			return result;
		}if(StringUtils.isEmpty(classifyColumn.getClassifyId())){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "请选择分类!");
			return result;
		}if(StringUtils.isEmpty(classifyColumn.getImgUrl())){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "图片不能为空!");
			return result;
		}
		ClassifyColumn po = getDomainByVo(classifyColumn);
		if(po.getId() == null){
			po.setId(IdGenerator.getDefault().nextId());
			po.add();
		}else{
			po.modify();
		}
		return result;
	}

	@Override
	public Map<String, Object> delete(String ids) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		if(StringUtils.isEmpty(ids)){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "请选择数据!");
			return result;
		}
		repository.deleteByList(ListUtils.strToLongList(ids));
		return result;
	}
	
	private ClassifyColumn getDomainByVo(ClassifyColumnVo src) {
		ClassifyColumn target = null;
		if (src != null) {
			target = new ClassifyColumn();
			target.setId(StringUtils.isEmpty(src.getId())? null : Long.valueOf(src.getId()));
			target.setName(src.getName());
			target.setClassifyId(StringUtils.isEmpty(src.getClassifyId())? null : Long.valueOf(src.getClassifyId()));
			target.setOrderIndex(src.getOrderIndex() == null ? 0 : src.getOrderIndex());
			target.setImgUrl(src.getImgUrl());
		}
		return target;
	}

	@Override
	public List<ClassifyColumnVo> list() throws Exception {
		Map<String, Object> con = new HashMap<>();
		con.put("classifyStatus", 1);
		return repository.queryPoByMap(con);
	}
}
