/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.stock.constant.CargoClassifyStatus;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.store.constant.GoodStatus;
import com.club.web.store.dao.base.TradeGoodMapper;
import com.club.web.store.dao.base.po.TradeGood;
import com.club.web.store.dao.extend.GoodsColumnExtendMapper;
import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.TradeGoodDo;
import com.club.web.store.domain.repository.GoodRepository;
import com.club.web.store.domain.repository.GoodSKURepository;
import com.club.web.store.domain.repository.GoodsBaseLabelRepository;
import com.club.web.store.domain.repository.GoodsColumnRepository;
import com.club.web.store.domain.repository.StoreLevelRepository;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.TradeGoodVo;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
@Repository
public class GoodReposityImpl implements GoodRepository{

	@Autowired
	TradeGoodExtendMapper  tradeGoodMapper;
	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodRepository#addGood(com.club.web.store.domain.TradeGoodDo)
	 */
	@Autowired
	GoodsColumnRepository goodsColumnRepository;
	@Autowired
	GoodsBaseLabelRepository goodsBaseLabelRepository;
	@Autowired
	GoodSKURepository   goodSkuRepository;
	@Autowired
	private StoreLevelRepository storeLevelRepository;
	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	
	@Override
	/**
	 * 添加商品
	 */
	public void addGood(TradeGoodDo tradeGoodDo) {
		// TODO Auto-generated method stub
		TradeGood tradeGood = new TradeGood();
		BeanUtils.copyProperties(tradeGoodDo, tradeGood);
		tradeGoodMapper.insert(tradeGood);
	}
	/**
	 * 更新商品信息
	 */
	public void updateTradeGood(TradeGoodDo tradeGoodDo){
		TradeGood tradeGood = new TradeGood();
		BeanUtils.copyProperties(tradeGoodDo, tradeGood);
		tradeGoodMapper.updateByPrimaryKey(tradeGood);
	}
	
	/**
	 * 根据商品id删除商品
	 */
	public void deleteTradeGood(long id){
		tradeGoodMapper.deleteByPrimaryKey(id);
	}
	
	
	public List<GoodVo> queryGoodPage(Page<Map<String, Object>> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("conditions", page.getConditons().get("conditions").toString());
		map.put("conditions1", page.getConditons().get("conditions1").toString());
		if(page.getConditons().get("statusConditions")!=null&&Long.parseLong(page.getConditons().get("statusConditions").toString())>-1){
			map.put("statusConditions", page.getConditons().get("statusConditions").toString());
		}
		if(page.getConditons().get("goodColumnId")!=null&& !page.getConditons().get("goodColumnId").equals("")){
			map.put("goodColumnId", page.getConditons().get("goodColumnId").toString());
		}
		if(page.getConditons().get("cargoBrandId")!=null&& !page.getConditons().get("cargoBrandId").equals("")){
			map.put("cargoBrandId", page.getConditons().get("cargoBrandId").toString());
		}
		if(page.getConditons().get("cargoClassifyId")!=null&& !page.getConditons().get("cargoClassifyId").equals("")){
			List<Long> classifyIds = cargoClassifyRepository.
					getAllIdsByIdAndStatus(Long.valueOf(page.getConditons().get("cargoClassifyId").toString()), CargoClassifyStatus.启用.getDbData());
			if(classifyIds!=null && classifyIds.size()>0)
			map.put("classifyIds",classifyIds);
		}
	
			
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		List<GoodVo> list = tradeGoodMapper.queryGoodPage(map);
		for (GoodVo goodVo : list) {
			goodVo.setStatusName(GoodStatus.getTextByDbData(goodVo.getStatus()));
			
		}
		return tradeGoodMapper.queryGoodPage(map);
		
	};
	
	public Long queryGoodCountPage(Page<Map<String, Object>> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("conditions", page.getConditons().get("conditions").toString());
		map.put("conditions1", page.getConditons().get("conditions1").toString());
		if(page.getConditons().get("statusConditions")!=null&&Long.parseLong(page.getConditons().get("statusConditions").toString())>-1){
			map.put("statusConditions", page.getConditons().get("statusConditions").toString());
		}
		if(page.getConditons().get("goodColumnId")!=null&&!page.getConditons().get("goodColumnId").equals("")){
			map.put("goodColumnId", page.getConditons().get("goodColumnId").toString());
		}
		if(page.getConditons().get("cargoBrandId")!=null&&!page.getConditons().get("cargoBrandId").equals("")){
			map.put("cargoBrandId", page.getConditons().get("cargoBrandId").toString());
		}
		if(page.getConditons().get("cargoClassifyId")!=null&&!page.getConditons().get("cargoClassifyId").equals("")){
			List<Long> classifyIds = cargoClassifyRepository.
					getAllIdsByIdAndStatus(Long.valueOf(page.getConditons().get("cargoClassifyId").toString()), CargoClassifyStatus.启用.getDbData());
			map.put("classifyIds",classifyIds);
		}
		return tradeGoodMapper.queryGoodCountPage(map);
	}
	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodRepository#updateStatus(java.util.Map)
	 */
	@Override
	public void updateStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		tradeGoodMapper.updateStatus(map);
	}
	@Override
	public Long ifGoodExgist(long cargoId) {
		// TODO Auto-generated method stub
		return tradeGoodMapper.selectGoodNumsByCargoId(cargoId);
	}
     
	@Override
	public List<GoodListVo> queryGoodList(Map<String,Object> map){
		
		return tradeGoodMapper.queryGoodList(map);
	}
	
	@Override
	public TradeGoodVo queryTradeGoodInfo(long goodId){
		TradeGoodVo tradeGoodVo = new TradeGoodVo();
		TradeGood tradeGood = tradeGoodMapper.selectByPrimaryKey(goodId);
		if(tradeGood != null){
			BeanUtils.copyProperties(tradeGood, tradeGoodVo);			
		}
		
		return tradeGoodVo;
	}
	
	@Override
	public List<GoodVo> selectGoodIdByCategory(Long category){
		return tradeGoodMapper.selectGoodByCategory(category);
	}
	
	public void updateSaleNumById(List<GoodUpDo> list) {
		// TODO Auto-generated method stub
		for (GoodUpDo goodUpDo : list) {
			tradeGoodMapper.updateSaleNumById(goodUpDo);
		}
	}
}
