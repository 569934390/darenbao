package com.club.web.weixin.controller;

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

import com.club.framework.util.JsonUtil;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.vo.IndentVo;
import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.service.CargoBrandService;
import com.club.web.stock.service.CargoClassifyService;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.stock.vo.CargoClassifyVo;
import com.club.web.store.service.GoodEvaluationService;
import com.club.web.store.service.GoodService;
import com.club.web.store.service.GoodsBaseLabelService;
import com.club.web.store.service.GoodsColumnService;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.GoodDetailsVo;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.store.vo.ShanguoGoodMsg;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.weixin.service.GoodWeixinService;
import com.club.web.weixin.vo.WinXinEvaluateVo;

/**
 * @Title: GoodWeixinController.java
 * @Package com.club.web.weixin.controller
 * @Description: TODO(微信商品)
 * @author 柳伟军
 * @date 2016年5月19日 上午11:23:21
 * @version V1.0
 */
@RequestMapping("/weixin")
@Controller
public class GoodWeixinController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodEvaluationService evaluationService;
	@Autowired
	private CargoClassifyService cargoClassifyService;
	@Autowired
	private CargoBrandService cargoBrandService;
	@Autowired
	private GoodsBaseLabelService goodsBaseLabelService;
	@Autowired
	private GoodsColumnService goodsColumnService;
	@Autowired
	private CouponDetailService couponDetailService;
	@Autowired
	private IndentService indentService;
	@Autowired
	private GoodWeixinService goodWeixinService;

	/**
	 * 手机端获取店铺信息
	 * 
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping("/store/subbranch/loadInfo")
	@ResponseBody
	public SubbranchVo findSubbranchById(Long id, HttpServletResponse response) {
		logger.debug("findSubbranchById GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return subbranchService.selectByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("cargoclassify queryByParentId error:", e);
		}
		return null;
	}

	/**
	 * 手机端获取货品分类
	 * 
	 * @param parentId
	 *            一级传1或者空，其他传父级ID
	 * @return
	 */
	@RequestMapping("/cargo/classify/queryByParentId")
	@ResponseBody
	public List<CargoClassifyVo> queryByParentId(String parentId, HttpServletResponse response) {
		logger.debug("queryByParentId GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			List<CargoClassifyVo> cargoClassifyList = cargoClassifyService.getVoListByParentId(
					parentId == null ? ClassifyConstant.CARGO_CLASSIFY : Long.valueOf(parentId), 1);
//			if (cargoClassifyList != null) {
//				CargoClassifyVo all = new CargoClassifyVo();
//				all.setId(parentId == null ? ClassifyConstant.CARGO_CLASSIFY + "" : parentId);
//				all.setName("全部");
//				cargoClassifyList.add(0, all);
//			}
			return cargoClassifyList;
		} catch (Exception e) {
			logger.error("cargoclassify queryByParentId error:", e);
		}
		return null;
	}

	/**
	 * 手机端获取商品栏目
	 * 
	 * @return
	 */
	@RequestMapping("/good/goodColumn/findAllGoodColumns")
	@ResponseBody
	public Map<String, Object> findAllGoodColumns(HttpServletResponse response) {
		logger.debug("findAllGoodColumns GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return goodsColumnService.goodsColumnService();
		} catch (Exception e) {
			logger.error("查询所有商品栏目异常<findAllGoodColumns>:", e);
		}
		return null;
	}

	/**
	 * 手机端获取货品用途
	 * 
	 * @return
	 */
	@RequestMapping("/good/goodLabels/findAll")
	@ResponseBody
	public List<GoodsBaseLabelVo> findAllGoodLabels(HttpServletResponse response) {
		logger.debug("findAllGoodLabels GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<GoodsBaseLabelVo> list = null;
		try {
			list = goodsBaseLabelService.findListAll();
		} catch (Exception e) {
			logger.error("查询所有货品用途异常<findAllGoodLabels>:", e);
		}
		return list;
	}

	/**
	 * 手机端获取货品品牌
	 * 
	 * @return
	 */
	@RequestMapping("/cargo/brand/findAll")
	@ResponseBody
	public List<CargoBrandVo> findAllBrands(HttpServletResponse response) {
		logger.debug("findAllBrands GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<CargoBrandVo> list = null;
		try {
			list = cargoBrandService.findListAll();
		} catch (Exception e) {
			logger.error("查询所有品牌异常<findAllBrand>:", e);
		}
		return list;
	}

	/**
	 * 获取微信端的商品列表
	 *
	 * @param conditionStr
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * 
	 * @add by 2016-05-17
	 */
	@RequestMapping("/good/getGoodList")
	@ResponseBody
	public List<ShanguoGoodMsg> getGoodListCon(String conditionStr, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<ShanguoGoodMsg> list = null;
		try {
			list = goodService.getWeixinGoodListSer(conditionStr);
		} catch (Exception e) {
			logger.error("获取微信端的商品列表异常<getGoodListCon>:", e);
		}
		return list;
	}

	/**
	 * 查询商品详情
	 *
	 * @param goodId
	 * @return GoodDetailsVo
	 * @add by 2016-05-18
	 */
	@RequestMapping("/good/getGoodDetail")
	@ResponseBody
	public GoodDetailsVo getGoodDetailCon(String goodId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		GoodDetailsVo goodDetail = null;
		try {
			goodDetail = goodService.getShanguoGoodDetailSer(Long.valueOf(goodId), null);
			int score = 0;
			int index = 0;
			if (goodDetail != null) {
				List<GoodEvaluationVo> goodEvaluationList = goodDetail.getGoodEvaluationList();
				if (goodEvaluationList != null && goodEvaluationList.size() > 0) {
					for (GoodEvaluationVo goodEvaluationVo : goodEvaluationList) {
						index++;
						score = score + goodEvaluationVo.getScore();
					}
					if (index != 0) {
						score = score / index;
					}
					goodDetail.setScore(score);
				}
				goodDetail.setGoodEvaluationList(goodEvaluationList);
			}
		} catch (Exception e) {
			logger.error("查询商品详情异常<getSearchGoodListCon>:", e);
		}

		return goodDetail;
	}

	/**
	 * 下订单时验证兑换券的编码和密码,并且验证有效期
	 * 
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping("/good/validateCoupon")
	@ResponseBody
	public Map<String, Object> validateCoupon(String code, String password, String goodId,
			HttpServletResponse response) {
		logger.debug("findSubbranchById GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		// Map<String, Object> serviceResult = new HashMap<String, Object>(); //
		// service返回信息
		Map<String, Object> result = new HashMap<String, Object>(); // controller返回信息
		try {
			/*
			 * serviceResult=couponDetailService.validateCoupon(code, password,
			 * goodId); result.put("success", serviceResult.get("success"));
			 * result.put("msg", String.valueOf(serviceResult.get("msg")));
			 */
			result.putAll(couponDetailService.validateCoupon(code, password, goodId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "验证兑换券失败");
			logger.error("验证兑换券失败-controller", e.getMessage());
		}
		return result;
	}

	/**
	 * 保存评价
	 * 
	 * @param modelJson
	 * @param request
	 * @param logo1
	 * @return
	 */
	@RequestMapping("/good/addEvaluation")
	@ResponseBody
	private Map<String, Object> addEvaluation(String modelJson, String logo1, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("addEvaluation");
		Map<String, Object> result = new HashMap<String, Object>();
		GoodEvaluationVo v = new GoodEvaluationVo();
		String array[];
		array = JsonUtil.toArray(logo1, String.class);
		v = JsonUtil.toBean(modelJson, GoodEvaluationVo.class);
		try {
			evaluationService.saveEvaluation(v, array, 1);
			if(v.getIndentId() != null && !v.getIndentId().isEmpty()){
				IndentVo indentVo = indentService.findVoById(Long.valueOf(v.getIndentId()));
				if(indentVo.getStatus() == 12){
					indentService.updateStatus(v.getIndentId(), "done", null, new HashMap<String, Object>());					
				}
			}
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
	 * 保存评价
	 * 
	 * @param modelJson
	 * @param request
	 * @param logo1
	 * @return
	 */
	@RequestMapping("/good/addEvaluate")
	@ResponseBody
	private Map<String, Object> addEvaluate(String modelJson, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("addEvaluate");
		Map<String, Object> result = new HashMap<String, Object>();
		List<WinXinEvaluateVo> list = new ArrayList<WinXinEvaluateVo>();
		if(modelJson != null && !modelJson.isEmpty()){
			list = JsonUtil.toList(modelJson, WinXinEvaluateVo.class);
		} else{
			result.put("success", false);
			result.put("msg", "评价内容不能为空！");
			return result;
		}
		if (list != null && list.size() > 0) {
			result.putAll(goodWeixinService.saveEvaluation(list));
		} else {
			result.put("success", false);
			result.put("msg", "评价内容不正确！");
		}

		return result;
	}
}