/**
 * 
 */
package com.club.web.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.stock.domain.CargoInboundDetailDo;
import com.club.web.stock.domain.CargoInboundOrderDo;
import com.club.web.stock.domain.CargoOutboundDetailDo;
import com.club.web.stock.domain.CargoOutboundOrderDo;
import com.club.web.stock.domain.CargoSkuStockDo;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.service.CargoClassifyService;
import com.club.web.stock.service.StockManagerService;
import com.club.web.stock.vo.CargoClassifyVo;
import com.club.web.stock.vo.CargoInboundDetailVo;
import com.club.web.stock.vo.CargoInboundOrderVo;
import com.club.web.stock.vo.CargoOutboundOrderVo;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.stock.vo.CargoSkuStockVo;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.stock.vo.StockGoodsInboundMsgVo;
import com.club.web.stock.vo.StockGoodsOutboundMsgVo;
import com.club.web.stock.vo.TreeListVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.sqlexecutor.SqlExecuteRepository;

/**
 * 库存管理-service
 * 
 * @author wqh
 * 
 * @add by 2016-03-20
 *
 */
@Service
@Transactional
public class StockManagerServiceImpl implements StockManagerService {
	/**
	 * 引入日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(StockManagerServiceImpl.class);
	/**
	 * 注入库存仓库
	 */
	@Autowired
	StockManagerRepository repository;
	/**
	 * 注入货品分类
	 */
	@Autowired
	private CargoClassifyService cargoClassify;
	/**
	 * 封装操作结果信息
	 */
	private Map<String, Object> result;
	@Autowired
	private SqlExecuteRepository sqlExecuteRepository;

