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
import com.club.web.store.dao.base.po.TradeEexpressage;
import com.club.web.store.dao.extend.TradeEexpressageExtendMapper;
import com.club.web.store.domain.TradeEexpressageDo;
import com.club.web.store.service.TradeEexpressageService;
import com.club.web.store.vo.TradeEexpressageVo;

@Service("tradeEexpressageService")
public class TradeEexpressageServiceImpl implements TradeEexpressageService {

	
	@Autowired
	TradeEexpressageExtendMapper tradeEexpressageExtendMapper;

	@Override
	public Map<String, Object> saveOrUpdateExpressage(TradeEexpressageVo tradeEexpressageVo) {
		TradeEexpressageDo tradeEexpressageDo = new TradeEexpressageDo();
		Map<String, Object> result = new HashMap<String, Object>();
		int total = tradeEexpressageExtendMapper.selectByPageCount(2,tradeEexpressageVo.getName());

		BeanUtils.copyProperties(tradeEexpressageVo, tradeEexpressageDo);
		if (tradeEexpressageVo != null && !StringUtils.isEmpty(tradeEexpressageVo.getCreator())) {
			tradeEexpressageDo.setCreator(Long.parseLong(tradeEexpressageVo.getCreator()));
		}
		if (tradeEexpressageVo != null && !StringUtils.isEmpty(tradeEexpressageVo.getId())) {
			tradeEexpressageDo.setId(Long.parseLong(tradeEexpressageVo.getId()));
			tradeEexpressageDo.update();
			result.put("msg", "修改成功");
			result.put("success", true);
		} else {
			if (total == 0) {
				tradeEexpressageDo.insert();
				result.put("success", true);
				result.put("msg", "添加成功");
			} else {
				result.put("success", false);
				result.put("msg", "插入失败，快递公司已存在");
			}
		}

		

		return result;
	}

	@Override
	public Page getExpressageList(Page page) {
		int total = tradeEexpressageExtendMapper.selectByPageCount(2,null);
		List<TradeEexpressage> TradeExpressageList = new ArrayList<>();
		TradeEexpressageVo tradeEexpressageVo = null;
		TradeExpressageList = tradeEexpressageExtendMapper.selectByPage(2, page.getStart(), page.getLimit());

		page.setTotalRecords(total);
		List<TradeEexpressageVo> tradeEexpressageVoList = new ArrayList<>();
		if (TradeExpressageList.size() > 0) {
			for (TradeEexpressage tradeEexpressage : TradeExpressageList) {
				tradeEexpressageVo = new TradeEexpressageVo();
				BeanUtils.copyProperties(tradeEexpressage, tradeEexpressageVo);
				tradeEexpressageVo.setId(tradeEexpressage.getId() + "");
				tradeEexpressageVo.setCreator(tradeEexpressage.getCreator() + "");
				tradeEexpressageVoList.add(tradeEexpressageVo);
			}

		}
		page.setPage(total / (page.getLimit() - page.getStart()));
		page.setResultList(tradeEexpressageVoList);
		return page;
	}
	@Override
	public List<TradeEexpressageVo> getExpressageUseList(Page page) {
		
		List<TradeEexpressage> TradeExpressageList = new ArrayList<>();
		TradeEexpressageVo tradeEexpressageVo = null;
		TradeExpressageList = tradeEexpressageExtendMapper.selectByPage(0, page.getStart(), page.getLimit());
		
		List<TradeEexpressageVo> tradeEexpressageVoList = new ArrayList<>();
		if (TradeExpressageList.size() > 0) {
			for (TradeEexpressage tradeEexpressage : TradeExpressageList) {
				tradeEexpressageVo = new TradeEexpressageVo();
				BeanUtils.copyProperties(tradeEexpressage, tradeEexpressageVo);
				tradeEexpressageVo.setId(tradeEexpressage.getId() + "");
				tradeEexpressageVo.setCreator(tradeEexpressage.getCreator() + "");
				tradeEexpressageVoList.add(tradeEexpressageVo);
			}
		}
		
		return tradeEexpressageVoList;
	}

	@Override
	public int deleteExpressage(String[] expressageIds) {
		TradeEexpressageDo tradeEexpressageDo = new TradeEexpressageDo();
		int result = 0;
		try {
			for (String expressageId : expressageIds) {
				if (!StringUtils.isEmpty(expressageId)) {
					tradeEexpressageDo.setId(Long.parseLong(expressageId));
					tradeEexpressageDo.delet();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateExpressageState(String[] expressageIds, String action) {
		TradeEexpressageDo tradeEexpressageDo =null;
		int result = 0;
		try {
			if (action != null) {
				for (String expressageId : expressageIds) {
					if (!StringUtils.isEmpty(expressageId)) {
						tradeEexpressageDo = new TradeEexpressageDo();
						tradeEexpressageDo.setId(Long.parseLong(expressageId));
						tradeEexpressageDo.setState(Integer.parseInt(action));
						tradeEexpressageDo.updateState();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

}
