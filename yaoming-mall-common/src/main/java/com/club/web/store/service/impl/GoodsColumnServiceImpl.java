package com.club.web.store.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.store.domain.GoodsColumnDo;
import com.club.web.store.domain.RuleValueDo;
import com.club.web.store.domain.repository.GoodsColumnRepository;
import com.club.web.store.domain.repository.RuleSourceRepository;
import com.club.web.store.domain.repository.RuleValueRepository;
import com.club.web.store.listener.GoodsColumnListenerManager;
import com.club.web.store.service.GoodsColumnService;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodsColumnIndexVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.RuleSourceVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**   
* @Title: GoodsColumnServiceImpl.java
* @Package com.club.web.store.service.impl 
* @Description: 商品基础栏目Service接口实现类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
@Service("goodsColumnService")
public class GoodsColumnServiceImpl implements GoodsColumnService {
	
	private Logger logger = LoggerFactory.getLogger(GoodsColumnServiceImpl.class);
	
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
	public Page<Map<String, Object>> selectGoodsColumnByColumnName(Page<Map<String, Object>> page, 
			String columnName, String shopYn, HttpServletRequest request) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		if(columnName.isEmpty()){
			columnName = null;			
		} else{
			columnName = "%" + columnName +"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if("Y".equals(shopYn)){
			Long shopId = getShopId(request);
			map.put("shopYn", shopId);				
		}
		map.put("columnName", columnName);
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		try {
			List<GoodsColumnVo> list = goodsColumnRepository.selectGoodsColumnByColumnName(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));			
		} catch (Exception e) {
			logger.error("查询商品栏目异常:", e);
		}
		try {
			Long count = goodsColumnRepository.queryGoodsColumnCountPage(map);
			result.setTotalRecords(count.intValue());			
		} catch (Exception e) {
			logger.error("查询商品栏目笔数异常:", e);
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> addGoodsColumn(GoodsColumnVo goodsColumnVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsColumnDo goodsColumnDo = goodsColumnRepository.voChangeDo(goodsColumnVo);
		String ruleValue = goodsColumnVo.getRuleVal();
		Date ruleStarttime = goodsColumnVo.getRuleStarttime();
		Date ruleEndtime = goodsColumnVo.getRuleEndtime();
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
				ruleValueDo.setRuleVal(ruleValue);
				ruleValueDo.setRuleId(Long.valueOf(goodsColumnVo.getRuleId()));
			}
			if(goodsColumnVo.getRuleId2() != null && !goodsColumnVo.getRuleId2().isEmpty()){
				ruleValueDo.setRuleStarttime(ruleStarttime);
				ruleValueDo.setRuleEndtime(ruleEndtime);
				ruleValueDo.setRuleId2(Long.valueOf(goodsColumnVo.getRuleId2()));
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
	
	@Override
	public Map<String, Object> deleteGoodsColumn(String idStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		try {
			for (String id : ids) {
				String[] idSplit = id.split(";");
				try {
					boolean flg = goodsColumnListenerManager.deleteGoodsColumn(Long.parseLong(idSplit[0]));
					if(flg){
						result.put("success", true);
						result.put("msg", "删除商品栏目成功");
						goodsColumnRepository.deleteGoodsColumn(Long.parseLong(idSplit[0]));					
					} else{
						result.put("success", false);
						result.put("msg", "商品栏目被引用，不能删除");
						return result;
					}
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", e.getMessage());
					logger.error("删除商品栏目异常:", e);
				}
				if(idSplit[1] != null){
					try {
						ruleValueRepository.deleteRuleValue(Long.parseLong(idSplit[1]));
					} catch (Exception e) {
						result.put("success", false);
						result.put("msg", e.getMessage());
						logger.error("删除规则值异常:", e);
					}
				}
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	@Override
	public Map<String, Object> updateStatusForGoodsColumnById(String idStr, String status) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		try {
			result.put("success", true);
			result.put("msg", "修改状态成功");
			for(String id : ids) {
				goodsColumnRepository.updateStatusForGoodsColumnById(Long.valueOf(id), status);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("修改状态异常:", e);
		}
		
		return result;
	}
	
	@Override
	public List<GoodsColumnVo> selectGoodsColumnListByShopId(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodsColumnVo> goodsColumnVoLst = new ArrayList<GoodsColumnVo>();
		Long shopId = getShopId(request);
		map.put("shopYn", shopId);
		map.put("start", 0);
		map.put("limit", 9999);
		
		try {
			goodsColumnVoLst = goodsColumnRepository.selectGoodsColumnByColumnName(map);
		} catch (Exception e) {
			logger.error("根据店铺查询商品栏目异常:", e);
		}
		
		return goodsColumnVoLst;
	}
	
	@Override
	public List<RuleSourceVo> selectRuleSourceList(int ruleType) {
		List<RuleSourceVo> ruleSourceVoLst = new ArrayList<RuleSourceVo>();
		try {
			ruleSourceVoLst = ruleSourceRepository.selectRuleSourceList(ruleType);
		} catch (Exception e) {
			logger.error("查询规则来源异常:", e);
		}
		
		return ruleSourceVoLst;
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
	
	@Override
	public List<Map<String, Object>> getGoodsAndCloumn(Long shopId) throws SQLException{
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();//返回参数
		Map<String, Object> map = new HashMap<String, Object>();
		CargoClassifyDo cargoClassifyDo = cargoClassifyRepository.findDoByIdAndStatus(ClassifyConstant.CARGO_CLASSIFY,null);
		if(cargoClassifyDo != null){
			map.put("classifyIds", cargoClassifyDo.getAllIds(1));//查询货品分类有效的商品
		}	
		List<GoodListVo> goodList = goodsColumnRepository.selectColumnAllGoods(map);
		columnList = getColumnsGoodList(goodList);
		
		return columnList;
	}
	
	@Override
	public Map<String, Object> selectGoodsByColumnId(Long columnsId,int start,int limit) {
		Map<String, Object> tradegoodInfo = new HashMap<String, Object>();//单个商品参数
		Map<String, Object> columnInfo = new HashMap<String, Object>();//栏目参数，包含栏目名称，ID，商品数组
		List<Map<String, Object>> tradegoodList = new ArrayList<Map<String, Object>>();//商品数组
		String columnName = "";
		String columnId = "";
		String columnPicture = "";
		Map<String, Object> map = new HashMap<String, Object>();
		CargoClassifyDo cargoClassifyDo = cargoClassifyRepository.findDoByIdAndStatus(ClassifyConstant.CARGO_CLASSIFY,null);
		if(cargoClassifyDo != null){
			map.put("classifyIds", cargoClassifyDo.getAllIds(1));//查询货品分类有效的商品
		}
		map.put("columnId", columnsId);
		map.put("start", start*limit);
		map.put("limit", limit);
		List<GoodListVo> goodList = goodsColumnRepository.selectGoodsByColumnId(map);
		if(goodList != null && goodList.size() > 0){
			columnName = goodList.get(0).getColumnName();
			columnId = goodList.get(0).getColumnId();
			columnPicture = goodList.get(0).getColumnPicture();
		}
		for(GoodListVo good : goodList){
			tradegoodInfo = new HashMap<String, Object>();
			tradegoodInfo.put("id", good.getId());
			tradegoodInfo.put("name", good.getName());
			tradegoodInfo.put("showPicture", good.getShowPicture());
			tradegoodInfo.put("score", good.getScore());
			tradegoodInfo.put("marketPrice", good.getMarketPrice());
			tradegoodInfo.put("salePrice", good.getSalePrice());
			tradegoodInfo.put("imgSquare", good.getImgSquare());
			tradegoodInfo.put("imgRectangle", good.getImgRectangle());
			tradegoodInfo.put("imgLarge", good.getImgLarge());
			tradegoodInfo.put("saleNum", good.getSaleNum());
			tradegoodList.add(tradegoodInfo);
		}
		columnInfo.put("columnName", columnName);
		columnInfo.put("columnId", columnId);
		columnInfo.put("columnPicture", columnPicture);
		columnInfo.put("goodsList", tradegoodList);
		
		return columnInfo;
	}
	
	@Override
	public List<Map<String, Object>> selectColumnsGood(){
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();//返回参数
		Map<String, Object> map = new HashMap<String, Object>();
		CargoClassifyDo cargoClassifyDo = cargoClassifyRepository.findDoByIdAndStatus(ClassifyConstant.CARGO_CLASSIFY,null);
		if(cargoClassifyDo != null){
			map.put("classifyIds", cargoClassifyDo.getAllIds(1));//查询货品分类有效的商品
		}
		List<GoodListVo> goodList = goodsColumnRepository.selectColumnsGood(map);
		columnList = getColumnsGoodList(goodList);
		
		return columnList;
	}
	
	/**
	 * 拼装栏目的商品列表
	 * @param goodList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getColumnsGoodList(List<GoodListVo> goodList){
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();//返回参数
		Map<String, Object> tradegoodInfo = new HashMap<String, Object>();//单个商品参数
		Map<String, Object> columnInfo = new HashMap<String, Object>();//栏目参数，包含栏目名称，ID，商品数组
		Map<String, Map<String, Object>> columnInfoMap = new HashMap<String, Map<String, Object>>();
		List<Map<String, Object>> tradegoodList = new ArrayList<Map<String, Object>>();//商品数组
		String columnName = "";
		String columnId = "";
		String columnPicture = "";
		
		if(goodList != null && goodList.size() > 0){
			columnName = goodList.get(0).getColumnName();
		}
		for(GoodListVo good : goodList){
			tradegoodInfo = new HashMap<String, Object>();
			columnInfo = new HashMap<String, Object>();
			tradegoodList = new ArrayList<Map<String, Object>>();
			columnName = good.getColumnName();
			columnId = good.getColumnId();
			columnPicture = good.getColumnPicture();
			tradegoodInfo.put("id", good.getId());
			tradegoodInfo.put("name", good.getName());
			tradegoodInfo.put("showPicture", good.getShowPicture());
			tradegoodInfo.put("score", good.getScore());
			tradegoodInfo.put("marketPrice", good.getMarketPrice());
			tradegoodInfo.put("salePrice", good.getSalePrice());
			tradegoodInfo.put("imgSquare", good.getImgSquare());
			tradegoodInfo.put("imgRectangle", good.getImgRectangle());
			tradegoodInfo.put("imgLarge", good.getImgLarge());
			tradegoodInfo.put("saleNum", good.getSaleNum());
			if(columnInfoMap.containsKey(columnName)){
				columnInfo = columnInfoMap.get(good.getColumnName());
				tradegoodList = (List<Map<String, Object>>) columnInfo.get("goodsList");
			} else{
				columnInfo.put("columnName", columnName);
				columnInfo.put("columnId", columnId);
				columnInfo.put("columnPicture", columnPicture);
			}
			tradegoodList.add(tradegoodInfo);
			columnInfo.put("goodsList", tradegoodList);
			columnInfoMap.put(columnName, columnInfo);
		}
		Iterator<String> keys = columnInfoMap.keySet().iterator();
		while(keys.hasNext()){
			String key = (String)keys.next();
			columnList.add(columnInfoMap.get(key));
		}
		
		return columnList;
	}

	@Override
	public Map<String, Object> goodsColumnService() {
		Map<String, Object> result=new HashMap<String, Object>();
		List<GoodsColumnIndexVo> norule=goodsColumnRepository.selectNoRuleGoodsColumn();
		List<GoodsColumnIndexVo> rule=goodsColumnRepository.selectRuleGoodsColumn();
		result.put("norule", norule);
		result.put("rule", rule);
		return result;
	}
}