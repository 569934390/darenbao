package com.club.web.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.stock.constant.CargoClassifyStatus;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.store.service.GoodService;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.GoodDetailsVo;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.store.vo.ShanguoGoodMsg;

/**
 * 三国商品控制类-Controller
 * 
 * @author wqh
 * @add by 2016-05-17
 */
@RequestMapping("/mobile")
@Controller
public class GoodAppController {

	private Logger logger = LoggerFactory.getLogger(GoodAppController.class);

	@Autowired
	private GoodService goodService;
	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	@Autowired
	SubbranchService subbranService;
	@Autowired
	CouponDetailService couponDetailService;

	/**
	 * 查询出售中的商品
	 *
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * 
	 * @add by 2016-05-17
	 */
	@RequestMapping("/good/getSaleGoodList")
	@ResponseBody
	public List<ShanguoGoodMsg> getSaleGoodListCon(String shopId, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<ShanguoGoodMsg> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", pageSize);
			map.put("shopId", Long.valueOf(shopId));
			map.put("start", (pageNum - 1) * pageSize);
			list = goodService.getSaleGoodListSer(map);
		} catch (Exception e) {
			logger.error("查询出售中的商品异常<getSaleGoodListCon>:", e);
		}
		return list;
	}

	/**
	 * 查询已下架的商品
	 *
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * 
	 * @add by 2016-05-17
	 */
	@RequestMapping("/good/getUnshelveGoodList")
	@ResponseBody
	public List<ShanguoGoodMsg> getUnshelveGoodListCon(String shopId, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<ShanguoGoodMsg> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", pageSize);
			map.put("shopId", Long.valueOf(shopId));
			map.put("start", (pageNum - 1) * pageSize);
			list = goodService.getUnshelveGoodListSer(map);
		} catch (Exception e) {
			logger.error("查询已下架的商品异常<getUnshelveGoodListCon>:", e);
		}
		return list;
	}

	/**
	 * 搜索商品列表
	 *
	 * @param goodsName
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * 
	 * @add by 2016-05-17
	 */
	@RequestMapping("/good/getSearchGoodList")
	@ResponseBody
	public List<ShanguoGoodMsg> getSearchGoodListCon(@RequestParam(defaultValue = "") String goodsName,
			@RequestParam(defaultValue = "6") int pageSize, @RequestParam(defaultValue = "1") int pageNum,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<ShanguoGoodMsg> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", pageSize);
			map.put("goodsName", goodsName);
			map.put("start", (pageNum - 1) * pageSize);
			list = goodService.getSearchGoodListSer(map);
		} catch (Exception e) {
			logger.error("搜索商品列表异常<getSearchGoodListCon>:", e);
		}
		return list;
	}

	/**
	 * 根据条件查询商品列表  
	 * @param sortType 0:表示出售中 1表示已下架   默认为0
	 * @param shopId 店铺id
	 * @param goodName 商品名称
	 * @param pageSize
	 * @param pageNum
	 * @param response
	 * @return
	 * @add by 2016-06-11
	 */
	@RequestMapping("/good/getGoodList")
	@ResponseBody
	public List<ShanguoGoodMsg> getGoodListCon(@RequestParam(defaultValue = "0")String sortType,String shopId,String classifyId,String brandId,String goodName, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<ShanguoGoodMsg> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", pageSize);
			map.put("shopId", Long.valueOf(shopId));
			map.put("brandId", brandId);
			if(StringUtils.isNotEmpty(classifyId)){
				List<Long> classifyIds = cargoClassifyRepository.getAllIdsByIdAndStatus(Long.valueOf(classifyId), CargoClassifyStatus.启用.getDbData());
				map.put("classifyIds",classifyIds);
			}
			map.put("sortType", sortType);
			map.put("goodName", goodName);
			map.put("start", (pageNum - 1) * pageSize);
			list = goodService.getGoodListByMap(map);
		} catch (Exception e) {
			logger.error("查询已下架的商品异常<getUnshelveGoodListCon>:", e);
		}
		return list;
	}
	
	/**
	 * 查询商品详情
	 *
	 * @param goodId
	 * @param shopId
	 * @return GoodDetailsVo
	 * @add by 2016-05-18
	 */
	@RequestMapping("/good/getGoodDetail")
	@ResponseBody
	public GoodDetailsVo getGoodDetailCon(String goodId, String shopId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		GoodDetailsVo goodDetail = null;
		try {
			goodDetail = goodService.getShanguoGoodDetailSer(Long.valueOf(goodId), Long.valueOf(shopId));
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
				int count =-1;
				try {
					count=subbranService.getShopGoodStatus(Long.valueOf(goodId),Long.valueOf(shopId));
				} catch (Exception e) {
				}
				if (count > 0) {
					goodDetail.setStatus(0);
				} else {
					goodDetail.setStatus(1);
				}
			}
		} catch (Exception e) {
			logger.error("查询商品详情异常<getGoodDetailCon>:", e);
		}

		return goodDetail;
	}

	/**
	 * 店铺商品上架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	@RequestMapping("/good/setGoodShelve")
	@ResponseBody
	public Map<String, Object> setGoodShelveCon(String shopId, String goodIds, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		try {
			result = subbranService.setGoodOutShelveSer(shopId, goodIds);
		} catch (Exception e) {
			logger.error("店铺商品上架异常<setGoodShelveCon>:", e);
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, e.getMessage());
		}
		return result;
	}

	/**
	 * 店铺商品下架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	@RequestMapping("/good/setGoodOutShelve")
	@ResponseBody
	public Map<String, Object> setGoodOutShelveCon(String shopId, String goodIds, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		try {
			result = subbranService.setGoodShelveSer(shopId, goodIds);
		} catch (Exception e) {
			logger.error("店铺商品上架异常<setGoodOutShelveCon>:", e);
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, e.getMessage());
		}
		return result;
	}
	
}
