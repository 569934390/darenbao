package com.club.web.deal.dao.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.ListUtils;
import com.club.web.deal.dao.base.po.Indent;
import com.club.web.deal.dao.extend.IndentExtendMapper;
import com.club.web.deal.domain.IndentDo;
import com.club.web.deal.domain.repository.IndentRepository;
import com.club.web.deal.vo.CargoStockCheckVo;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.store.vo.ExportExcelBillVo;
import com.club.web.store.vo.MerchantBillVo;

/**
 * 订单仓库接口实现类
 * 
 * @author zhuzd
 *
 */
@Repository
public class IndentRepositoryImpl implements IndentRepository {

	@Autowired
	private IndentExtendMapper indentDao;

	@Override
	public IndentDo findDoById(Long id) {
		return getDomainByPo(indentDao.selectByPrimaryKey(id));
	}

	@Override
	public List<IndentDo> findDoListByIds(String ids) {
		return getDomainListByPoList(indentDao.findListByIds(ListUtils.strToLongList(ids)));
	}

	@Override
	public List<CargoStockCheckVo> findCardoStockCheckVoByMap(Map<String, Object> con) {
		return fillCargoStockCheckVoList(indentDao.findCardoStockCheckVoByMap(con));
	}

	private List<CargoStockCheckVo> fillCargoStockCheckVoList(List<CargoStockCheckVo> voList) {
		Map<String, CargoStockCheckVo> map = new HashMap<>();
		for (CargoStockCheckVo cargoStockCheckVo : voList) {
			if (map.containsKey(cargoStockCheckVo.getId())) {
				CargoStockCheckVo vo = map.get(cargoStockCheckVo.getId());
				if (vo.getIndentStatus() != cargoStockCheckVo.getIndentStatus()) {
					switch (cargoStockCheckVo.getIndentStatus()) {
					case 1: {
						vo.setIndentNopay(vo.getIndentNopay() + cargoStockCheckVo.getIndentNumber());
						break;
					}
					case 2: {
						vo.setIndentNosend(vo.getIndentNosend() + cargoStockCheckVo.getIndentNumber());
						break;
					}
					}
				}
			} else {
				switch (cargoStockCheckVo.getIndentStatus()) {
					case 1 :{
						cargoStockCheckVo.setIndentNopay(cargoStockCheckVo.getIndentNumber());
						cargoStockCheckVo.setIndentNosend(cargoStockCheckVo.getShelveNosend());
						break;
					}case 2 :{
						cargoStockCheckVo.setIndentNopay(cargoStockCheckVo.getShelveNopay());
						cargoStockCheckVo.setIndentNosend(cargoStockCheckVo.getIndentNumber());
						break;
					}
				}
				map.put(cargoStockCheckVo.getId(), cargoStockCheckVo);
			}
		}
		List<CargoStockCheckVo> newVoList = new ArrayList<>(map.values());
		return ListUtils.isNotEmpty(newVoList) ? newVoList : new ArrayList<>();
	}

	@Override
	public void add(IndentDo indentDo) {
		indentDao.insert(getPoByDomain(indentDo));
	}

	@Override
	public void modify(IndentDo indentDo) {
		indentDao.updateByPrimaryKeySelective(getPoByDomain(indentDo));
	}
	
	@Override
	public boolean indentExist(Long indentId){
		int indent = indentDao.indentExist(indentId);
		if(indent == 0){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public List<IndentDo> findDoListByBuyerIdAndStoreId(Map<String, Object> con) {
		return getDomainListByPoList(indentDao.findListByBuyerIdAndStoreId(con));
	}

	@Override
	public List<IndentExtendVo> getShopOrderList(Map<String, Object> con) {
		List<IndentExtendVo> list = indentDao.getShopOrderList(con);
		return list;
	}

	@Override
	public List<MerchantBillVo> selectSettlementBill(Map<String, Object> map) {
		return indentDao.selectSettlementBill(map);
	}

	@Override
	public List<ExportExcelBillVo> exportExcel(Map<String, Object> map) {
		return indentDao.selectExcelBill(map);
	}

	@Override
	public int selectSettlementBillCountPage(Map<String, Object> map) {
		return indentDao.selectSettlementBillCountPage(map);
	}

	@Override
	public void delete(Long id) {
		indentDao.deleteByPrimaryKey(id);		
	}
	
	private List<IndentDo> getDomainListByPoList(List<Indent> srcs) {
		List<IndentDo> targets = new ArrayList<IndentDo>();
		if (srcs != null && srcs.size() != 0) {
			for (Indent src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}

	private Indent getPoByDomain(IndentDo src) {
		Indent target = null;
		if (src != null) {
			target = new Indent();
			target.setId(src.getId());
			target.setTradeHeadStoreId(src.getTradeHeadStoreId());
			target.setSubbranchId(src.getSubbranchId());
			target.setBuyerId(src.getBuyerId());
			target.setReferrerId(src.getReferrerId());
			target.setName(src.getName());
			target.setTotalAmount(src.getTotalAmount());
			target.setPaymentAmount(src.getPaymentAmount());
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setShipNotice(src.getShipNotice());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(src.getCarriage());
			target.setBuyerCarriage(src.getBuyerCarriage());
			target.setShipper(src.getShipper());
			target.setShipTime(src.getShipTime());
			target.setReceiver(src.getReceiver());
			target.setStatus(src.getStatus());
			target.setPayType(src.getPayType());
			target.setPayAccount(src.getPayAccount());
			target.setNeedInvoice(src.getNeedInvoice());
			target.setInvoiceName(src.getInvoiceName());
			target.setInvoiceContent(src.getInvoiceContent());
			target.setRemark(src.getRemark());
			target.setFinishTime(src.getFinishTime());
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId());
			target.setReturnId(src.getReturnId());
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
		}
		return target;
	}

	private IndentDo getDomainByPo(Indent src) {
		IndentDo target = null;
		if (src != null) {
			target = new IndentDo();
			target.setId(src.getId());
			target.setTradeHeadStoreId(src.getTradeHeadStoreId());
			target.setSubbranchId(src.getSubbranchId());
			target.setBuyerId(src.getBuyerId());
			target.setReferrerId(src.getReferrerId());
			target.setName(src.getName());
			target.setTotalAmount(src.getTotalAmount());
			target.setPaymentAmount(src.getPaymentAmount());
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setShipNotice(src.getShipNotice());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(src.getCarriage());
			target.setBuyerCarriage(src.getBuyerCarriage());
			target.setShipper(src.getShipper());
			target.setShipTime(src.getShipTime());
			target.setReceiver(src.getReceiver());
			target.setStatus(src.getStatus());
			target.setPayType(src.getPayType());
			target.setPayAccount(src.getPayAccount());
			target.setNeedInvoice(src.getNeedInvoice());
			target.setInvoiceName(src.getInvoiceName());
			target.setInvoiceContent(src.getInvoiceContent());
			target.setRemark(src.getRemark());
			target.setFinishTime(src.getFinishTime());
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId());
			target.setReturnId(src.getReturnId());
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
		}
		return target;
	}
}
