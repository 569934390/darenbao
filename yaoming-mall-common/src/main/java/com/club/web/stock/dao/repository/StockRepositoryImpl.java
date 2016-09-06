package com.club.web.stock.dao.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.stock.dao.base.CargoSkuStockLogMapper;
import com.club.web.stock.dao.base.po.CargoInboundDetail;
import com.club.web.stock.dao.base.po.CargoInboundOrder;
import com.club.web.stock.dao.base.po.CargoOutboundDetail;
import com.club.web.stock.dao.base.po.CargoOutboundOrder;
import com.club.web.stock.dao.base.po.CargoSkuStock;
import com.club.web.stock.dao.base.po.CargoSkuStockLog;
import com.club.web.stock.dao.extend.CargoInboundDetailExtendMapper;
import com.club.web.stock.dao.extend.CargoInboundOrderExtendMapper;
import com.club.web.stock.dao.extend.CargoOutboundDetailExtendMapper;
import com.club.web.stock.dao.extend.CargoOutboundOrderExtendMapper;
import com.club.web.stock.dao.extend.CargoSkuStockExtendMapper;
import com.club.web.stock.domain.CargoInboundDetailDo;
import com.club.web.stock.domain.CargoInboundExtendDetailDo;
import com.club.web.stock.domain.CargoInboundOrderDo;
import com.club.web.stock.domain.CargoOutboundDetailDo;
import com.club.web.stock.domain.CargoOutboundOrderDo;
import com.club.web.stock.domain.CargoSkuStockDo;
import com.club.web.stock.domain.CargoSkuStockLogDo;
import com.club.web.stock.domain.CargoSkuStockLogExtendDo;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.vo.CargoInboundDetailVo;
import com.club.web.stock.vo.CargoInboundOrderVo;
import com.club.web.stock.vo.CargoOutboundOrderVo;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.stock.vo.CargoSkuStockVo;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.stock.vo.StockGoodsInboundMsgVo;
import com.club.web.stock.vo.StockGoodsOutboundMsgVo;
import com.club.web.util.IdGenerator;

/**
 * 库存管理
 * 
 * @author wqh
 * @add by 2016-03-20
 */
