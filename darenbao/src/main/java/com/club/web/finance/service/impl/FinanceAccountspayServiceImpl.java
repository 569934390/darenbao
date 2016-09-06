package com.club.web.finance.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.finance.domain.FinanceAccountspayDo;
import com.club.web.finance.domain.repository.FinanceAccountspayRepository;
import com.club.web.finance.service.FinanceAccountspayService;
import com.club.web.finance.vo.FinanceAccountspayVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.DateParseUtil;
import com.club.web.util.IdGenerator;

@Service("financeAccountspayService")
public class FinanceAccountspayServiceImpl implements FinanceAccountspayService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FinanceAccountspayRepository financeAccountspayRepository;

	/**
	 * 保存更新收款单
	 */
	@Override
	public Map<String, Object> saveOrUpdate(FinanceAccountspayVo financeAccountspayVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (financeAccountspayVo == null) {
			result.put("success", false);
			result.put("msg", "收款单不能为空！");
			return result;
		}
		if (financeAccountspayVo.getId() == null || "".equals(financeAccountspayVo.getId())) {
			financeAccountspayVo.setId(IdGenerator.getDefault().nextId() + "");
			financeAccountspayVo.setCode("skd" + DateParseUtil.formatDate(new Date(), "yyyyMMddHHmmssS"));
			financeAccountspayVo.setCreateTime(new Date());
			financeAccountspayVo.setUpdateTime(new Date());
			FinanceAccountspayDo financeAccountspayDo = financeAccountspayRepository.create(financeAccountspayVo);
			financeAccountspayDo.save();
		} else {
			FinanceAccountspayDo financeAccountspayDo = financeAccountspayRepository
					.load(Long.parseLong(financeAccountspayVo.getId()));
			financeAccountspayDo.setU8accountsid(financeAccountspayVo.getU8accountsid());
			financeAccountspayDo.setU8orderid(financeAccountspayVo.getU8orderid());
			financeAccountspayDo.setStatue(financeAccountspayVo.getStatue());
			financeAccountspayDo.setUpdateTime(new Date());
			financeAccountspayDo.update();
		}
		result.put("success", true);
		return result;
	}

	@Override
	public FinanceAccountspayVo getVoByOrderId(String orderId) {
		return financeAccountspayRepository.getVoByOrderId(orderId);
	}

	public FinanceAccountspayDo getVoById(String fanceId) {
		return financeAccountspayRepository.load(Long.parseLong(fanceId));
	}

	@Override
	public Map<String, Object> updateByU8Account(String fanceId, String yongYouAccountId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (fanceId == null || "".equals(fanceId)) {
			result.put("success", false);
			result.put("msg", "中国茶商收款单号不能为空！");
			return result;
		}
		if (yongYouAccountId == null || "".equals(yongYouAccountId)) {
			result.put("success", false);
			result.put("msg", "U8收款单号不能为空！");
			return result;
		}
		FinanceAccountspayDo accountspayDo = getVoById(fanceId);
		if (accountspayDo == null) {
			result.put("success", false);
			result.put("msg", "中国茶商收款单不存在！");
			return result;
		}
		accountspayDo.setU8accountsid(yongYouAccountId);
		accountspayDo.setUpdateTime(new Date());
		accountspayDo.update();
		result.put("success", true);
		result.put("msg", "更新成功！");
		return result;
	}

	@Override
	public Map<String, Object> updateByU8Order(String fanceId, String yongYouOrderId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (fanceId == null || "".equals(fanceId)) {
			result.put("success", false);
			result.put("msg", "中国茶商收款单号不能为空！");
			return result;
		}
		if (yongYouOrderId == null || "".equals(yongYouOrderId)) {
			result.put("success", false);
			result.put("msg", "U8销售单号不能为空！");
			return result;
		}
		FinanceAccountspayDo accountspayDo = getVoById(fanceId);
		if (accountspayDo == null) {
			result.put("success", false);
			result.put("msg", "中国茶商收款单不存在！");
			return result;
		}
		accountspayDo.setU8orderid(yongYouOrderId);
		accountspayDo.setUpdateTime(new Date());
		accountspayDo.update();
		result.put("success", true);
		result.put("msg", "更新成功！");
		return result;
	}

	@Override
	public List<FinanceAccountspayVo> getVoByU8Missing(String u8Type) {
		// TODO Auto-generated method stub
		if(u8Type=="order"){
			return financeAccountspayRepository.selectByU8OrderNull();
		}else{
			return financeAccountspayRepository.selectByU8AccountsNull();
		}
	}

	@Override
	public Map<String, Object> updateByOrderId(String orderId, Integer statue) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (orderId == null || "".equals(orderId)) {
			result.put("success", false);
			result.put("msg", "中国茶商订单号单号不能为空！");
			return result;
		}
		if (statue == null) {
			result.put("success", false);
			result.put("msg", "修改状态不能为空！");
			return result;
		}
		FinanceAccountspayVo financeAccountspayVo= getVoByOrderId(orderId);
		if(financeAccountspayVo==null){
			result.put("success", false);
			result.put("msg", "中国茶商该收款单不存在！");
			return result;
		}
		financeAccountspayVo.setStatue(statue);
		result.putAll(saveOrUpdate(financeAccountspayVo));
		return result;
	}

}
