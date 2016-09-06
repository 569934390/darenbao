package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.web.store.domain.GoodsBaseLabelDo;
import com.club.web.store.domain.repository.GoodsBaseLabelRepository;
import com.club.web.store.service.GoodsBaseLabelService;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.util.CommonUtil;

/**   
* @Title: GoodsBaseLabelServiceImpl.java
* @Package com.club.web.store.service.impl 
* @Description: 商品基础标签Service接口实现类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
@Service("goodsBaseLabelService")
public class GoodsBaseLabelServiceImpl implements GoodsBaseLabelService {
	@Autowired
	GoodsBaseLabelRepository goodsBaseLabelRepository;
	
	@Autowired
	private TradeHeadStoreService tradeHeadStoreService;

	@Override
	public Page<Map<String, Object>> selectGoodsBaseLabelByLabelName(Page<Map<String, Object>> page, 
			String labelName, String shopYn, HttpServletRequest request) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		if(labelName.isEmpty()){
			labelName = null;			
		} else{
			labelName = "%" + labelName +"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if("Y".equals(shopYn)){
			Long shopId = tradeHeadStoreService.getStaffHeadStoreId(request);
			map.put("shopYn", shopId);
		}
		map.put("labelName", labelName);
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		
		List<GoodsBaseLabelVo> list = goodsBaseLabelRepository.selectGoodsBaseLabelByLabelName(map);
		result.setResultList(CommonUtil.listObjTransToListMap(list));			
		Long count = goodsBaseLabelRepository.queryGoodsBaseLabelCountPage(map);
		result.setTotalRecords(count.intValue());
		
		return result;
	}
	
	@Override
	public Map<String, Object> addGoodsBaseLabel(GoodsBaseLabelVo goodsBaseLabelVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsBaseLabelDo goodsBaseLabelDo = goodsBaseLabelRepository.voChangeDo(goodsBaseLabelVo);
		//判断是否是总店
		if(goodsBaseLabelVo != null && "Y".equals(goodsBaseLabelVo.getShopFlag())){
			Long shopId = tradeHeadStoreService.getStaffHeadStoreId(request);			
			goodsBaseLabelDo.setShopId(shopId);				
		}
		goodsBaseLabelDo.insert();
		
		return result;
	}
	
	@Override
	public Map<String, Object> editGoodsBaseLabel(GoodsBaseLabelVo goodsBaseLabelVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsBaseLabelDo goodsBaseLabelDo = goodsBaseLabelRepository.voChangeDo(goodsBaseLabelVo);
		
		if("Y".equals(goodsBaseLabelVo.getShopFlag())){	//总店
			if(goodsBaseLabelVo.getShopId() == null || goodsBaseLabelVo.getShopId().isEmpty()){
				result.put("success", false);
				result.put("msg", "标签："+goodsBaseLabelVo.getLabelName()+":是平台的数据,总店不能修改");
			} else{
				result.put("success", true);
				result.put("msg", "编辑商品标签成功");
				goodsBaseLabelDo.update();
			}
		} else{	//平台
			if(goodsBaseLabelVo.getShopId() == null || goodsBaseLabelVo.getShopId().isEmpty()){
				result.put("success", true);
				result.put("msg", "编辑商品标签成功");
				goodsBaseLabelDo.update();
			} else{
				result.put("success", false);
				result.put("msg", "标签："+goodsBaseLabelVo.getLabelName()+":是总店的数据,平台不能修改");
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteGoodsBaseLabel(String idStr, String shopFlag) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		try {
			for (String id : ids) {
				GoodsBaseLabelVo goodsBaseLabelVo = new GoodsBaseLabelVo();
				goodsBaseLabelVo = goodsBaseLabelRepository.selectGoodsBaseLabelById(Long.valueOf(id));
				if(goodsBaseLabelVo != null){
					if("Y".equals(shopFlag)){	//总店
						if(goodsBaseLabelVo.getShopId() == null || goodsBaseLabelVo.getShopId().isEmpty()){
							result.put("success", false);
							result.put("msg", "标签："+goodsBaseLabelVo.getLabelName()+"是平台的数据，总店不能删除");
							return result;
						} else{
							result.put("success", true);
							result.put("msg", "删除商品标签成功");
							goodsBaseLabelRepository.deleteGoodsBaseLabel(Long.parseLong(id));								
						}
					} else{	//平台
						if(goodsBaseLabelVo.getShopId() == null || goodsBaseLabelVo.getShopId().isEmpty()){
							result.put("success", true);
							result.put("msg", "删除商品标签成功");
							goodsBaseLabelRepository.deleteGoodsBaseLabel(Long.parseLong(id));
						} else{
							result.put("success", false);
							result.put("msg", "标签："+goodsBaseLabelVo.getLabelName()+"是总店的数据，平台不能删除");
							return result;
						}
					}
				} else{
					result.put("success", false);
					result.put("msg", "数据不存在");
				}
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	@Override
	@Transactional
	public Map<String, Object> changeStatus(String idStr, String status) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			goodsBaseLabelRepository.updateStatusById(Long.valueOf(id), status);
		}
		
		return result;
	}
	
	@Override
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> page, 
			String labelName, String shopYn, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodsBaseLabelVo> goodsBaseLabelVoLst = new ArrayList<GoodsBaseLabelVo>();
		if("Y".equals(shopYn)){
			Long shopId = tradeHeadStoreService.getStaffHeadStoreId(request);
			map.put("shopYn", shopId);				
		}
		if(labelName.isEmpty()){
			labelName = null;			
		} else{
			labelName = "%" + labelName +"%";
		}
		map.put("labelName", labelName);
		map.put("start", 0);
		map.put("limit", 9999);
		goodsBaseLabelVoLst = goodsBaseLabelRepository.selectGoodsBaseLabelListByLabelName(map);
		
		return goodsBaseLabelVoLst;
	}

	@Override
	public List<GoodsBaseLabelVo> findListAll() {
		return goodsBaseLabelRepository.findListAll();
	}
}