package com.club.web.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.stock.service.StockManagerService;
import com.club.web.stock.vo.TreeListVo;

/**
 * 库存管理-Controller
 * 
 * @author wqh
 * @add by 2016-03-20
 */
@RequestMapping("/stock")
@Controller
public class StockManagerController {
	/**
	 * 引入日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(StockManagerController.class);
	/**
	 * 注入库存的service
	 */
	@Autowired
	StockManagerService stockService;
	/**
	 * 操作结果返回信息
	 */
	private Map<String, Object> result;

	/*
	 * 查询入库单
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-20
	 */
	@RequestMapping("/inbound/queryInboundOrderList")
	@ResponseBody
	public Page<Map<String, Object>> queryInboundOrderListCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryInboundOrderListSer(page);
		} catch (Exception e) {
			logger.error("查询入库单异常<queryInboundOrderListCon>:", e);
		}
		return page;
	}

	/*
	 * 查询入库单明细
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-20
	 */
	@RequestMapping("/inbound/queryInboundOrderDetail")
	@ResponseBody
	public Page<Map<String, Object>> queryInboundOrderDetailCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryInboundOrderDetailSer(page);
		} catch (Exception e) {
			logger.error("查询入库单明细<queryInboundOrderDetailCon>:", e);
		}
		return page;
	}

	/*
	 * 添加入库/出库-查询货品信息
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-25
	 */
	@RequestMapping("/inbound/queryGoodsMsg")
	@ResponseBody
	public Page<Map<String, Object>> queryGoodsMsgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryGoodsMsgSer(page);
		} catch (Exception e) {
			logger.error("添加入库-查询货品信息异常<queryGoodsMsgCon>:", e);
		}
		return page;
	}

	/*
	 * 添加入库-查询货品Sku信息
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-25
	 */
	@RequestMapping("/inbound/queryGoodsSkuMsg")
	@ResponseBody
	public Page<Map<String, Object>> queryGoodsSkuMsgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryGoodsSkuMsgSer(page);
		} catch (Exception e) {
			logger.error("添加入库-查询货品Sku信息异常<queryGoodsSkuMsgCon>:", e);
		}
		return page;
	}

	/*
	 * 保存入库单信息
	 * 
	 * @param request
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-03-26
	 */
	@RequestMapping("/inbound/saveGoodsSkuMsg")
	@ResponseBody
	public Map<String, Object> saveGoodsSkuMsgCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.saveInboundStockSer(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
			logger.error("保存入库单信息异常<saveGoodsSkuMsgCon>:", e);
		}
		return result;
	}

	/*
	 * 修改入库单状态
	 * 
	 * @param request
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-03-26
	 */
	@RequestMapping("/inbound/updateInboundStatus")
	@ResponseBody
	public Map<String, Object> updateInboundStatusCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.updateInboundStatusSer(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("修改入库单状态异常<updateInboundStatusCon>:", e);
		}
		return result;
	}

	/*
	 * 删除入库单信息
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-03-26
	 */
	@RequestMapping("/inbound/delInboundOrder")
	@ResponseBody
	public Map<String, Object> delInboundOrderCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.delInboundOrderSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("删除入库单信息异常<delInboundOrderCon>:", e);
		}
		return result;
	}

	/*
	 * 删除入库单明细信息
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-03-28
	 */
	@RequestMapping("/inbound/delInboundOrderDetail")
	@ResponseBody
	public Map<String, Object> delInboundOrderDetailCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.delInboundOrderDetailSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("删除入库单明细信息异常<delInboundOrderDetailCon>:", e);
		}
		return result;
	}

	/*
	 * 查询库存信息
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-20
	 */
	@RequestMapping("/modular/queryStockMsg")
	@ResponseBody
	public Page<Map<String, Object>> queryStockMsgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryStockMsgSer(page);
		} catch (Exception e) {
			logger.error("查询库存信息异常<queryStockMsgCon>:", e);
		}
		return page;
	}

	/*
	 * 修改库存留存数量
	 * 
	 * @param request
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-29
	 */
	@RequestMapping("/modular/updateStockRemain")
	@ResponseBody
	public Map<String, Object> updateStockRemainCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.updateStockRemainSer(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("修改库存留存数量异常<updateStockRemainCon>:", e);
		}
		return result;
	}

	/*
	 * 更新存在异常的库存信息
	 * 
	 * @param request
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-30
	 */
	@RequestMapping("/modular/updateNormalStockMsg")
	@ResponseBody
	public Map<String, Object> updateNormalStockMsgCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.updateNormalStockMsgSer(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("更新存在异常的库存信息异常<updateNormalStockMsgCon>:", e);
		}
		return result;
	}

	/*
	 * 查询出库单信息
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-31
	 */
	@RequestMapping("/outbound/queryOutboundOrderList")
	@ResponseBody
	public Page<Map<String, Object>> queryOutboundOrderListCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryOutboundOrderListSer(page);
		} catch (Exception e) {
			logger.error("查询出库单信息异常<queryOutboundOrderListCon>:", e);
		}
		return page;
	}

	/*
	 * 查询出库单详细信息
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-31
	 */
	@RequestMapping("/outbound/queryOutboundOrderDetail")
	@ResponseBody
	public Page<Map<String, Object>> queryOutboundOrderDetailCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryOutboundOrderDetailSer(page);
		} catch (Exception e) {
			logger.error("查询出库单详细信息异常<queryOutboundOrderDetailCon>:", e);
		}
		return page;
	}

	/*
	 * 查询新增出库单-货品sku列表
	 * 
	 * @param page-分页信息
	 * 
	 * @param conditionStr-查询参数
	 * 
	 * @add by 2016-03-31
	 */
	@RequestMapping("/outbound/queryOutboundStockSkuMsg")
	@ResponseBody
	public Page<Map<String, Object>> queryOutboundStockSkuMsgCon(Page<Map<String, Object>> page, String conditionStr) {
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = stockService.queryOutboundStockSkuMsgSer(page);
		} catch (Exception e) {
			logger.error("查询新增出库单的货品sku列表异常<queryOutboundStockSkuMsgCon>:", e);
		}
		return page;
	}

	/*
	 * 保存出库单信息
	 * 
	 * @param request
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-03-31
	 */
	@RequestMapping("/outbound/saveOutboundMsg")
	@ResponseBody
	public Map<String, Object> saveOutboundMsgCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.saveOutboundMsgSer(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
			logger.error("保存出库单信息异常<saveOutboundMsgCon>:", e);
		}
		return result;
	}

	/*
	 * 删除出库单明细信息
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-04-01
	 */
	@RequestMapping("/outbound/delOutboundOrderDetail")
	@ResponseBody
	public Map<String, Object> delOutboundOrderDetailCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.delOutboundOrderDetailSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("删除出库单明细信息异常<delOutboundOrderDetailCon>:", e);
		}
		return result;
	}

	/*
	 * 删除出库单信息
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-04-01
	 */
	@RequestMapping("/outbound/delOutboundOrder")
	@ResponseBody
	public Map<String, Object> delOutboundOrderCon(String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.delOutboundOrderSer(paramMap);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("删除出库单信息异常<delOutboundOrderCon>:", e);
		}
		return result;
	}

	/*
	 * 修改出库单状态
	 * 
	 * @param request
	 * 
	 * @param conditionStr-参数
	 * 
	 * @add by 2016-04-01
	 */
	@RequestMapping("/outbound/updateOutboundStatus")
	@ResponseBody
	public Map<String, Object> updateOutboundStatusCon(HttpServletRequest request, String conditionStr) {
		result = new HashMap<String, Object>();
		Map<String, Object> paramMap = null;
		try {
			long userId = getUserId(request);
			// 判断参数是否为空
			if (StringUtils.isNotEmpty(conditionStr)) {
				paramMap = JsonUtil.toMap(conditionStr);
				result = stockService.updateOutboundStatusSerHandler(paramMap, userId);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败，请重新操作！");
			logger.error("修改出库单状态异常<updateOutboundStatusCon>:", e);
		}
		return result;
	}

	/*
	 * 查询树列表
	 * 
	 * @add by 2016-04-05
	 */
	@RequestMapping("/tree/queryTreeMenu")
	@ResponseBody
	public String queryTreeMenuCon() {
		String result = "";
		try {
			List<TreeListVo> list = stockService.queryTreeMenuSer();
			if (list != null && list.stream().count() > 0) {
				result = JsonUtil.toJson(list);
			}
		} catch (Exception e) {
			logger.error("查询树列表异常<queryTreeMenuCon>:", e);
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

}