@Repository
public class StockRepositoryImpl implements StockManagerRepository {
	private Logger logger = LoggerFactory.getLogger(StockRepositoryImpl.class);
	@Autowired
	CargoInboundOrderExtendMapper inboundDao;
	@Autowired
	CargoInboundDetailExtendMapper inboundDetailDao;
	@Autowired
	CargoSkuStockExtendMapper stockDao;
	@Autowired
	CargoOutboundOrderExtendMapper outboundDao;
	@Autowired
	CargoOutboundDetailExtendMapper outboundDetailDao;
	@Autowired
	CargoSkuStockLogMapper stockLogDao;

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-03-31
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof CargoOutboundOrderDo) {
				CargoOutboundOrderDo out = (CargoOutboundOrderDo) t;
				CargoOutboundOrder po = new CargoOutboundOrder();
				BeanUtils.copyProperties(po, out);
				po.setOutboundTime(null);
				outboundDao.insert(po);
			} else if (t instanceof CargoSkuStockDo) {
				CargoSkuStockDo stockDo = (CargoSkuStockDo) t;
				CargoSkuStock stockPo = new CargoSkuStock();
				BeanUtils.copyProperties(stockPo, stockDo);
				stockDao.insert(stockPo);
			} else if (t instanceof CargoInboundOrderDo) {
				CargoInboundOrderDo inboundObj = (CargoInboundOrderDo) t;
				CargoInboundOrder inboundPo = new CargoInboundOrder();
				BeanUtils.copyProperties(inboundPo, inboundObj);
				inboundPo.setInboundTime(null);
				inboundDao.insert(inboundPo);
			} else if (t instanceof CargoInboundDetailDo) {
				CargoInboundDetailDo detailObj = (CargoInboundDetailDo) t;
				CargoInboundDetail detailPo = new CargoInboundDetail();
				BeanUtils.copyProperties(detailPo, detailObj);
				inboundDetailDao.insert(detailPo);
			} else if (t instanceof CargoOutboundDetailDo) {
				CargoOutboundDetailDo detailOut = (CargoOutboundDetailDo) t;
				CargoOutboundDetail po = new CargoOutboundDetail();
				BeanUtils.copyProperties(po, detailOut);
				outboundDetailDao.insert(po);
			} else if (t instanceof CargoSkuStockLogDo) {
				CargoSkuStockLogDo stockLog = (CargoSkuStockLogDo) t;
				CargoSkuStockLog stockPo = new CargoSkuStockLog();
				BeanUtils.copyProperties(stockPo, stockLog);
				stockLogDao.insert(stockPo);
			} else {
				throw new Exception("不存在操作的对象实例");
			}
		} else {
			throw new NullPointerException("操作对象为空");
		}
	}

	/**
	 * 根据条件查询库存对象
	 * 
	 * @param id
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @return CargoSkuStockDo
	 * @add by 2016-03-29
	 */
	@Override
	public CargoSkuStockDo queryStockById(long id) throws Exception {
		CargoSkuStockDo stockDo = null;
		CargoSkuStock stock = stockDao.selectByPrimaryKey(id);
		if (stock != null) {
			stock.setUpdateTime(stock.getCreateTime());
			stockDo = new CargoSkuStockDo();
			BeanUtils.copyProperties(stockDo, stock);
		}
		return stockDo;
	}

	/**
	 * 根据条件查询库存信息
	 * 
	 * @param nodeList
	 * @param brand-品牌Id
	 * @param matchParam-模糊参数
	 * @return List<CargoSkuStockVo>
	 * @add by 2016-03-29
	 */
	@Override
	public List<CargoSkuStockVo> queryStockMsg(List<Long> nodeList, long brand, String matchParam, int startIndex,
			int pageSize) {
		List<CargoSkuStockVo> list = stockDao.queryStockMsg(nodeList, brand, matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 查询存在异常的库存信息
	 * 
	 * @param ids
	 * @return List<CargoSkuStockVo>
	 * @add by 2016-03-30
	 */
	@Override
	public List<CargoSkuStockVo> queryNormalStockMsgByIds(List<Long> ids) {
		List<CargoSkuStockVo> list = stockDao.queryNormalStockMsgByIds(ids);
		return list;
	}

	/**
	 * 根据条件查询库存总条数
	 * 
	 * @param nodeList
	 * @param brand-品牌Id
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-29
	 */
	@Override
	public int queryStockMsgTotal(List<Long> nodeList, long brand, String matchParam) {
		int total = stockDao.queryStockMsgTotal(nodeList, brand, matchParam);
		return total;
	}

	/**
	 * 创建库存对象
	 * 
	 * @param skuId
	 * @param count
	 * @param userId
	 * @return CargoSkuStockDo
	 * @add by 2016-03-29
	 */
	@Override
	public CargoSkuStockDo createStockObj(long skuId, int count, long userId) {
		CargoSkuStockDo stock = new CargoSkuStockDo();
		CargoSkuStock stockPo = stockDao.queryBySkuId(skuId);
		// 判断对象是否已经存在
		if (stockPo == null) {
			stock.setId(IdGenerator.getDefault().nextId());
			stock.setCargoSkuId(skuId);
			stock.setCreateBy(userId);
			stock.setCreateTime(new Date());
			stock.setUpdateTime(new Date());
			// 未上架数量
			stock.setOutShelvesNo(count);
			// 已售未付款
			stock.setOnPayNo(0);
			// 已上架未售
			stock.setOnSalesNo(0);
			// 已售未发货
			stock.setOnSendNo(0);
			stock.setRemainCount(0);
			stock.setFlag(0);
		} else {
			stock.setFlag(1);
			stock.setId(stockPo.getId());
			stock.setCargoSkuId(stockPo.getCargoSkuId());
			stock.setCreateBy(stockPo.getCreateBy());
			stock.setCreateTime(stockPo.getCreateTime());
			stock.setOnPayNo(stockPo.getOnPayNo());
			stock.setOnSalesNo(stockPo.getOnSalesNo());
			stock.setOnSendNo(stockPo.getOnSendNo());
			stock.setRemainCount(stockPo.getRemainCount());
			stock.setUpdateBy(userId);
			stock.setUpdateTime(new Date());
			stock.setOutShelvesNo(stockPo.getOutShelvesNo() + count);
		}
		return stock;
	}

	/**
	 * 创建库存日志对象
	 * 
	 * @param stock
	 * @param status
	 * @param inboundId
	 * @param count
	 * @return CargoSkuStockLogDo
	 * @add by 2016-04-14
	 */
	@Override
	public CargoSkuStockLogDo createStockLogObj(CargoSkuStockDo stock, int status, long inboundId, int count) {
		CargoSkuStockLogDo stockLog = null;
		if (stock != null) {
			stockLog = new CargoSkuStockLogExtendDo();
			stockLog.setId(IdGenerator.getDefault().nextId());
			stockLog.setCargoSkuId(stock.getCargoSkuId());
			stockLog.setOutShelvesNo(stock.getOutShelvesNo());
			stockLog.setOnSalesNo(stock.getOnSalesNo());
			stockLog.setOnPayNo(stock.getOnPayNo());
			stockLog.setOnSendNo(stock.getOnSendNo());
			stockLog.setInboundId(inboundId);
			stockLog.setCreateTime(new Date());
			stockLog.setCreateBy(stock.getCreateBy());
			stockLog.setUpdateCount(count);
			stockLog.setUpdateStatus(status);
		}
		return stockLog;
	}

	/**
	 * 保存库存信息
	 * 
	 * @param obj
	 * @return void
	 * @add by 2016-03-29
	 */
	@Override
	public synchronized void updateStock(CargoSkuStockDo obj) throws IllegalAccessException, InvocationTargetException {
		CargoSkuStock stockPo = new CargoSkuStock();
		BeanUtils.copyProperties(stockPo, obj);
		stockDao.updateByPrimaryKey(stockPo);
	}

	/**
	 * 更新出库明细信息
	 * 
	 * @param obj
	 * @return void
	 * @add by 2016-03-29
	 */
	@Override
	public void updateOutbound(CargoOutboundDetailDo obj) throws Exception {
		CargoOutboundDetail po = new CargoOutboundDetail();
		BeanUtils.copyProperties(po, obj);
		outboundDetailDao.updateByPrimaryKey(po);
	}

	/**
	 * 更新入库单状态
	 * 
	 * @param inboundObj
	 * @return void
	 * @add by 2016-03-26
	 */
	@Override
	public void updateInboundOrderStatus(CargoInboundOrderDo inboundObj)
			throws IllegalAccessException, InvocationTargetException {
		CargoInboundOrder inboundPo = new CargoInboundOrder();
		BeanUtils.copyProperties(inboundPo, inboundObj);
		if (inboundPo.getStatus() != 3) {
			inboundPo.setInboundTime(null);
		}
		inboundDao.updateByPrimaryKey(inboundPo);
	}

	/**
	 * 更新出库单状态
	 * 
	 * @param inboundObj
	 * @return void
	 * @add by 2016-04-01
	 */
	@Override
	public void updateOutbound(CargoOutboundOrderDo outbound) throws Exception {
		CargoOutboundOrder po = new CargoOutboundOrder();
		BeanUtils.copyProperties(po, outbound);
		if (outbound.getStatus() != 3) {
			po.setOutboundTime(null);
		}
		outboundDao.updateByPrimaryKey(po);
	}

	/**
	 * 根据Id查询入库单对象信息
	 * 
	 * @param ids
	 * @return List<CargoInboundOrderDo>
	 * @add by 2016-03-26
	 */
	@Override
	public List<CargoInboundOrderDo> getInboundObjByIds(List<Long> ids) {
		List<CargoInboundOrderDo> list = inboundDao.getInboundObjByIds(ids);
		return list;
	}

	/**
	 * 更新入库单明细信息
	 * 
	 * @param detailObj
	 * @return void
	 * @add by 2016-03-26
	 */
	@Override
	public void updateInboundDetail(CargoInboundDetailDo detailObj)
			throws IllegalAccessException, InvocationTargetException {
		CargoInboundDetail detailPo = new CargoInboundDetail();
		BeanUtils.copyProperties(detailPo, detailObj);
		inboundDetailDao.updateByPrimaryKey(detailPo);
	}

	/**
	 * 创建入库单对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param userId-用户Id
	 * @param sourceCode-来源编号
	 * @param remk-备注
	 * @return CargoInboundOrderDo
	 * @add by 2016-03-26
	 */
	@Override
	public CargoInboundOrderDo createInboundObj(long[] skuIdList, int[] skuCountList, long userId, String sourceCode,
			String remk) {
		CargoInboundOrderDo inboundObj = null;
		List<CargoInboundDetailDo> detailList = null;
		if (skuIdList != null && skuCountList != null && skuCountList.length > 0 && skuIdList.length > 0) {
			inboundObj = new CargoInboundOrderDo();
			inboundObj.setId(IdGenerator.getDefault().nextId());
			inboundObj.setCreateTime(new Date());
			inboundObj.setSubTime(new Date());
			inboundObj.setInboundTime(new Date());
			inboundObj.setCreateBy(userId);
			inboundObj.setRemarks(remk);
			inboundObj.setSourceNo(sourceCode);
			inboundObj.setStatus(1);
			detailList = new ArrayList<CargoInboundDetailDo>();
			CargoInboundDetailDo detailObj = null;
			for (int i = 0; i < skuIdList.length; i++) {
				detailObj = new CargoInboundExtendDetailDo();
				detailObj.setId(IdGenerator.getDefault().nextId());
				detailObj.setInboundId(inboundObj.getId());
				detailObj.setSkuId(skuIdList[i]);
				detailObj.setCount(skuCountList[i]);
				detailList.add(detailObj);
			}
			inboundObj.setDetail(detailList);
		}
		return inboundObj;
	}

	/**
	 * 创建出库单对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param userId
	 * @param sourceCode-来源编号
	 * @param remk-备注
	 * @return CargoOutboundOrderDo
	 * 
	 * @add by 2016-03-26
	 */
	@Override
	public CargoOutboundOrderDo createOutboundObj(long[] skuIdList, int[] skuCountList, long userId, String sourceCode,
			String remk) {
		CargoOutboundOrderDo outbound = null;
		List<CargoOutboundDetailDo> list = null;
		if (skuIdList != null && skuCountList != null && skuCountList.length > 0 && skuIdList.length > 0) {
			outbound = new CargoOutboundOrderDo();
			outbound.setId(IdGenerator.getDefault().nextId());
			outbound.setCreateTime(new Date());
			outbound.setSubTime(new Date());
			outbound.setOutboundTime(new Date());
			outbound.setStatus(1);
			outbound.setRemarks(remk);
			outbound.setSourceNo(sourceCode);
			outbound.setOutboundType(1);
			outbound.setCreateBy(userId);
			list = new ArrayList<>();
			CargoOutboundDetailDo detail = null;
			for (int i = 0; i < skuIdList.length; i++) {
				detail = new CargoOutboundDetailDo();
				detail.setId(IdGenerator.getDefault().nextId());
				detail.setOutboundId(outbound.getId());
				detail.setSkuId(skuIdList[i]);
				detail.setCount(skuCountList[i]);
				list.add(detail);
			}
			outbound.setDetail(list);
		}
		return outbound;
	}

	/**
	 * 创建出库单对象
	 * 
	 * @param list
	 * @param userId
	 * @param orderId-来源编号
	 * @param remk-备注
	 * @return CargoOutboundOrderDo
	 * @add by 2016-03-26
	 */
	@Override
	public CargoOutboundOrderDo createOutboundObj(List<SkuGoodsParam> list, long userId, String orderId, String remk) {
		CargoOutboundOrderDo outbound = null;
		List<CargoOutboundDetailDo> listDetail = null;
		if (list != null && list.stream().count() > 0) {
			outbound = new CargoOutboundOrderDo();
			outbound.setId(IdGenerator.getDefault().nextId());
			outbound.setCreateTime(new Date());
			outbound.setSubTime(new Date());
			outbound.setOutboundTime(new Date());
			outbound.setStatus(2);
			outbound.setRemarks(remk);
			outbound.setSourceNo(orderId);
			outbound.setOutboundType(0);
			outbound.setCreateBy(userId);
			listDetail = new ArrayList<>();
			CargoOutboundDetailDo detail = null;
			for (SkuGoodsParam sk : list) {
				detail = new CargoOutboundDetailDo();
				detail.setId(IdGenerator.getDefault().nextId());
				detail.setOutboundId(outbound.getId());
				detail.setSkuId(Long.valueOf(sk.getCargoSkuId()));
				detail.setCount(Integer.valueOf(sk.getNum()));
				listDetail.add(detail);
			}
			outbound.setDetail(listDetail);
		}
		return outbound;
	}

	/**
	 * 创建入库单明细对象
	 * 
	 * @param skuIdList-skuId
	 * 
	 * @param skuCountList-sku数量
	 * 
	 * @param inboundId
	 * 
	 * @return List<CargoInboundDetailDo>
	 * 
	 * @add by 2016-03-26
	 */
	@Override
	public List<CargoInboundDetailDo> createInboundDetailObj(long[] skuIdList, int[] skuCountList, long inboundId) {
		List<CargoInboundDetailDo> list = new ArrayList<>();
		CargoInboundDetailDo detailObj = null;
		CargoInboundDetail detailPo = null;
		for (int i = 0; i < skuIdList.length; i++) {
			detailPo = inboundDetailDao.selectBySkuId(skuIdList[i], inboundId);
			detailObj = new CargoInboundExtendDetailDo();
			if (detailPo != null) {
				detailObj.setId(detailPo.getId());
				detailObj.setCount(detailPo.getCount() + skuCountList[i]);
				detailObj.setInboundId(detailPo.getInboundId());
				detailObj.setSkuId(detailPo.getSkuId());
				detailObj.setFlag(1);
			} else {
				detailObj.setId(IdGenerator.getDefault().nextId());
				detailObj.setInboundId(inboundId);
				detailObj.setSkuId(skuIdList[i]);
				detailObj.setCount(skuCountList[i]);
				detailObj.setFlag(0);
			}
			list.add(detailObj);
		}
		return list;
	}

	/**
	 * 创建出库单明细对象
	 * 
	 * @param skuIdList-skuId
	 * 
	 * @param skuCountList-sku数量
	 * 
	 * @param outboundId
	 * 
	 * @return List<CargoOutboundDetailDo>
	 * 
	 * @add by 2016-03-26
	 */
	@Override
	public List<CargoOutboundDetailDo> createOutboundDetailObj(long[] skuIdList, int[] skuCountList, long outboundId) {
		List<CargoOutboundDetailDo> list = new ArrayList<>();
		CargoOutboundDetail po = null;
		CargoOutboundDetailDo obj = null;
		for (int i = 0; i < skuIdList.length; i++) {
			po = outboundDetailDao.selectBySkuAndOutId(skuIdList[i], outboundId);
			obj = new CargoOutboundDetailDo();
			if (po != null) {
				obj.setId(po.getId());
				obj.setCount(po.getCount() + skuCountList[i]);
				obj.setOutboundId(po.getOutboundId());
				obj.setSkuId(po.getSkuId());
				obj.setFlag(1);
			} else {
				obj.setId(IdGenerator.getDefault().nextId());
				obj.setOutboundId(outboundId);
				obj.setSkuId(skuIdList[i]);
				obj.setCount(skuCountList[i]);
				obj.setFlag(0);
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 查询入库单-根据查询条件
	 * 
	 * @param status-状态
	 * @param matchParam-模糊查询
	 * @param startIndex-开始记录数
	 * @param pageSize-页面数据大小
	 * @return List<CargoInboundOrderVo>
	 * @add by 2016-03-23
	 */
	@Override
	public List<CargoInboundOrderVo> queryInboundOrderList(int status, String matchParam, int startIndex,
			int pageSize) {
		List<CargoInboundOrderVo> record = inboundDao.queryInboundOrderList(status, matchParam, startIndex, pageSize);
		return record;
	}

	/**
	 * 查询入库单总数-根据查询条件
	 * 
	 * @param status-状态
	 * @param matchParam-模糊查询
	 * @return int
	 * @add by 2016-03-23
	 */
	@Override
	public int queryInboundOrderTotal(int status, String matchParam) {
		int total = inboundDao.queryInboundOrderTotal(status, matchParam);
		return total;
	}

	/**
	 * 查询入库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param inboundId-入库单
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-24
	 */
	@Override
	public int queryInboundDetailTotal(List<Long> nodeList, long brand, long inboundId, String matchParam) {
		int total = inboundDao.queryInboundOrderDetailTotal(nodeList, brand, inboundId, matchParam);
		return total;
	}

	/**
	 * 查询出库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * 
	 * @param brand-品牌Id
	 * 
	 * @param outboundId-出库单
	 * 
	 * @param matchParam-模糊参数
	 * 
	 * @return int
	 * 
	 * @add by 2016-03-24
	 */
	@Override
	public int queryOutboundDetailTotal(List<Long> nodeList, long brand, long outboundId, String matchParam) {
		int total = outboundDetailDao.queryOutboundDetailTotal(nodeList, brand, outboundId, matchParam);
		return total;
	}

	/**
	 * 查询出库单明细信息-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * 
	 * @param brand-品牌Id
	 * 
	 * @param outboundId-出库单
	 * 
	 * @param matchParam-模糊参数
	 * 
	 * @param startIndex
	 * 
	 * @param pageSize
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-03-24
	 */
	@Override
	public List<StockGoodsOutboundMsgVo> queryOutboundDetail(List<Long> nodeList, long brand, long outboundId,
			String matchParam, int startIndex, int pageSize) {
		List<StockGoodsOutboundMsgVo> list = outboundDetailDao.queryOutboundDetail(nodeList, brand, outboundId,
				matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 添加入库-查询货品信息总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-24
	 */
	@Override
	public int queryGoodsMsgTotal(List<Long> nodeList, String matchParam) {
		int total = 0;
		try {
			total = inboundDao.queryGoodsMsgTotal(nodeList, matchParam);
		} catch (Exception e) {
			logger.error("添加入库-查询货品信息总数-根据查询条件异常<queryGoodsMsgTotal>:", e);
		}
		return total;
	}

	/**
	 * 添加入库-查询货品Sku信息总数
	 * 
	 * @param goodsIdList
	 * @return int
	 * @add by 2016-03-25
	 */
	@Override
	public int queryGoodsSkuMsgTotal(List<Long> goodsIdList) {
		int total = 0;
		try {
			total = inboundDao.queryGoodsSkuMsgTotal(goodsIdList);
		} catch (Exception e) {
			logger.error("添加入库-查询货品Sku信息总数异常<queryGoodsSkuMsgTotal>:", e);
		}
		return total;
	}

	/**
	 * 根据SKU查询规格
	 * 
	 * @param skuId
	 * @return List<CargoSkuItemVo>
	 * @add by 2016-03-24
	 */
	@Override
	public List<CargoSkuItemVo> queryGoodsSpecList(long skuId) {
		List<CargoSkuItemVo> list = null;
		try {
			list = inboundDao.queryGoodsSpecList(skuId);
		} catch (Exception e) {
			logger.error("根据sku查询规格异常<queryGoodsSpecList>:", e);
		}
		return list;
	}

	/**
	 * 查询入库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param inboundId-入库单
	 * @param matchParam-模糊参数
	 * @param startIndex-开始记录数
	 * @param pageSize-每页几条
	 * @return List<StockGoodsInboundMsgVo>
	 * 
	 * @add by 2016-03-24
	 */
	@Override
	public List<StockGoodsInboundMsgVo> queryInboundorderDetailList(List<Long> nodeList, long brand, long inboundId,
			String matchParam, int startIndex, int pageSize) {
		List<StockGoodsInboundMsgVo> list = inboundDao.queryInboundorderDetailList(nodeList, brand, inboundId,
				matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 添加入库/出库-查询货品信息
	 * 
	 * @param nodeList-分类Id
	 * @param matchParam-模糊参数
	 * @param startIndex-开始记录数
	 * @param pageSize-每页几条
	 * @return List<StockGoodsInboundMsgVo>
	 * @add by 2016-03-24
	 */
	@Override
	public List<StockGoodsInboundMsgVo> queryGoodsMsg(List<Long> nodeList, String matchParam, int startIndex,
			int pageSize) {
		List<StockGoodsInboundMsgVo> list = inboundDao.queryGoodsMsg(nodeList, matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 添加入库-查询货品Sku信息
	 * 
	 * @param goodsIdList-货品Id
	 * @param startIndex
	 * @param pageSize
	 * @return List<StockGoodsInboundMsgVo>
	 * @add by 2016-03-25
	 */
	@Override
	public List<StockGoodsInboundMsgVo> queryGoodsSkuMsg(List<Long> goodsIdList, int startIndex, int pageSize) {
		List<StockGoodsInboundMsgVo> list = inboundDao.queryGoodsSkuMsg(goodsIdList, startIndex, pageSize);
		return list;
	}

	/**
	 * 删除入库单信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-03-27
	 */
	@Override
	public boolean deleteInboundOrder(List<Long> ids) {
		inboundDao.deleteInboundStock(ids);
		return true;
	}

	/**
	 * 删除入库单详细信息
	 * 
	 * @param inboundIds
	 * @return boolean
	 * @add by 2016-03-27
	 */
	@Override
	public boolean deleteInboundOrderDetail(List<Long> inboundIds) {
		inboundDetailDao.deleteInboundDetail(inboundIds);
		return true;
	}

	/**
	 * 删除入库单详细信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-03-27
	 */
	@Override
	public boolean deleteInboundOrderDetailByIds(List<Long> ids) {
		inboundDetailDao.deleteInboundDetailByIds(ids);
		return true;
	}

	/**
	 * 根据入库单id查询shuId,count列表
	 * 
	 * @param inboundId
	 * @return List<CargoInboundDetailVo>
	 * @add by 2016-03-29
	 */
	@Override
	public List<CargoInboundDetailVo> queryByInboundId(long inboundId) {
		List<CargoInboundDetailVo> list = inboundDetailDao.querySkuIdsByInboundId(inboundId);
		return list;
	}

	/**
	 * 查询出库单信息总数
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-03-31
	 */
	@Override
	public int queryOutboundOrderListTotal(int status, String matchParam) {
		int total = outboundDao.queryOutboundOrderListTotal(status, matchParam);
		return total;
	}

	/**
	 * 查询出库单信息
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return List<CargoOutboundOrderVo>
	 * @add by 2016-03-31
	 */
	@Override
	public List<CargoOutboundOrderVo> queryOutboundOrderList(int status, String matchParam, int startIndex,
			int pageSize) {
		List<CargoOutboundOrderVo> list = outboundDao.queryOutboundOrderList(status, matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 添加出库-查询货品Sku信息总数
	 * 
	 * @param goodsIdList
	 * @return int
	 * @add by 2016-04-01
	 */
	@Override
	public int queryOutboundSkuMsgTotal(List<Long> goodsIdList) {
		int total = 0;
		try {
			total = outboundDetailDao.queryOutboundSkuMsgTotal(goodsIdList);
		} catch (Exception e) {
			logger.error("添加出库-查询货品Sku信息总数异常<queryOutboundSkuMsgTotal>:", e);
		}
		return total;
	}

	/**
	 * 添加出库-查询货品Sku信息
	 * 
	 * @param goodsIdList
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-04-01
	 */
	@Override
	public List<StockGoodsOutboundMsgVo> queryOutboundSkuMsg(List<Long> goodsIdList, int startIndex, int pageSize) {
		List<StockGoodsOutboundMsgVo> list = outboundDetailDao.queryOutboundSkuMsg(goodsIdList, startIndex, pageSize);
		return list;
	}

	/**
	 * 删除出库单详细信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-04-01
	 */
	@Override
	public boolean deleteOutboundDetailByIds(List<Long> ids) {
		outboundDetailDao.deleteOutboundDetailByIds(ids);
		return true;
	}

	/**
	 * 删除出库单详细信息根据外键
	 * 
	 * @param outboundIds
	 * @return boolean
	 * @add by 2016-04-01
	 */
	@Override
	public boolean delOutboundDetailByOutIds(List<Long> outboundIds) {
		outboundDetailDao.delOutboundDetailByOutIds(outboundIds);
		return true;
	}

	/**
	 * 删除出库单信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-04-01
	 */
	@Override
	public boolean deleteOutboundOrder(List<Long> ids) {
		outboundDao.deleteOutboundOrder(ids);
		return true;
	}

	/**
	 * 根据Id查询出库单对象信息
	 * 
	 * @param ids
	 * @return List<CargoOutboundOrderDo>
	 * @add by 2016-04-01
	 */
	@Override
	public List<CargoOutboundOrderDo> getOutboundObjByIds(List<Long> ids) {
		List<CargoOutboundOrderDo> list = outboundDao.getOutboundObjByIds(ids);
		return list;
	}

	/**
	 * 根据来源单号查询出库单
	 * 
	 * @param sourceNo
	 * @return List<CargoOutboundDetailDo>
	 * @add by 2016-04-08
	 */
	@Override
	public CargoOutboundOrderDo queryOutboundBySourceNo(String sourceNo) {
		CargoOutboundOrderDo outbound = outboundDao.queryOutboundBySourceNo(sourceNo);
		return outbound;
	}

	/**
	 * 根据skuId查询库存数量
	 * 
	 * @param inboundObj
	 * @return int
	 * @add by 2016-03-26
	 */
	@Override
	public int queryStockCountBySkuId(long skuId) {
		int count = stockDao.queryStockCountBySkuId(skuId);
		return count;
	}

	/**
	 * 根据skuId查询总库存量
	 * 
	 * @param skuId
	 * @return int
	 * @add by 2016-04-09
	 */
	@Override
	public int queryStockTotalBySkuId(long skuId) {
		int count = stockDao.queryStockTotalBySkuId(skuId);
		return count;
	}

	/**
	 * 根据cargoId查询总库存量
	 * 
	 * @param cargoId
	 * @return int
	 * @add by 2016-04-09
	 */
	@Override
	public int queryStockTotalByCargoId(long cargoId) {
		int count = stockDao.queryStockTotalByCargoId(cargoId);
		return count;
	}

	/**
	 * 根据skuId删除库存
	 * 
	 * @param skuId
	 * @return void
	 * @add by 2016-04-09
	 */
	@Override
	public void deleteStockBySkuId(long skuId) {
		stockDao.deleteStockBySkuId(skuId);
	}

	/**
	 * 根据cargoId删除库存
	 * 
	 * @param skuId
	 * @return void
	 * @add by 2016-04-09
	 */
	@Override
	public void deleteStockByCargoId(long cargoId) {
		stockDao.deleteStockByCargoId(cargoId);
	}

	/**
	 * 根据skuId查询出库数量
	 * 
	 * @param skuId
	 * @return int
	 * @add by 2016-04-04
	 */
	@Override
	public int queryOutCountBySkuId(long skuId, long outboundId) {
		int count = 0;
		CargoOutboundDetail detail = outboundDetailDao.selectBySkuAndOutId(skuId, outboundId);
		if (detail != null) {
			count = detail.getCount();
		}
		return count;
	}

	/**
	 * 根据出库单Id查询库存和出库单数量
	 * 
	 * @param outboundIds
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-04-01
	 */
	@Override
	public List<StockGoodsOutboundMsgVo> getStockCountByOutIds(List<Long> outboundIds) {
		List<StockGoodsOutboundMsgVo> list = outboundDetailDao.getStockCountByOutIds(outboundIds);
		return list;
	}

	/**
	 * 通过SKUID查询库存对象
	 * 
	 * @param skuId
	 * @return CargoSkuStockDo
	 * @add by 2016-04-01
	 */
	@Override
	public CargoSkuStockDo queryBySkuId(long skuId) throws Exception {
		CargoSkuStockDo stock = null;
		CargoSkuStock stockPo = stockDao.queryBySkuId(skuId);
		if (stockPo != null) {
			stockPo.setUpdateTime(stockPo.getCreateTime());
			stock = new CargoSkuStockDo();
			BeanUtils.copyProperties(stock, stockPo);
		}
		return stock;
	}

	/**
	 * 根据outboundId查询出库单明细信息
	 * 
	 * @param outboundId
	 * @return List<CargoOutboundDetailDo>
	 * @add by 2016-04-04
	 */
	@Override
	public List<CargoOutboundDetailDo> queryOutDetailByOutboundId(long outboundId) {
		List<CargoOutboundDetailDo> list = outboundDetailDao.queryOutDetailByOutboundId(outboundId);
		return list;
	}

}
