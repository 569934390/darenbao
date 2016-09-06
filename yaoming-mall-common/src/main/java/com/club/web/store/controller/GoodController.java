/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.common.Constants;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.store.service.GoodEvaluationService;
import com.club.web.store.service.GoodLabelsService;
import com.club.web.store.service.GoodService;
import com.club.web.store.vo.GoodDetailsVo;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.TradeGoodSkuVo;

/**
 * @Title:商品控制器
 * @Description:
 * @Author:czj
 * @Since:2016年3月25日
 * @Version:1.1.0
 */
@Controller
@RequestMapping("/good")
public class GoodController {

	private Logger logger = LoggerFactory.getLogger(GoodController.class);

	@Autowired
	private GoodService goodServiceImpl;

	@Autowired
	private GoodEvaluationService evaluationService;
	
	@Autowired
	private GoodLabelsService goodLabelsService;

	/**
	 * 
	 * @param modelJson
	 *            添加/修改商品的各个属性，封装成json格式
	 * @return 返回信息提示操作成功或者失败
	 * @Description:
	 */
	@RequestMapping("/addGood")
	@ResponseBody
	public Map<String, Object> addGood(String modelJson, String labelIds, String cargoSkuIdList,
			String cargoSkuNameList, String marketPriceList, String salePriceList, String allPriceList, String count,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		List<String> labelsList = null;
		List<String> skuIdList = null;
		List<String> skuNameList = null;
		List<String> mList = null;
		List<String> sList = null;
		List<String> supplyList = null;
		List<String> countList = null;
		List<String> levelIdList = null;
		GoodVo goodVo = new GoodVo();
		Map<String, Object> map = new HashMap<String, Object>();
		long i = -1L;
		try {
			if (!modelJson.isEmpty()) {
				map = JsonUtil.toMap(modelJson);
				long creator = getUserId(request);
				goodVo = JsonUtil.toBean(modelJson, GoodVo.class);
//				goodVo.setCreator(creator);
				if (map != null && map.get("levelId") != null) {
					try {
						levelIdList = (List<String>) map.get("levelId");

					} catch (Exception e) {
						levelIdList = new ArrayList<String>();
						levelIdList.add(String.valueOf(map.get("levelId")));
					}
				}
				if (labelIds != null && !labelIds.isEmpty() && !labelIds.equals("")) {
					labelsList = JsonUtil.toList(labelIds, String.class);
				}
				if (cargoSkuIdList != null && !cargoSkuIdList.isEmpty() && !cargoSkuIdList.equals("")) {
					skuIdList = JsonUtil.toList(cargoSkuIdList, String.class);
					skuNameList = JsonUtil.toList(cargoSkuNameList, String.class);
					if (skuIdList == null || skuIdList.size() == 0) {
						result.put("success", false);
						result.put("msg", "商品添加失败，请选择商品sku");
						return result;
					}
				} else {
					result.put("success", false);
					result.put("msg", "商品添加失败，请选择商品sku");
					return result;
				}

				if (marketPriceList != null && !marketPriceList.isEmpty() && !marketPriceList.equals("")) {
					mList = JsonUtil.toList(marketPriceList, String.class);
				}
				if (salePriceList != null && !salePriceList.isEmpty() && !salePriceList.equals("")) {
					sList = JsonUtil.toList(salePriceList, String.class);
				}
				if (allPriceList != null && !allPriceList.isEmpty() && !allPriceList.equals("")) {
					supplyList = JsonUtil.toList(allPriceList, String.class);
				}
				if (count != null && !count.isEmpty() && !count.equals("")) {
					countList = JsonUtil.toList(count, String.class);
					if (countList.size() > 0) {
						i = Long.parseLong(countList.get(0));
					}
				}
				goodServiceImpl.addGood(goodVo, labelsList, skuIdList, skuNameList, mList, sList, supplyList, i,
						levelIdList,request);
				result.put("success", true);
				result.put("msg", "添加商品成功");
			}

		} catch (Exception e) {
			System.out.println("添加商品抛出异常:" + e.getMessage());
			result.put("success", false);
			result.put("msg", "添加商品失败");
		}
		return result;
	}

