package com.club.web.store.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.web.store.service.StoreLevelService;
import com.club.web.store.service.StoreSupplyPriceService;
import com.club.web.store.vo.StoreLevelVo;
import com.club.web.store.vo.StoreSupplyPriceVo;

@Controller
@RequestMapping("/StoreSupplyPrice")
public class StoreLevelSupplyController {
	private Logger logger = LoggerFactory.getLogger(StoreLevelSupplyController .class);

	@Autowired
	private StoreSupplyPriceService storeSupplyPriceService;
	
	/**
	 * 获取当前用户绑定总店有效的店铺等级
	 */
	@RequestMapping("/findSupplyPrice")
	@ResponseBody
	public List<StoreSupplyPriceVo> findAllStoreLevel(String  goodId) {
		List<StoreSupplyPriceVo> levelVoList=null;
		try {
			if(goodId != null && !goodId.equals("")){
				levelVoList=storeSupplyPriceService.findByGoodId(Long.parseLong(goodId));
			}
			
		} catch (Exception e) {
			logger.error("获取当前用户所有供货价格和店铺名称", e);
		}
		return levelVoList;
	}
	
}
