package com.club.web.store.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.store.dao.base.po.GoodReceiptAddr;
import com.club.web.store.dao.extend.GoodReceiptAddrExtendMapper;
import com.club.web.store.domain.GoodReceiptAddrDo;
import com.club.web.store.domain.repository.ReceiptAddrRepository;
import com.club.web.store.vo.GoodReceiptAddrVo;
import com.club.web.util.IdGenerator;

/**
 * 收货地址仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-18
 */
@Repository
public class ReceiptAddrRepositoryImpl implements ReceiptAddrRepository {
	@Autowired
	GoodReceiptAddrExtendMapper receiptDao;

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof GoodReceiptAddrDo) {
				GoodReceiptAddrDo receipt = (GoodReceiptAddrDo) t;
				GoodReceiptAddr receiptPo = new GoodReceiptAddr();
				BeanUtils.copyProperties(receiptPo, receipt);
				receiptDao.insert(receiptPo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	@Override
	public <T> void update(T t) throws Exception {
		if (t != null) {
			if (t instanceof GoodReceiptAddrDo) {
				GoodReceiptAddrDo receipt = (GoodReceiptAddrDo) t;
				GoodReceiptAddr receiptPo = new GoodReceiptAddr();
				BeanUtils.copyProperties(receiptPo, receipt);
				receiptDao.updateByPrimaryKey(receiptPo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	@Override
	public void updateNoExists(long id) {
		receiptDao.updateNoExists(id);
	}

	/**
	 * 创建对象
	 * 
	 * @param paramMap
	 * @return GoodReceiptAddrDo
	 * @add by 2016-04-18
	 */
	@Override
	public GoodReceiptAddrDo createReceiptAddtObj(Map<String, Object> paramMap) {
		GoodReceiptAddrDo receipt = null;
		int status = 0;
		if (paramMap != null) {
			long userId = paramMap.get("userId") != null ? Long.valueOf(paramMap.get("userId").toString()) : 0;
			receipt = new GoodReceiptAddrDo();
			if (paramMap.containsKey("status")) {
				status = paramMap.get("status") != null ? Integer.valueOf(paramMap.get("status").toString()) : 0;
			}
			List<GoodReceiptAddrDo> list = receiptDao.queryAllObj(userId);
			if (list == null || list.size() == 0) {
				status = 1;
			}
			receipt.setStatus(status);
			receipt.setId(IdGenerator.getDefault().nextId());
			receipt.setUserId(userId);
			receipt.setReceiptName(paramMap.get("receiptName") != null ? paramMap.get("receiptName").toString() : "");
			receipt.setMobile(paramMap.get("mobile") != null ? paramMap.get("mobile").toString() : "");
			receipt.setFiexdPhone(paramMap.get("fiexdPhone") != null ? paramMap.get("fiexdPhone").toString() : "");
			receipt.setReceiptEmail(paramMap.get("receiptEmail") != null ? paramMap.get("receiptEmail").toString() : "");
			receipt.setProvinceName(paramMap.get("provinceName") != null ? paramMap.get("provinceName").toString() : "");
			receipt.setProvinceCode(paramMap.get("provinceCode") != null ? paramMap.get("provinceCode").toString() : "");
			receipt.setCityCode(paramMap.get("cityCode") != null ? paramMap.get("cityCode").toString() : "");
			receipt.setCityName(paramMap.get("cityName") != null ? paramMap.get("cityName").toString() : "");
			receipt.setAreaName(paramMap.get("areaName") != null ? paramMap.get("areaName").toString() : "");
			receipt.setAreaCode(paramMap.get("areaCode") != null ? paramMap.get("areaCode").toString() : "");
			receipt.setMailbox(paramMap.get("mailbox") != null ? paramMap.get("mailbox").toString() : "");
			receipt.setDetailAddr(paramMap.get("detailAddr") != null ? paramMap.get("detailAddr").toString() : "");
			receipt.setUpdateTime(new Date());
			receipt.setCreateTime(new Date());
		}
		return receipt;
	}

	/**
	 * 根据用户Id查询地址列表
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public List<GoodReceiptAddrVo> queryReceiptAddrList(long userId) {
		List<GoodReceiptAddrVo> list = receiptDao.queryReceiptAddrList(userId);
		return list;
	}

	/**
	 * 根据id查询收货地址信息
	 * 
	 * @param id
	 * @return GoodReceiptAddrVo
	 * @add by 2016-05-13
	 */
	@Override
	public GoodReceiptAddrVo getAddrById(long id) {
		GoodReceiptAddrVo obj = receiptDao.getAddrById(id);
		return obj;
	}

	/**
	 * 删除收货地址列表
	 * 
	 * @param userId
	 * @param ids
	 * @return void
	 * @add by 2016-04-18
	 */
	public void deleteReceiptAddr(long userId, List<Long> ids) {
		receiptDao.deleteReceiptAddr(userId, ids);
	}

	/**
	 * 根据参数查询对象
	 * 
	 * @param userId
	 * @param id
	 * @return GoodReceiptAddrDo
	 * @add by 2016-04-18
	 */
	@Override
	public GoodReceiptAddrDo queryByCondition(long userId, long id) {
		GoodReceiptAddrDo receiptDo = receiptDao.queryByCondition(userId, id);
		return receiptDo;
	}

}