	/**
	 * 
	 * @param modelJson
	 *            添加/修改商品的各个属性，封装成json格式
	 * @return 返回信息提示操作成功或者失败
	 * @Description:
	 */
	@RequestMapping("/editGood")
	@ResponseBody
	public Map<String, Object> editGood(String modelJson, String labelIds, String cargoSkuIdList,
			String cargoSkuNameList, String marketPriceList, String salePriceList, String allPriceList, String count,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		List<String> labelsList = new ArrayList<String>();
		List<String> skuIdList = new ArrayList<String>();
		List<String> skuNameList = new ArrayList<String>();
		List<String> mList = new ArrayList<String>();
		List<String> sList = new ArrayList<String>();
		List<String> supplyList = new ArrayList<String>();
		List<String> countList = new ArrayList<String>();
		GoodVo goodVo = new GoodVo();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> levelIdList = new ArrayList<String>();
		long i = -1L;
		try {
			map = JsonUtil.toMap(modelJson);
			long creator = getUserId(request);
			goodVo = JsonUtil.toBean(modelJson, GoodVo.class);
			goodVo.setCreator(creator);
			if (map != null && map.get("levelId") != null) {
				try {
					levelIdList = (List<String>) map.get("levelId");

				} catch (Exception e) {
					levelIdList = new ArrayList<String>();
					levelIdList.add(String.valueOf(map.get("levelId")));
				}
			}
			if (labelIds != null && !labelIds.isEmpty() && !labelIds.equals("")) {
				labelsList = JsonUtil.toList(labelIds, String.class);
			}
			if (cargoSkuIdList != null && !cargoSkuIdList.isEmpty() && !cargoSkuIdList.equals("")) {
				skuIdList = JsonUtil.toList(cargoSkuIdList, String.class);
				skuNameList = JsonUtil.toList(cargoSkuNameList, String.class);
				if (skuIdList == null || skuIdList.size() == 0) {
					result.put("success", false);
					result.put("msg", "商品添加失败，请选择商品sku");
					return result;
				}
			} else {
				result.put("success", false);
				result.put("msg", "商品編輯失败，请选择商品sku");
				return result;
			}

			if (marketPriceList != null && !marketPriceList.isEmpty() && !marketPriceList.equals("")) {
				mList = JsonUtil.toList(marketPriceList, String.class);
			}
			if (salePriceList != null && !salePriceList.isEmpty() && !salePriceList.equals("")) {
				sList = JsonUtil.toList(salePriceList, String.class);
			}
			if (allPriceList != null && !allPriceList.isEmpty() && !allPriceList.equals("")) {
				supplyList = JsonUtil.toList(allPriceList, String.class);
			}
			if (count != null && !count.isEmpty() && !count.equals("")) {
				countList = JsonUtil.toList(count, String.class);
				if (countList.size() > 0) {
					i = Long.parseLong(countList.get(0));
				}
			}
			goodServiceImpl.editGood(goodVo, labelsList, skuIdList, skuNameList, mList, sList, supplyList, i,
					levelIdList,request);
			result.put("success", true);
			result.put("msg", "编辑商品成功");
		} catch (Exception e) {
			System.out.println("编辑商品抛出异常:" + e.getMessage());
			result.put("success", false);
			result.put("msg", "编辑商品失败");
		}
		return result;

	}

	/**
	 * 根据名称分页查询商品的信息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/goodPage")
	@ResponseBody
	public Page<Map<String, Object>> goodPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}

		// 根据查询分页信息和查询查询分页结果
		page = goodServiceImpl.queryGoodPage(page);
		return page;

	}
	
	/**
	 * 查询商品栏目列表
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/getGoodColumnList")
	@ResponseBody
	public List<GoodsColumnVo> getGoodColumnList() {

		// 根据查询分页信息和查询查询分页结果
		List<GoodsColumnVo> list = goodServiceImpl.getColumnList();
		return list;

	}
	
	
	/**
	 * 查询某个商品对应的标签
	 * 
	 * @param goodId 商品id
	
	 * @return
	 */
	@RequestMapping("/getGoodLabels")
	@ResponseBody
	public List<GoodsBaseLabelVo> getGoodLabels(String goodId) {
		List<GoodsBaseLabelVo> list=null;
		if(goodId !=null && !goodId.equals("")){
		list=goodLabelsService.selectGoodLabels(Long.parseLong(goodId));
		}
		return list;

	}
	
	
	

	/**
	 * 删除商品
	 * 
	 * @return 返回信息提示操作成功或者失败
	 */
	@RequestMapping("/deletegood")
	@ResponseBody
	public Map<String, Object> deleteGood(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("deleteGood");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(goodServiceImpl.deleteGoods(IdStr));
			result.put("success", true);
		} catch (Exception e) {
			logger.error("删除商品异常<deleteGoods>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 根据商品id查出商品SKU的所有规格名称
	 * 
	 * @return 返回信息提示操作成功或者失败
	 */
	@RequestMapping("/selectGoodSku")
	@ResponseBody
	public Map<String, Object> selectGoodSku(@RequestParam(value = "tradeGoodId", required = true) String tradeGoodId) {
		logger.debug("selectGoodSku");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TradeGoodSkuVo> voList = new ArrayList<TradeGoodSkuVo>();
			voList = goodServiceImpl.selectTradeGoodSkuVo(tradeGoodId);
			result.put("list", voList);
			result.put("success", true);
		} catch (Exception e) {
			logger.error("查询商品SKU名称异常<deleteGoods>:", e);
			result.put("success", false);
			result.put("msg", "查询失败！");
		}
		return result;
	}

	/**
	 * 上架功能 接收前台传回来的商品sku和对应的数量
	 * 
	 * @return 返回信息提示上架成功或者失败
	 */
	@RequestMapping("/upGoodSku")
	@ResponseBody
	public Map<String, Object> upGoodSku(HttpServletRequest request,
			@RequestParam(value = "goodupList", required = true) String goodupList, String tradeGoodId) {
		logger.debug("upGoodSku");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		Map<String, Object> loginMap = null;
		long userId = 0;
		try {
			loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;
			List<SkuGoodsParam> list = JsonUtil.toList(goodupList, SkuGoodsParam.class);
			Boolean b = goodServiceImpl.updateUpGoodStatus(tradeGoodId, list, userId);
			if (b) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "商品上架失败！");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("商品上架异常<deleteGoods>:", e);
			result.put("success", false);
			result.put("msg", "商品上架失败！");
		}
		return result;
	}