	/**
	 * 保存入库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-26
	 */
	@Override
	public Map<String, Object> saveInboundStockSer(Map<String, Object> paramMap, long userId) throws Exception {
		result = new HashMap<String, Object>();
		boolean trag = false;
		CargoInboundOrderDo inboundObj = null;
		List<CargoInboundDetailDo> listDetail = null;
		// skuId
		String skuIds = StringUtils.EMPTY;
		// sku数量
		String skuCount = StringUtils.EMPTY;
		// 来源单号
		String sourceCode = StringUtils.EMPTY;
		// / 备注
		String remk = StringUtils.EMPTY;
		// 入库单Id
		String inboundId = StringUtils.EMPTY;
		long[] skuIdList = null;
		int[] skuCountList = null;

		if (paramMap != null) {
			if (paramMap.containsKey("skuIds")) {
				skuIds = paramMap.get("skuIds") != null ? paramMap.get("skuIds").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("skuCount")) {
				skuCount = paramMap.get("skuCount") != null ? paramMap.get("skuCount").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("sourceCode")) {
				sourceCode = paramMap.get("sourceCode") != null ? paramMap.get("sourceCode").toString()
						: StringUtils.EMPTY;
			}
			if (paramMap.containsKey("remk")) {
				remk = paramMap.get("remk") != null ? paramMap.get("remk").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("inboundId")) {
				inboundId = paramMap.get("inboundId") != null ? paramMap.get("inboundId").toString()
						: StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(skuIds)) {
				String[] arr = skuIds.split(",");
				if (arr != null && arr.length > 0) {
					skuIdList = new long[arr.length];
					for (int i = 0; i < arr.length; i++) {
						skuIdList[i] = Long.valueOf(arr[i]);
					}
				}
			}
			if (StringUtils.isNotEmpty(skuCount)) {
				String[] arr = skuCount.split(",");
				if (arr != null && arr.length > 0) {
					skuCountList = new int[arr.length];
					for (int i = 0; i < arr.length; i++) {
						// 判断是否为数字
						if (CommonUtil.isDigital(arr[i])) {
							skuCountList[i] = Integer.valueOf(arr[i]);
						} else {
							result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
							result.put(Constants.RESULT_MSG, "入库货品列表数量包含非法字符");
							return result;
						}
					}
				}
			}
			// 判断是否有值
			if (skuIdList != null && skuIdList.length > 0 && skuCountList != null && skuCountList.length > 0) {
				for (long c : skuCountList) {
					if (c < 1) {
						trag = true;
						break;
					}
				}
				// sku数量的判断
				if (!trag) {
					try {
						// 关闭外键约束
						sqlExecuteRepository.disableForeignKeyChecks();
						if (StringUtils.isNotEmpty(inboundId)) {
							listDetail = repository.createInboundDetailObj(skuIdList, skuCountList,
									Long.valueOf(inboundId));
							if (listDetail != null && listDetail.stream().count() > 0) {
								for (CargoInboundDetailDo ddo : listDetail) {
									if (ddo.getFlag() == 0) {
										ddo.save();
									} else {
										ddo.update();
									}
								}
							} else {
								result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
								result.put(Constants.RESULT_MSG, "修改信息对象不存在");
							}
						} else {
							inboundObj = repository.createInboundObj(skuIdList, skuCountList, userId, sourceCode, remk);
							if (inboundObj != null) {
								inboundObj.save();
							} else {
								result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
								result.put(Constants.RESULT_MSG, "保存信息不存在");
							}
						}
						result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
						result.put(Constants.RESULT_MSG, "保存成功");
					} finally {
						// 打开外键约束
						sqlExecuteRepository.enableForeignKeyChecks();
					}
				} else {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "入库货品列表数量必须大于0");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "入库货品SKU列表不能为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 修改入库单状态
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-26
	 */
	@Override
	public Map<String, Object> updateInboundStatusSer(Map<String, Object> paramMap, long userId) throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		int status = -1;
		String desc = StringUtils.EMPTY;
		List<CargoInboundOrderDo> list = null;
		List<CargoInboundDetailVo> detailList = null;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("desc")) {
				desc = paramMap.get("desc") != null ? paramMap.get("desc").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("status")) {
				status = paramMap.get("status") != null ? Integer.valueOf(paramMap.get("status").toString()) : -1;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (status > -1 && ids != null && ids.stream().count() > 0) {
				list = repository.getInboundObjByIds(ids);
				if (list != null && list.stream().count() > 0) {
					for (CargoInboundOrderDo inboundDo : list) {
						if (status == 2) {
							// 申请入库判断明细是否存在
							detailList = repository.queryByInboundId(inboundDo.getId());
							if (detailList == null || detailList.stream().count() == 0) {
								result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
								result.put(Constants.RESULT_MSG, "提交审核记录中存在入库的明细不存在");
								return result;
							}
						}
						// 执行审核更新操作
						inboundDo.update(status, desc, userId);
					}
					result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
					result.put(Constants.RESULT_MSG, "操作成功");
				} else {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
					result.put(Constants.RESULT_MSG, "操作失败");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 修改库存留存数量
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-29
	 */
	@Override
	public Map<String, Object> updateStockRemainSer(Map<String, Object> paramMap, long userId) throws Exception {
		result = handleStock(-2, null, userId, null, 0, paramMap);
		return result;
	}

	/**
	 * 修改库存留存数量
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-29
	 */
	private Map<String, Object> updateRemainStock(Map<String, Object> paramMap, long userId) throws Exception {
		result = new HashMap<String, Object>();
		long id = 0;
		int noShelve = 0;
		int remainCount = 0;
		int count = 0;
		String updateCount = StringUtils.EMPTY;
		int variable = 0;
		CargoSkuStockDo stockDo = null;
		if (paramMap != null) {
			if (paramMap.containsKey("id")) {
				id = paramMap.get("id") != null ? Long.valueOf(paramMap.get("id").toString()) : 0;
			}
			if (paramMap.containsKey("updateCount")) {
				updateCount = paramMap.get("updateCount") != null ? paramMap.get("updateCount").toString()
						: StringUtils.EMPTY;
			}
			if (CommonUtil.isDigital(updateCount)) {
				count = Integer.valueOf(updateCount);
				if (remainCount < 0) {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "留存数量不能小于0");
				} else {
					stockDo = repository.queryStockById(id);
					if (stockDo != null) {
						remainCount = stockDo.getRemainCount();
						noShelve = stockDo.getOutShelvesNo();
						if (remainCount == count) {
							result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
							result.put(Constants.RESULT_MSG, "操作成功");
						} else if (count > (noShelve + remainCount)) {
							result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
							result.put(Constants.RESULT_MSG, "留存数量不能大于未上架数量，请重新输入！");
						} else {
							variable = remainCount - count;
							stockDo.update(count, variable, userId);
							result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
							result.put(Constants.RESULT_MSG, "操作成功");
						}
					} else {
						result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
						result.put(Constants.RESULT_MSG, "修改对象信息不存在！");
					}
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "留存数量包含非法字符，请重新输入!");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败！");
		}
		return result;
	}

	/**
	 * 删除入库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-27
	 */
	@Override
	public Map<String, Object> delInboundOrderSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (ids != null && ids.stream().count() > 0) {
				repository.deleteInboundOrderDetail(ids);
				repository.deleteInboundOrder(ids);
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 删除入库单明细信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-27
	 */
	@Override
	public Map<String, Object> delInboundOrderDetailSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (ids != null && ids.stream().count() > 0) {
				repository.deleteInboundOrderDetailByIds(ids);
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 查询入库单
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-20
	 */
	@Override
	public Page<Map<String, Object>> queryInboundOrderListSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<CargoInboundOrderVo> list = null;
		List<Map<String, Object>> listMap = null;
		int total = 0;
		int status = 0;
		String matchParam = StringUtils.EMPTY;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("status")) {
						status = con.get("status") != null ? Integer.valueOf(con.get("status").toString()) : 0;
					}
					if (con.containsKey("inboundName")) {
						matchParam = con.get("inboundName") != null ? con.get("inboundName").toString()
								: StringUtils.EMPTY;
					}
				}
				total = repository.queryInboundOrderTotal(status, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					list = repository.queryInboundOrderList(status, matchParam, startIndex, pageSize);
					listMap = CommonUtil.listObjTransToListMap(list);
					if (listMap != null && listMap.size() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询入库单异常<queryInboundOrderListSer>:", e);
		}
		return page;
	}

	/**
	 * 查询入库单明细
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-20
	 */
	@Override
	public Page<Map<String, Object>> queryInboundOrderDetailSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Long> nodeList = null;
		List<StockGoodsInboundMsgVo> listGoods = null;
		List<Map<String, Object>> listMap = null;
		// 模糊查询参数
		String matchParam = StringUtils.EMPTY;
		long classify = 0;
		long brand = 0;
		long inboundId = 0;
		int total = 0;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
					if (con.containsKey("classify")) {
						classify = con.get("classify") != null ? Long.valueOf(con.get("classify").toString()) : 0;
					}
					if (con.containsKey("brand")) {
						brand = con.get("brand") != null ? Long.valueOf(con.get("brand").toString()) : 0;
					}
					if (con.containsKey("inboundId")) {
						inboundId = con.get("inboundId") != null ? Long.valueOf(con.get("inboundId").toString()) : 0;
					}
				}
				if (inboundId > 0) {
					// 获取分类当前结点的所有子节点
					nodeList = queryClassyByNodeId(classify);
					total = repository.queryInboundDetailTotal(nodeList, brand, inboundId, matchParam);
					page.setTotalRecords(total);
					if (total > 0) {
						listGoods = repository.queryInboundorderDetailList(nodeList, brand, inboundId, matchParam,
								startIndex, pageSize);
						setCargoSkuSpecList(listGoods);
						listMap = CommonUtil.listObjTransToListMap(listGoods);
						if (listMap != null && listMap.size() > 0) {
							page.setResultList(listMap);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询入库单明细异常<queryInboundOrderDetailSer>:", e);
		}
		return page;
	}

	/**
	 * 添加入库-查询货品信息
	 * 
	 * @param page
	 *            -参数信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	@Override
	public Page<Map<String, Object>> queryGoodsMsgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Long> nodeList = null;
		List<StockGoodsInboundMsgVo> listGoods = null;
		List<Map<String, Object>> listMap = null;
		// 模糊查询参数
		String matchParam = StringUtils.EMPTY;
		long classify = 0;
		int total = 0;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
					if (con.containsKey("classify")) {
						classify = con.get("classify") != null ? Long.valueOf(con.get("classify").toString()) : 0;
					}
				}
				// 获取分类当前结点的所有子节点
				nodeList = queryClassyByNodeId(classify);
				total = repository.queryGoodsMsgTotal(nodeList, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					listGoods = repository.queryGoodsMsg(nodeList, matchParam, startIndex, pageSize);
					listMap = CommonUtil.listObjTransToListMap(listGoods);
					if (listMap != null && listMap.size() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("添加入库-查询货品信息异常<queryGoodsMsgCon>:", e);
		}
		return page;
	}

	/**
	 * 添加入库-查询货品Sku信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	@Override
	public Page<Map<String, Object>> queryGoodsSkuMsgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<StockGoodsInboundMsgVo> listGoods = null;
		List<Map<String, Object>> listMap = null;
		// 货品id
		String goodsIds = StringUtils.EMPTY;
		List<Long> goodsIdList = null;
		try {
			if (page != null) {
				pageSize = page.getLimit();
				startIndex = page.getStart();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("goodsIds")) {
						goodsIds = con.get("goodsIds") != null ? con.get("goodsIds").toString() : StringUtils.EMPTY;
					}
				}
				if (StringUtils.isNotEmpty(goodsIds)) {
					String[] arr = goodsIds.split(",");
					if (arr != null && arr.length > 0) {
						goodsIdList = new ArrayList<Long>();
						for (String str : arr) {
							goodsIdList.add(Long.valueOf(str));
						}
					}
				}
				if (goodsIdList != null && goodsIdList.stream().count() > 0) {
					total = repository.queryGoodsSkuMsgTotal(goodsIdList);
					page.setTotalRecords(total);
					if (total > 0) {
						listGoods = repository.queryGoodsSkuMsg(goodsIdList, startIndex, pageSize);
					}
					setCargoSkuSpecList(listGoods);
				}
				listMap = CommonUtil.listObjTransToListMap(listGoods);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		} catch (Exception e) {
			logger.error("添加入库-查询货品Sku信息异常<queryGoodsSkuMsgSer>:", e);
		}
		return page;
	}

	/**
	 * 添加出库-查询货品Sku信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	@Override
	public Page<Map<String, Object>> queryOutboundStockSkuMsgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<StockGoodsOutboundMsgVo> listGoods = null;
		List<Map<String, Object>> listMap = null;
		// 货品id
		String goodsIds = StringUtils.EMPTY;
		List<Long> goodsIdList = null;
		try {
			if (page != null) {
				pageSize = page.getLimit();
				startIndex = page.getStart();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("goodsIds")) {
						goodsIds = con.get("goodsIds") != null ? con.get("goodsIds").toString() : StringUtils.EMPTY;
					}
				}
				if (StringUtils.isNotEmpty(goodsIds)) {
					String[] arr = goodsIds.split(",");
					if (arr != null && arr.length > 0) {
						goodsIdList = new ArrayList<Long>();
						for (String str : arr) {
							goodsIdList.add(Long.valueOf(str));
						}
					}
				}
				if (goodsIdList != null && goodsIdList.stream().count() > 0) {
					total = repository.queryOutboundSkuMsgTotal(goodsIdList);
					page.setTotalRecords(total);
					if (total > 0) {
						listGoods = repository.queryOutboundSkuMsg(goodsIdList, startIndex, pageSize);
					}
					setCargoSkuSpecList(listGoods);
				}
				listMap = CommonUtil.listObjTransToListMap(listGoods);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		} catch (Exception e) {
			logger.error("添加出库-查询货品Sku信息异常<queryOutboundStockSkuMsgSer>:", e);
		}
		return page;
	}

	/**
	 * 设置对象的规格值
	 * 
	 * @param listStock
	 * @add by 2016-03-20
	 */
	private <T> void setCargoSkuSpecList(List<T> t) {
		List<CargoSkuItemVo> list = null;
		CargoSkuStockVo cargoSkuStockVo = null;
		StockGoodsInboundMsgVo stockGoodsMsgVo = null;
		StockGoodsOutboundMsgVo stockGoodsOutboundMsgVo = null;
		if (t != null && t.stream().count() > 0) {
			for (T vo : t) {
				try {
					list = null;
					if (vo instanceof CargoSkuStockVo) {
						cargoSkuStockVo = (CargoSkuStockVo) vo;
						list = repository.queryGoodsSpecList(Long.valueOf(cargoSkuStockVo.getSkuId()));
						if (list != null && list.size() > 0) {
							cargoSkuStockVo.setItem(list);
						}
					} else if (vo instanceof StockGoodsInboundMsgVo) {
						stockGoodsMsgVo = (StockGoodsInboundMsgVo) vo;
						list = repository.queryGoodsSpecList(Long.valueOf(stockGoodsMsgVo.getSkuId()));
						if (list != null && list.size() > 0) {
							stockGoodsMsgVo.setItem(list);
						}
					} else if (vo instanceof StockGoodsOutboundMsgVo) {
						stockGoodsOutboundMsgVo = (StockGoodsOutboundMsgVo) vo;
						list = repository.queryGoodsSpecList(Long.valueOf(stockGoodsOutboundMsgVo.getSkuId()));
						if (list != null && list.size() > 0) {
							stockGoodsOutboundMsgVo.setItem(list);
						}
					}
				} catch (Exception e) {
					logger.error("设置对象的规格值异常<setCargoSkuSpecList>:", e);
				}
			}
		}
	}

	/**
	 * 查询库存信息
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-29
	 */
	@Override
	public Page<Map<String, Object>> queryStockMsgSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Long> nodeList = null;
		List<CargoSkuStockVo> listStock = null;
		List<Map<String, Object>> listMap = null;
		// 模糊查询参数
		String matchParam = StringUtils.EMPTY;
		long classify = 0;
		long brand = 0;
		int total = 0;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
					if (con.containsKey("classify")) {
						classify = con.get("classify") != null ? Long.valueOf(con.get("classify").toString()) : 0;
					}
					if (con.containsKey("brand")) {
						brand = con.get("brand") != null ? Long.valueOf(con.get("brand").toString()) : 0;
					}
				}
				// 获取分类当前结点的所有子节点F
				nodeList = queryClassyByNodeId(classify);
				total = repository.queryStockMsgTotal(nodeList, brand, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					listStock = repository.queryStockMsg(nodeList, brand, matchParam, startIndex, pageSize);
					setCargoSkuSpecList(listStock);
					listMap = CommonUtil.listObjTransToListMap(listStock);
					if (listMap != null && listMap.size() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询库存信息异常<queryStockMsgSer>:", e);
		}
		return page;
	}

	/**
	 * 根据节点查询分类子节点信息
	 * 
	 * @param page
	 * @add by 2016-03-29
	 */
	private List<Long> queryClassyByNodeId(long nodeId) {
		List<Long> nodeList = null;
		try {
			if (nodeId > 0) {
				// 获取分类当前结点的所有子节点
				nodeList = cargoClassify.getAllIdsByIdAndStatus(nodeId, null);
			}
		} catch (Exception e) {
			logger.error("根据节点查询分类子节点信息异常<queryClassyByNodeId>:", e);
		}
		return nodeList;
	}

	/**
	 * 更新存在异常的库存信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-30
	 */
	@Override
	public Map<String, Object> updateNormalStockMsgSer(Map<String, Object> paramMap, long userId) throws Exception {
		result = handleStock(-1, null, userId, null, 0, paramMap);
		return result;
	}

	/**
	 * 更新存在异常的库存信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-30
	 */
	private Map<String, Object> updateExceptionStock(Map<String, Object> paramMap, long userId) throws Exception {
		result = new HashMap<>();
		String ids = StringUtils.EMPTY;
		String nopays = StringUtils.EMPTY;
		String noSends = StringUtils.EMPTY;
		String[] arrId = null;
		String[] arrNoPay = null;
		String[] arrNoSend = null;
		int variable_pay = 0;
		int variable_send = 0;
		CargoSkuStockDo stockDo = null;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				ids = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("nopays")) {
				nopays = paramMap.get("nopays") != null ? paramMap.get("nopays").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("noSends")) {
				noSends = paramMap.get("noSends") != null ? paramMap.get("noSends").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(nopays) && StringUtils.isNotEmpty(noSends)) {
				arrId = ids.split(",");
				arrNoPay = nopays.split(",");
				arrNoSend = noSends.split(",");
			}
			if (arrId != null && arrId.length > 0 && arrNoPay != null && arrNoPay.length > 0 && arrNoSend != null
					&& arrNoSend.length > 0) {
				for (int i = 0; i < arrId.length; i++) {
					stockDo = repository.queryStockById(Long.valueOf(arrId[i]));
					// 已售未付款差异
					variable_pay = Integer.valueOf(arrNoPay[i]) - stockDo.getOnPayNo();
					// 已售待发货差异
					variable_send = Integer.valueOf(arrNoSend[i]) - stockDo.getOnSendNo();
					if (stockDo != null) {
						stockDo.update(variable_pay, variable_send, Integer.valueOf(arrNoPay[i]),
								Integer.valueOf(arrNoSend[i]), userId);
					}
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "更新对象不存在");
		}
		return result;
	}

	/**
	 * 查询出库单信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-31
	 */
	@Override
	public Page<Map<String, Object>> queryOutboundOrderListSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<CargoOutboundOrderVo> listOutbound = null;
		List<Map<String, Object>> listMap = null;
		// 模糊查询参数
		String matchParam = StringUtils.EMPTY;
		int status = 0;
		int total = 0;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
					if (con.containsKey("status")) {
						status = con.get("status") != null ? Integer.valueOf(con.get("status").toString()) : 0;
					}
				}
				total = repository.queryOutboundOrderListTotal(status, matchParam);
				page.setTotalRecords(total);
				if (total > 0) {
					listOutbound = repository.queryOutboundOrderList(status, matchParam, startIndex, pageSize);
					setCargoSkuSpecList(listOutbound);
					listMap = CommonUtil.listObjTransToListMap(listOutbound);
					if (listMap != null && listMap.size() > 0) {
						page.setResultList(listMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询出库单信息异常<queryOutboundOrderListSer>:", e);
		}
		return page;
	}

	/**
	 * 查询出库单详细信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-31
	 */
	@Override
	public Page<Map<String, Object>> queryOutboundOrderDetailSer(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		List<Long> nodeList = null;
		List<StockGoodsOutboundMsgVo> listGoods = null;
		List<Map<String, Object>> listMap = null;
		// 模糊查询参数
		String matchParam = StringUtils.EMPTY;
		long classify = 0;
		long brand = 0;
		long outboundId = 0;
		int total = 0;
		try {
			if (page != null) {
				startIndex = page.getStart();
				pageSize = page.getLimit();
				Map<String, Object> con = page.getConditons();
				if (con != null) {
					if (con.containsKey("matchParam")) {
						matchParam = con.get("matchParam") != null ? con.get("matchParam").toString()
								: StringUtils.EMPTY;
					}
					if (con.containsKey("classify")) {
						classify = con.get("classify") != null ? Long.valueOf(con.get("classify").toString()) : 0;
					}
					if (con.containsKey("brand")) {
						brand = con.get("brand") != null ? Long.valueOf(con.get("brand").toString()) : 0;
					}
					if (con.containsKey("outboundId")) {
						outboundId = con.get("outboundId") != null ? Long.valueOf(con.get("outboundId").toString()) : 0;
					}
				}
				if (outboundId > 0) {
					// 获取分类当前结点的所有子节点
					nodeList = queryClassyByNodeId(classify);
					total = repository.queryOutboundDetailTotal(nodeList, brand, outboundId, matchParam);
					page.setTotalRecords(total);
					if (total > 0) {
						listGoods = repository.queryOutboundDetail(nodeList, brand, outboundId, matchParam, startIndex,
								pageSize);
						setCargoSkuSpecList(listGoods);
						listMap = CommonUtil.listObjTransToListMap(listGoods);
						if (listMap != null && listMap.size() > 0) {
							page.setResultList(listMap);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询出库单详细信息异常<queryOutboundOrderDetailSer>:", e);
		}
		return page;
	}

	/**
	 * 保存出库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-31
	 */
	@Override
	public Map<String, Object> saveOutboundMsgSer(Map<String, Object> paramMap, long userId) throws Exception {
		result = new HashMap<String, Object>();
		CargoOutboundOrderDo outboundDo = null;
		List<CargoOutboundDetailDo> listDetail = null;
		// skuId
		String skuIds = StringUtils.EMPTY;
		// sku数量
		String skuCount = StringUtils.EMPTY;
		// 来源单号
		String sourceCode = StringUtils.EMPTY;
		// / 备注
		String remk = StringUtils.EMPTY;
		// 出库单Id
		String outboundId = StringUtils.EMPTY;
		long[] skuIdList = null;
		int[] skuCountList = null;

		if (paramMap != null) {
			if (paramMap.containsKey("skuIds")) {
				skuIds = paramMap.get("skuIds") != null ? paramMap.get("skuIds").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("skuCount")) {
				skuCount = paramMap.get("skuCount") != null ? paramMap.get("skuCount").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("sourceCode")) {
				sourceCode = paramMap.get("sourceCode") != null ? paramMap.get("sourceCode").toString()
						: StringUtils.EMPTY;
			}
			if (paramMap.containsKey("remk")) {
				remk = paramMap.get("remk") != null ? paramMap.get("remk").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("outboundId")) {
				outboundId = paramMap.get("outboundId") != null ? paramMap.get("outboundId").toString()
						: StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(skuIds)) {
				String[] arr = skuIds.split(",");
				if (arr != null && arr.length > 0) {
					skuIdList = new long[arr.length];
					for (int i = 0; i < arr.length; i++) {
						skuIdList[i] = Long.valueOf(arr[i]);
					}
				}
			}
			if (StringUtils.isNotEmpty(skuCount)) {
				String[] arr = skuCount.split(",");
				if (arr != null && arr.length > 0) {
					skuCountList = new int[arr.length];
					for (int i = 0; i < arr.length; i++) {
						// 判断是否为数字
						if (CommonUtil.isDigital(arr[i])) {
							skuCountList[i] = Integer.valueOf(arr[i]);
						} else {
							result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
							result.put(Constants.RESULT_MSG, "出库货品列表数量包含非法字符");
							return result;
						}
					}
				}
			}
			// 判断是否有值
			if (skuIdList != null && skuIdList.length > 0 && skuCountList != null && skuCountList.length > 0) {
				// sku数量的判断
				if (checkSkuCount(skuIdList, skuCountList, outboundId)) {
					try {
						// 关闭外键约束
						sqlExecuteRepository.disableForeignKeyChecks();
						if (StringUtils.isNotEmpty(outboundId)) {
							listDetail = repository.createOutboundDetailObj(skuIdList, skuCountList,
									Long.valueOf(outboundId));
							if (listDetail != null && listDetail.stream().count() > 0) {
								for (CargoOutboundDetailDo ddo : listDetail) {
									if (ddo.getFlag() == 0) {
										ddo.save();
									} else {
										ddo.update();
									}
								}
							} else {
								result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
								result.put(Constants.RESULT_MSG, "修改对象不存在");
							}
						} else {
							outboundDo = repository
									.createOutboundObj(skuIdList, skuCountList, userId, sourceCode, remk);
							if (outboundDo != null) {
								outboundDo.save();
							} else {
								result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
								result.put(Constants.RESULT_MSG, "操作失败");
							}
						}
						result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
						result.put(Constants.RESULT_MSG, "保存成功");
					} finally {
						// 打开外键约束
						sqlExecuteRepository.enableForeignKeyChecks();
					}
				} else {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "输入的出库数量存在错误，请检查重新提交！");
				}

			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "出库货品SKU列表不能为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 校验库存和输入的数量
	 * 
	 * @param skuIdList
	 * @param skuCountList
	 * @add by 2016-04-01
	 */
	private boolean checkSkuCount(long[] skuIdList, int[] skuCountList, String outboundId) {
		boolean flag = true;
		for (long c : skuCountList) {
			if (c < 1) {
				flag = false;
				break;
			}
		}
		int count = 0;
		int exitsCount = 0;
		if (flag) {
			for (int i = 0; i < skuIdList.length; i++) {
				if (StringUtils.isNotEmpty(outboundId)) {
					exitsCount = repository.queryOutCountBySkuId(skuIdList[i], Long.valueOf(outboundId));
				}
				count = repository.queryStockCountBySkuId(skuIdList[i]);
				if (skuCountList[i] + exitsCount > count) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 删除出库单明细信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	@Override
	public Map<String, Object> delOutboundOrderDetailSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (ids != null && ids.stream().count() > 0) {
				repository.deleteOutboundDetailByIds(ids);
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 删除出库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	@Override
	public Map<String, Object> delOutboundOrderSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (ids != null && ids.stream().count() > 0) {
				repository.delOutboundDetailByOutIds(ids);
				repository.deleteOutboundOrder(ids);
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 修改出库单状态-手动出库
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	@Override
	public Map<String, Object> updateOutboundStatusSerHandler(Map<String, Object> paramMap, long userId)
			throws Exception {
		result = new HashMap<String, Object>();
		List<Long> ids = null;
		String id = StringUtils.EMPTY;
		int status = -1;
		String desc = StringUtils.EMPTY;
		List<CargoOutboundOrderDo> list = null;
		List<CargoOutboundDetailDo> outDetailList = null;
		if (paramMap != null) {
			if (paramMap.containsKey("ids")) {
				id = paramMap.get("ids") != null ? paramMap.get("ids").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("desc")) {
				desc = paramMap.get("desc") != null ? paramMap.get("desc").toString() : StringUtils.EMPTY;
			}
			if (paramMap.containsKey("status")) {
				status = paramMap.get("status") != null ? Integer.valueOf(paramMap.get("status").toString()) : -1;
			}
			if (StringUtils.isNotEmpty(id)) {
				String[] arr = id.split(",");
				if (arr != null && arr.length > 0) {
					ids = new ArrayList<>();
					for (String str : arr) {
						ids.add(Long.valueOf(str));
					}
				}
			}
			if (status > -1 && ids != null && ids.stream().count() > 0) {
				list = repository.getOutboundObjByIds(ids);
				if (list != null && list.stream().count() > 0) {
					// 状态为3确认入库需判断出库数量是否大于库存现有的数量
					if (status == 3) {
						if (!checkSureCount(ids)) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
							result.put(Constants.RESULT_MSG, "存在SKU库存数量不足，不能出库请确认！");
							return result;
						}
					}
					for (CargoOutboundOrderDo outbound : list) {
						// 状态为未审核无需查询明细
						if (status != 1) {
							outDetailList = repository.queryOutDetailByOutboundId(outbound.getId());
						}
						// 状态为2提交审核判断数据是否都存在
						if (status == 2 || status == 3) {
							if (outDetailList == null || outDetailList.stream().count() == 0) {
								result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
								result.put(Constants.RESULT_MSG, "提交审核记录包含空的明细信息，请确认！");
								return result;
							} else {
								outbound.setDetail(outDetailList);
							}
						}
						// 执行审核更新操作
						outbound.update(status, desc);
						if (status == 3) {
							handleStock(status, outDetailList, userId, null, 0, null);
						}
					}
					result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
					result.put(Constants.RESULT_MSG, "操作成功");
				} else {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_FAIL_CODE);
					result.put(Constants.RESULT_MSG, "更新对象不存在");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}

		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作有误");
		}
		return result;
	}

	/**
	 * 校验确认库存数量
	 * 
	 * @param outboundIds
	 * @add by 2016-04-01
	 */
	private boolean checkSureCount(List<Long> outboundIds) {
		boolean flag = true;
		List<StockGoodsOutboundMsgVo> list = repository.getStockCountByOutIds(outboundIds);
		if (list != null && list.stream().count() > 0) {
			for (StockGoodsOutboundMsgVo svo : list) {
				if (svo.getGoodCount() > svo.getSkuCount()) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 查询树菜单
	 * 
	 * @return List<TreeListVo>
	 * @add by 2016-04-05
	 */
	@Override
	public List<TreeListVo> queryTreeMenuSer() {
		result = new HashMap<String, Object>();
		List<CargoClassifyVo> list = cargoClassify.getVoAllList(null);
		List<TreeListVo> listTree = null;
		List<TreeListVo> listNodeTree = null;
		List<TreeListVo> node = null;
		if (list != null && list.stream().count() > 0) {
			listTree = new ArrayList<>();
			listNodeTree = new ArrayList<>();
			TreeListVo vo = null;
			for (CargoClassifyVo t : list) {
				if (t.getParentId() == null || "1".equals(t.getParentId()) || "null".equals(t.getParentId())) {
					vo = new TreeListVo();
					node = packDate(t.getId(), list);
					vo.setId(t.getId());
					vo.setpId("0");
					vo.setName(t.getName());
					if (node != null && node.stream().count() > 0) {
						vo.setOpen(true);
						vo.setChildren(node);
					}
					listTree.add(vo);
				}
			}
			{
				vo = new TreeListVo();
				vo.setId("0");
				vo.setpId("-1");
				vo.setName("全部");
				vo.setOpen(true);
				if (listTree != null && listTree.stream().count() > 0) {
					vo.setChildren(listTree);
				}
			}
			listNodeTree.add(vo);

		}
		return listNodeTree;
	}

	/**
	 * 递归树节点
	 * 
	 * @add by 2016-04-05
	 */
	private List<TreeListVo> packDate(String id, List<CargoClassifyVo> list) {
		List<TreeListVo> node = null;
		List<TreeListVo> nodeCh = null;
		if (list != null && list.stream().count() > 0) {
			TreeListVo obj = null;
			node = new ArrayList<>();
			for (CargoClassifyVo dv : list) {
				if (!id.equals(dv.getId())) {
					if (id.equals(dv.getParentId())) {
						obj = new TreeListVo();
						obj.setId(dv.getId());
						obj.setName(dv.getName());
						obj.setpId(dv.getParentId());
						nodeCh = packDate(dv.getId(), list);
						if (nodeCh != null && nodeCh.stream().count() > 0) {
							obj.setOpen(true);
							obj.setChildren(nodeCh);
						}
						node.add(obj);
					}
				}
			}
		}
		return node;
	}

	/**
	 * 商品上下架
	 * 
	 * @param list
	 *            -参数列表
	 * @param userId
	 *            -用户Id
	 * @param status
	 *            (0-上架,1-下架)
	 * @return Map<String, Object>(code,msg)
	 * @add by 2016-04-05
	 */
	@Override
	public Map<String, Object> updateUpAndDownGoodsMsg(List<SkuGoodsParam> list, long userId, int status)
			throws Exception {
		// 初始化返回对象
		result = new HashMap<String, Object>();
		// 判断参数是否为空
		if (list != null && list.stream().count() > 0) {
			// 判断操作类型是否存在
			if (status == 0 || status == 1) {
				result = handleStock(status, null, userId, list, 0, null);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "status操作状态不存在");
				throw new Exception("status操作状态不存在");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "list参数为空");
			throw new Exception("list参数为空");
		}
		return result;
	}

	/**
	 * 商品上架
	 * 
	 * @param list
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-05
	 */
	private Map<String, Object> updateUpSkuGoods(List<SkuGoodsParam> list, long userId) throws Exception {
		result = new HashMap<String, Object>();
		// 库存对象
		CargoSkuStockDo stockObj = null;
		int count = 0;
		long skuId = 0;
		for (SkuGoodsParam sku : list) {
			count = Integer.valueOf(sku.getNum());
			skuId = Long.valueOf(sku.getCargoSkuId());
			if (count > 0) {
				// 通过SKUID查询库存对象
				stockObj = repository.queryBySkuId(skuId);
				// 判断对象是否存在
				if (stockObj != null && skuId == stockObj.getCargoSkuId()) {
					if (count > stockObj.getOutShelvesNo()) {
						result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
						result.put(Constants.RESULT_MSG, skuId + "上架数量大于现有的库存数量");
						throw new Exception(skuId + "上架数量大于现有的库存数量");
					} else {
						stockObj.upate(count, userId, 0);
					}
				} else {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
					result.put(Constants.RESULT_MSG, skuId + "操作对象不存在");
					throw new Exception(skuId + "操作对象不存在");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, skuId + "上架数量必须大于0");
				throw new Exception(skuId + "上架数量必须大于0");
			}
		}
		result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
		result.put(Constants.RESULT_MSG, "操作成功");
		return result;
	}

	/**
	 * 商品下架
	 * 
	 * @param list
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-05
	 */
	private Map<String, Object> updateDownSkuGoods(List<SkuGoodsParam> list, long userId) throws Exception {
		result = new HashMap<String, Object>();
		// 库存对象
		CargoSkuStockDo stockObj = null;
		int count = 0;
		long skuId = 0;
		for (SkuGoodsParam sku : list) {
			count = Integer.valueOf(sku.getNum());
			skuId = Long.valueOf(sku.getCargoSkuId());
			// 通过SKUID查询库存对象
			stockObj = repository.queryBySkuId(skuId);
			// 判断对象是否存在
			if (stockObj != null && skuId == stockObj.getCargoSkuId()) {
				// 判断操作数量是否大于库存数量
				if (count > stockObj.getOnSalesNo()) {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
					result.put(Constants.RESULT_MSG, skuId + "下架数量大于已上架未售数量");
					throw new Exception(skuId + "下架数量大于已上架未售数量");
				} else {
					if (count > 0) {
						stockObj.upate(count, userId, 1);
					}
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
				result.put(Constants.RESULT_MSG, skuId + "操作对象不存在");
				throw new Exception(skuId + "操作对象不存在");
			}
		}
		result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
		result.put(Constants.RESULT_MSG, "操作成功");
		return result;
	}

	/**
	 * 库存操作统一入口
	 * 
	 * @param status
	 * @param detail
	 * @param userId
	 * @param list
	 * @param orderId
	 * @return Map<String, Object>
	 * 
	 * @add by 2016-04-08
	 */
	private synchronized Map<String, Object> handleStock(int status, List<CargoOutboundDetailDo> detail, long userId,
			List<SkuGoodsParam> list, long orderId, Map<String, Object> paramMap) throws Exception {
		switch (status) {
		case 3:
			// 手动出库处理
			updateHandleStockOutbound(detail, userId);
			break;
		case 0:
			// 上架
			result = updateUpSkuGoods(list, userId);
			break;
		case 1:
			// 下架
			result = updateDownSkuGoods(list, userId);
			break;
		case -1:
			// 检验异常库存信息
			result = updateExceptionStock(paramMap, userId);
			break;
		case -2:
			// 修改留存
			result = updateRemainStock(paramMap, userId);
			break;
		default:
			// 订单处理
			result = updateHandleOrderStock(list, userId, status, orderId);
			break;
		}
		return result;
	}

	/**
	 * 订单操作库存
	 * 
	 * @param list
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-04-08
	 */
	private Map<String, Object> updateHandleOrderStock(List<SkuGoodsParam> list, long userId, int status, long orderId)
			throws Exception {
		result = new HashMap<>();
		// 库存对象
		CargoSkuStockDo stockObj = null;
		int count = 0;
		long skuId = 0;
		CargoOutboundOrderDo outbound = null;
		// 对库存操作
		for (SkuGoodsParam sku : list) {
			count = Integer.valueOf(sku.getNum());
			skuId = Long.valueOf(sku.getCargoSkuId());
			if (count > 0) {
				// 通过SKUID查询库存对象
				stockObj = repository.queryBySkuId(skuId);
				// 判断对象是否存在
				if (stockObj != null && skuId == stockObj.getCargoSkuId()) {
					switch (status) {
					case 4:
						// 已售未付款
						if (count > stockObj.getOnSalesNo()) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
							result.put(Constants.RESULT_MSG, skuId + "操作数量大于已上架未售数量");
							throw new Exception(skuId + "操作数量大于已上架未售数量");
						} else {
							stockObj.setUpdateTime(new Date());
							stockObj.setUpdateBy(userId);
							stockObj.setOnSalesNo(stockObj.getOnSalesNo() - count);
							if(sku.getGoodStatus() == 1){
								stockObj.setOnPayNo(stockObj.getOnPayNo() + count);
							}else{
								stockObj.setOutShelvesNo(stockObj.getOutShelvesNo() + count);
							}
							stockObj.update(4, count);
						}
						break;
					case 5:
						// 已售待发货
						if (count > stockObj.getOnPayNo()) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
							result.put(Constants.RESULT_MSG, skuId + "操作数量大于已售未付款数量");
							throw new Exception(skuId + "操作数量大于已售未付款数量");
						} else {
							stockObj.setUpdateTime(new Date());
							stockObj.setUpdateBy(userId);
							stockObj.setOnPayNo(stockObj.getOnPayNo() - count);
							if(sku.getGoodStatus() == 1){
								stockObj.setOnSendNo(stockObj.getOnSendNo() + count);
							}else{
								stockObj.setOutShelvesNo(stockObj.getOutShelvesNo() + count);
							}
							stockObj.update(5, count);
						}
						break;
					case 6:
						// 已售已发货
						if (count > stockObj.getOnSendNo()) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
							result.put(Constants.RESULT_MSG, skuId + "操作数量大于已售待发货数量");
							throw new Exception(skuId + "操作数量大于已售待发货数量");
						} else {
							stockObj.setUpdateTime(new Date());
							stockObj.setUpdateBy(userId);
							stockObj.setOnSendNo(stockObj.getOnSendNo() - count);
							stockObj.update(6, count);
						}
						break;
					case 7:
						// 待付款取消
						if (count > stockObj.getOnPayNo()) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
							result.put(Constants.RESULT_MSG, skuId + "操作数量大于已售待付款数量");
							throw new Exception(skuId + "操作数量大于已售待付款数量");
						} else {
							stockObj.setUpdateTime(new Date());
							stockObj.setUpdateBy(userId);
							stockObj.setOnPayNo(stockObj.getOnPayNo() - count);
							if(sku.getGoodStatus() == 1){
								stockObj.setOnSalesNo(stockObj.getOnSalesNo() + count);
							}else{
								stockObj.setOutShelvesNo(stockObj.getOutShelvesNo() + count);
							}
							stockObj.update(4, count);
						}
						break;
					case 8:
						// 已付款取消
						if (count > stockObj.getOnSendNo()) {
							result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
							result.put(Constants.RESULT_MSG, skuId + "操作数量大于已售待发货数量");
							throw new Exception(skuId + "操作数量大于已售待发货数量");
						} else {
							stockObj.setUpdateTime(new Date());
							stockObj.setUpdateBy(userId);
							stockObj.setOnSendNo(stockObj.getOnSendNo() - count);
							if(sku.getGoodStatus() == 1){
								stockObj.setOnSalesNo(stockObj.getOnSalesNo() + count);
							}else{
								stockObj.setOutShelvesNo(stockObj.getOutShelvesNo() + count);
							}
							stockObj.update(5, count);
						}
						break;
					default:
						throw new Exception("操作状态不存在");
					}
				} else {
					result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
					result.put(Constants.RESULT_MSG, skuId + "操作对象不存在");
					throw new Exception(skuId + "操作对象不存在");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, skuId + "操作数量必须大于0");
				throw new Exception(skuId + "操作数量必须大于0");
			}
		}
		// 出库单操作
		switch (status) {
		case 5:
			// 新增出库单
			outbound = repository.createOutboundObj(list, userId, String.valueOf(orderId), "销售出库");
			outbound.save();
			break;
		case 6:
			// 确认出库
			outbound = repository.queryOutboundBySourceNo(String.valueOf(orderId));
			outbound.update(3, "销售出库");
			break;
		case 8:
			// 取消出库
			outbound = repository.queryOutboundBySourceNo(String.valueOf(orderId));
			outbound.update(4, "销售出库");
			break;
		default:
			break;
		}
		result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
		result.put(Constants.RESULT_MSG, "操作成功");
		return result;
	}

	/**
	 * 手动操作出库单
	 * 
	 * @param detail
	 * @param userId
	 * @return void
	 * @add by 2016-04-08
	 */
	private void updateHandleStockOutbound(List<CargoOutboundDetailDo> detail, long userId) throws Exception {
		if (detail != null && detail.stream().count() > 0) {
			CargoSkuStockDo stockDo = null;
			for (CargoOutboundDetailDo outDo : detail) {
				stockDo = repository.queryBySkuId(outDo.getSkuId());
				if (stockDo != null) {
					// 出库数量大于库存数量不能执行
					if (outDo.getCount() > stockDo.getOutShelvesNo()) {
						throw new Exception("出库数量大于库存数量");
					} else {
						stockDo.setUpdateBy(userId);
						stockDo.setUpdateTime(new Date());
						stockDo.setOutShelvesNo(stockDo.getOutShelvesNo() - outDo.getCount());
						stockDo.update(6, outDo.getCount());
					}
				} else {
					throw new Exception("stockDo为空");
				}
			}
		} else {
			throw new Exception("detail为空");
		}
	}

	/**
	 * 生成出库单/修改出库单状态/锁定库存-订单出库
	 * 
	 * @param list
	 *            -skuId列表
	 * @param userId
	 *            -(0-系统操作)
	 * @param status
	 *            -(4-待付款,5-已付款,6-出库,7-待付款取消,8-退款操作)
	 * @param orderId
	 *            -订单id
	 * @return Map<String, Object>
	 * @add by 2016-04-08
	 */
	@Override
	public Map<String, Object> updateStockByOrder(List<SkuGoodsParam> list, long userId, int status, long orderId)
			throws Exception {
		result = new HashMap<>();
		try {
			// 关闭外键约束
			sqlExecuteRepository.disableForeignKeyChecks();
			if (list != null && list.stream().count() > 0) {
				result = handleStock(status, null, userId, list, orderId, null);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数list为空");
				throw new Exception("参数list为空");
			}
		} finally {
			sqlExecuteRepository.enableForeignKeyChecks();
		}
		return result;
	}
}
