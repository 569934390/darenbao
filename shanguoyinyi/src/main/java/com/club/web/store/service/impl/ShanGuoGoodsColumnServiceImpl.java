package com.club.web.store.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.store.domain.GoodsColumnDo;
import com.club.web.store.domain.RuleValueDo;
import com.club.web.store.domain.repository.GoodsColumnRepository;
import com.club.web.store.domain.repository.RuleSourceRepository;
import com.club.web.store.domain.repository.RuleValueRepository;
import com.club.web.store.listener.GoodsColumnListenerManager;
import com.club.web.store.service.ShanGuoGoodsColumnService;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.util.IdGenerator;

/**   
* @Title: GoodsColumnServiceImpl.java
* @Package com.club.web.store.service.impl 
* @Description: 商品基础栏目Service接口实现类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
@Service("shanGuoGoodsColumnService")
public class ShanGuoGoodsColumnServiceImpl implements ShanGuoGoodsColumnService {
	
	private Logger logger = LoggerFactory.getLogger(ShanGuoGoodsColumnServiceImpl.class);
	
	@Autowired
	GoodsColumnRepository goodsColumnRepository;
	
	@Autowired
	private TradeHeadStoreService tradeHeadStoreService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private RuleSourceRepository ruleSourceRepository;
	
	@Autowired
	private RuleValueRepository ruleValueRepository;
	
	@Autowired
	private GoodsColumnListenerManager goodsColumnListenerManager;
	
	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	
	@Override
	public Map<String, Object> addGoodsColumn(GoodsColumnVo goodsColumnVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsColumnDo goodsColumnDo = goodsColumnRepository.voChangeDo(goodsColumnVo);
		//判断是否是总店
		if(goodsColumnVo != null && "Y".equals(goodsColumnVo.getShopFlag())){
			Long shopId = getShopId(request);
			goodsColumnDo.setShopId(shopId);				
		}
		// 如果传过来的图片不为空。则保存记录
		if(goodsColumnVo != null){
			if (goodsColumnVo.getShowpicture() != null && !"".equals(goodsColumnVo.getShowpicture())) {
				try {
					ImageVo imageVo = imageService.saveImage(goodsColumnVo.getShowpicture());
					goodsColumnDo.setShowpicture(imageVo.getId() + "");					
				} catch (Exception e) {
					logger.error("新增图片异常:", e);
				}
			}	
		}
		
		/* 新增规则项，默认无规则时也创建一个空的规则，方便以后规则来源的变更  */
		try {
			RuleValueDo ruleValueDo = new RuleValueDo();
			if(goodsColumnVo.getRuleId() != null && !goodsColumnVo.getRuleId().isEmpty()){
				ruleValueDo.setRuleId(Long.valueOf(goodsColumnVo.getRuleId()));
				ruleValueDo.setRuleVal(goodsColumnVo.getRuleVal());
			} else{
				ruleValueDo.setRuleId(null);
				ruleValueDo.setRuleVal(null);
			}
			if(goodsColumnVo.getRuleId2() != null && !goodsColumnVo.getRuleId2().isEmpty()){
				ruleValueDo.setRuleId2(Long.valueOf(goodsColumnVo.getRuleId2()));
				ruleValueDo.setRuleStarttime(goodsColumnVo.getRuleStarttime());
				ruleValueDo.setRuleEndtime(goodsColumnVo.getRuleEndtime());
			} else{
				ruleValueDo.setRuleId2(null);
				ruleValueDo.setRuleStarttime(null);
				ruleValueDo.setRuleEndtime(null);
			}
			ruleValueDo.setId(IdGenerator.getDefault().nextId());
			goodsColumnDo.setRuleSourceId(ruleValueDo.getId());
			ruleValueRepository.addRuleValue(ruleValueDo);			
		} catch (Exception e) {
			logger.error("新增规格值异常:", e);
		}
		
		try {
			result.put("success", true);
			result.put("msg", "新增商品栏目成功");
			goodsColumnDo.setStatus(1);
			goodsColumnDo.insert();
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("新增商品栏目异常:", e);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> editGoodsColumn(GoodsColumnVo goodsColumnVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsColumnDo goodsColumnDo = goodsColumnRepository.voChangeDo(goodsColumnVo);
		//修改规则值
		if(goodsColumnVo.getValueId() != null && !goodsColumnVo.getValueId().isEmpty()){
			try {
				RuleValueDo ruleValueDo = new RuleValueDo();
				ruleValueDo.setId(Long.valueOf(goodsColumnVo.getValueId()));
				ruleValueDo.setRuleVal(goodsColumnVo.getRuleVal());
				ruleValueDo.setRuleStarttime(goodsColumnVo.getRuleStarttime());
				ruleValueDo.setRuleEndtime(goodsColumnVo.getRuleEndtime());
				if(goodsColumnVo.getRuleId() != null && !goodsColumnVo.getRuleId().isEmpty()){
					ruleValueDo.setRuleId(Long.valueOf(goodsColumnVo.getRuleId()));				
				} else{
					ruleValueDo.setRuleId(null);
					ruleValueDo.setRuleVal(null);
				}
				if(goodsColumnVo.getRuleId2() != null && !goodsColumnVo.getRuleId2().isEmpty()){
					ruleValueDo.setRuleId2(Long.valueOf(goodsColumnVo.getRuleId2()));				
				} else{
					ruleValueDo.setRuleId2(null);
					ruleValueDo.setRuleStarttime(null);
					ruleValueDo.setRuleEndtime(null);
				}
				ruleValueDo.update();			
			} catch (Exception e) {
				logger.error("修改规格值异常:", e);
			}
		}
		
		if(goodsColumnVo.getShowpictureId() != null && !goodsColumnVo.getShowpictureId().isEmpty()){
			try {
				ImageVo imageVo = new ImageVo();
				imageVo.setId(Long.valueOf(goodsColumnVo.getShowpictureId()));
				imageVo.setPicUrl(goodsColumnVo.getShowpicture());
				imageService.updateImage(imageVo);
				goodsColumnDo.setShowpicture(imageVo.getId() + "");
			} catch (Exception e) {
				logger.error("修改图片异常:", e);
			}
		} else{
			if (goodsColumnVo.getShowpicture() != null && !"".equals(goodsColumnVo.getShowpicture())) {
				try {
					ImageVo imageVo = imageService.saveImage(goodsColumnVo.getShowpicture());
					goodsColumnDo.setShowpicture(imageVo.getId() + "");					
				} catch (Exception e) {
					logger.error("新增图片异常:", e);
				}
			}
		}
		
		try {
			result.put("success", true);
			result.put("msg", "编辑商品栏目成功");
			goodsColumnDo.update();
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("编辑商品栏目异常:", e);
		}
		
		return result;
	}
	
	/**
	 * 获取店铺ID
	 * @param request
	 * @return 店铺ID
	 */
	public Long getShopId(HttpServletRequest request){
		Long shopId = null;
		try {
			shopId = tradeHeadStoreService.getStaffHeadStoreId(request);			
		} catch (Exception e) {
			logger.error("查询商品栏目笔数异常:", e);
		}
		
		return shopId;
	}
}