	/**
	 * 下架功能 接收前台传回来的商品id数组
	 * 
	 * @return 返回信息提示下架成功或者失败
	 */
	@RequestMapping("/downGoodSku")
	@ResponseBody
	public Map<String, Object> downGoodSku(HttpServletRequest request,
			@RequestParam(value = "ids", required = true) String ids) {
		logger.debug("upGoodSku");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		Map<String, Object> loginMap = null;
		long userId = 0;
		try {
			loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;
			List<String> list = JsonUtil.toList(ids, String.class);
			Boolean b = goodServiceImpl.updateDownGoodStatus(list, userId);
			if (b) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "商品下架失败！");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("商品下架异常<deleteGoods>:", e);
			result.put("success", false);
			result.put("msg", "商品下架失败！");
		}

		return result;
	}

	/**
	 * 获取用户Id
	 * 
	 * @param request
	 * 
	 * @add by 2016-04-09
	 */
	@SuppressWarnings("unchecked")
	private long getUserId(HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		long userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;
		return userId;
	}

	/**
	 * 保存商品评价
	 * 
	 * @param request
	 * 
	 * @add by czj 2016-04-09
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addEvaluation")
	@ResponseBody
	private Map<String, Object> addEvaluation(String modelJson, HttpServletRequest request, String logo1) {
		logger.debug("addEvaluation");
		Map<String, Object> result = new HashMap<String, Object>();
		GoodEvaluationVo v = new GoodEvaluationVo();
		List<String> imageList = new ArrayList<String>();
		String array[];
		array = JsonUtil.toArray(logo1, String.class);
		v = JsonUtil.toBean(modelJson, GoodEvaluationVo.class);
		// v.setUser(getUserId(request));
		try {
			evaluationService.saveEvaluation(v, array, 1);
			result.put("success", true);
			return result;
		} catch (Exception e) {
			logger.error("商品评价异常:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 保存商品评价
	 * 
	 * @param request
	 * 
	 * @add by 2016-04-09
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getEvaluation")
	@ResponseBody
	private Map<String, Object> getEvaluation(@RequestParam(value = "tradeGoodId", required = true) String tradeGoodId,
			HttpServletRequest request) {
		logger.debug("getEvaluation");
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			List<GoodEvaluationVo> list = evaluationService.selectEvaluationByGoodId(Long.parseLong(tradeGoodId));
			result.put("evaluationList", list);
			result.put("success", true);
			return result;
		} catch (Exception e) {
			logger.error("获取商品评价信息异常:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	@RequestMapping("/mobile/queryGoodList")
	@ResponseBody
	public List<GoodListVo> queryGoodList(@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "limit", defaultValue = "6") int limit,
			@RequestParam(value = "columnId", defaultValue = "") String columnId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> param = new HashMap<String, Object>();
		if (columnId != null && !columnId.isEmpty()) {
			param.put("columnId", columnId);
		}
		param.put("start", start * limit);
		param.put("limit", limit);
		List<GoodListVo> goodList = goodServiceImpl.queryGoodList(param);

		return goodList;
	}

	@RequestMapping("/mobile/getGoodDetails")
	@ResponseBody
	public GoodDetailsVo getGoodDetails(long goodId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");

		return goodServiceImpl.getGoodDetails(goodId);
	}

	@RequestMapping("/queryGoodSkuList")
	@ResponseBody
	public Map<String, Object> queryGoodSkuList(
			@RequestParam(value = "tradeGoodId", required = true) String tradeGoodId) {
		logger.debug("queryGoodSkuList");
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			List<TradeGoodSkuVo> list = goodServiceImpl.queryGoodSkuList(Long.parseLong(tradeGoodId));
			result.put("goodSkuList", list);
			result.put("success", true);
			return result;
		} catch (Exception e) {
			logger.error("获取获取商品sku信息异常:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 根据Id删除商品评价
	 * 
	 * @param id
	 * 
	 * @add by 2016-04-25
	 */
	@RequestMapping("/deleteEvaluation")
	@ResponseBody
	public Map<String, Object> deleteEvaluationCon(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = evaluationService.deleteEvaluationSer(id);
		} catch (Exception e) {
			logger.error("根据Id删除商品评价异常<deleteEvaluationCon>:", e);
			result.put("code", "-1");
			result.put("msg", "操作失败！");
		}
		return result;
	}
}