package com.club.web.deal.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.service.U8InterfaceService;
import com.club.web.deal.vo.IndentListVo;
import com.club.web.deal.vo.IndentU8Vo;
import com.club.web.finance.dao.extend.FinanceAccountspayExtendMapper;
import com.club.web.finance.service.FinanceAccountspayItemService;
import com.club.web.finance.service.FinanceAccountspayService;
import com.club.web.finance.vo.FinanceAbnormal;
import com.club.web.finance.vo.FinanceAccountspayItemVo;
import com.club.web.finance.vo.FinanceAccountspayVo;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.U8EaiUtil;

/**
 * @Title: U8InterfaceServiceImpl.java
 * @Package com.club.web.deal.service.impl
 * @Description: TODO(U8对接)
 * @author 柳伟军
 * @date 2016年6月30日 下午5:20:51
 * @version V1.0
 */
@Service("u8InterfaceService")
public class U8InterfaceServiceImpl implements U8InterfaceService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public SubbranchService subbranchService;

	@Autowired
	public IndentService indentService;

	@Autowired
	public FinanceAccountspayService financeAccountspayService;
	
	@Autowired
	public FinanceAccountspayExtendMapper financeAccountspayExtendDao;

	@Autowired
	public FinanceAccountspayItemService financeAccountspayItemService;

	/**
	 * 收款单
	 */
	@Override
	public void account(String orderId) {
		logger.error("u8InterfaceService account");
		try {
			FinanceAccountspayVo financeAccountspayOldVo = financeAccountspayService.getVoByOrderId(orderId);
			if (financeAccountspayOldVo == null) {
				IndentU8Vo indentU8Vo = indentService.findU8VoById(Long.parseLong(orderId));
				if (indentU8Vo != null && indentU8Vo.getSubbranchId() != null
						&& !"".equals(indentU8Vo.getSubbranchId())) {
					SubbranchVo subbranchVo = subbranchService
							.selectByPrimaryKey(Long.parseLong(indentU8Vo.getSubbranchId()));
					indentU8Vo.setShopCode(subbranchVo.getNumber());
					indentU8Vo.setShopName(subbranchVo.getName());
					indentU8Vo.setDepartmentCode(subbranchVo.getDepartmentNum());
				}

				BigDecimal amount = new BigDecimal("0");
				for (IndentListVo indentListVo : indentU8Vo.getIndentList()) {
					BigDecimal supplyPrice = new BigDecimal(
							indentListVo.getSupplyPrice() == null ? "0" : indentListVo.getSupplyPrice() + "");
					BigDecimal num = new BigDecimal(
							indentListVo.getNumber() == null ? "0" : indentListVo.getNumber() + "");
					amount = amount.add(supplyPrice.multiply(num));
				}
				indentU8Vo.setAmount(amount);

				FinanceAccountspayVo financeAccountspayVo = new FinanceAccountspayVo();
				financeAccountspayVo.setOrderid(indentU8Vo.getId());
				financeAccountspayVo.setStatue(0);
				financeAccountspayVo.setVouchtype("48");
				financeAccountspayVo.setVouchdate(new Date());
				financeAccountspayVo.setCustomerid(indentU8Vo.getSubbranchId());
				financeAccountspayVo.setCustomercode(indentU8Vo.getShopCode());
				financeAccountspayVo.setCustomername(indentU8Vo.getShopName());
				financeAccountspayVo.setDepartmentcode(indentU8Vo.getDepartmentCode());
				financeAccountspayVo.setDigest(indentU8Vo.getBuyerRemark());
				financeAccountspayVo.setBalancecode("现金");
				financeAccountspayVo.setBalanceitemcode("10120701");
				financeAccountspayVo.setForeigncurrency("人民币");
				financeAccountspayVo.setCurrencyrate(new BigDecimal("1.00"));
				financeAccountspayVo.setAmount(indentU8Vo.getAmount());
				financeAccountspayVo.setOriginalamount(indentU8Vo.getAmount());
				financeAccountspayVo.setOperator("demo");
				financeAccountspayVo.setFlag("AR");
				financeAccountspayVo.setNote(indentU8Vo.getBuyerRemark());

				financeAccountspayService.saveOrUpdate(financeAccountspayVo);

				FinanceAccountspayItemVo financeAccountspayItemVo = new FinanceAccountspayItemVo();
				financeAccountspayItemVo.setMainid(financeAccountspayVo.getId());
				financeAccountspayItemVo.setType(0);
				financeAccountspayItemVo.setCustomerid(indentU8Vo.getSubbranchId());
				financeAccountspayItemVo.setCustomercode(indentU8Vo.getShopCode());
				financeAccountspayItemVo.setCustomername(indentU8Vo.getShopName());
				financeAccountspayItemVo.setDepartmentcode(indentU8Vo.getDepartmentCode());
				financeAccountspayItemVo.setAmount(indentU8Vo.getAmount());
				financeAccountspayItemVo.setOriginalamount(indentU8Vo.getAmount());
				financeAccountspayItemVo.setItemcode("112201");
				financeAccountspayItemService.saveOrUpdate(financeAccountspayItemVo);
				logger.error("u8InterfaceService account msg :{}",
						financeAccountspayVo.getId() + "创建成功，收款单号为：" + orderId);

				try {
					String u8OrderId = U8EaiUtil.U8EaiSalesOrder(indentU8Vo,financeAccountspayVo.getId());
					if (u8OrderId == null || "".equals(u8OrderId)) {
						logger.error("u8InterfaceService account U8对接销售单失败 :{}", "返回信息为空！");
					} else {
						financeAccountspayVo.setU8orderid(u8OrderId);
						financeAccountspayService.saveOrUpdate(financeAccountspayVo);
						logger.error("u8InterfaceService account U8对接销售单成功 :{}",
								"u8OrderId=" + u8OrderId);
					}
				} catch (Exception e) {
					logger.error("u8InterfaceService account U8对接销售单失败 :{}", e);
				}
				
				try {
					String u8AccountId = U8EaiUtil.U8Accept(financeAccountspayVo, financeAccountspayItemVo);
					if (u8AccountId == null || "".equals(u8AccountId)) {
						logger.error("u8InterfaceService account U8对接收款单失败 :{}", "返回信息为空！");
					} else {
						financeAccountspayVo.setU8accountsid(u8AccountId);
						financeAccountspayService.saveOrUpdate(financeAccountspayVo);
						logger.error("u8InterfaceService account U8对接收款单成功 :{}",
								" u8AccountId=" + u8AccountId);
					}
				} catch (Exception e) {
					logger.error("u8InterfaceService account U8对接收款单失败 :{}", e);
				}
			} else {
				logger.error("u8InterfaceService account msg : {}", "该订单的收款单已存在！");
			}

		} catch (NumberFormatException e) {
			logger.error("u8InterfaceService account NumberFormatException error: {}", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("u8InterfaceService account Exception error: {}", e);
			e.printStackTrace();
		}
	}

	@Override
	public void addU8Order() {
		// TODO Auto-generated method stub
		logger.info("u8InterfaceService addU8Order");
		try {
			List<FinanceAccountspayVo> financeAccountspayVoList = financeAccountspayService.getVoByU8Missing("order");
			if (financeAccountspayVoList != null) {
				for(FinanceAccountspayVo financeAccountspayVo:financeAccountspayVoList){
					String orderId=financeAccountspayVo.getOrderid();
					IndentU8Vo indentU8Vo = indentService.findU8VoById(Long.parseLong(orderId));
					if(indentU8Vo== null){
						continue;
					}
					if (indentU8Vo != null && indentU8Vo.getSubbranchId() != null
							&& !"".equals(indentU8Vo.getSubbranchId())) {
						SubbranchVo subbranchVo = subbranchService
								.selectByPrimaryKey(Long.parseLong(indentU8Vo.getSubbranchId()));
						indentU8Vo.setShopCode(subbranchVo.getNumber());
						indentU8Vo.setShopName(subbranchVo.getName());
						indentU8Vo.setDepartmentCode(subbranchVo.getDepartmentNum());
					}

					BigDecimal amount = new BigDecimal("0");
					for (IndentListVo indentListVo : indentU8Vo.getIndentList()) {
						BigDecimal supplyPrice = new BigDecimal(
								indentListVo.getSupplyPrice() == null ? "0" : indentListVo.getSupplyPrice() + "");
						BigDecimal num = new BigDecimal(
								indentListVo.getNumber() == null ? "0" : indentListVo.getNumber() + "");
						amount = amount.add(supplyPrice.multiply(num));
					}
					indentU8Vo.setAmount(amount);

					try {
						String u8OrderId = U8EaiUtil.U8EaiSalesOrder(indentU8Vo,financeAccountspayVo.getId());
						if (u8OrderId == null || "".equals(u8OrderId)) {
							logger.error("u8InterfaceService addU8Order U8对接销售单失败 :{}", "返回信息为空！");
						} else {
							financeAccountspayVo.setU8orderid(u8OrderId);
							financeAccountspayService.saveOrUpdate(financeAccountspayVo);
							logger.error("u8InterfaceService addU8Order U8对接销售单成功 :{}",
									"u8OrderId=" + u8OrderId);
						}
					} catch (Exception e) {
						logger.error("u8InterfaceService addU8Order U8对接销售单失败 :{}", e);
					}
				}
			} else {
				logger.error("u8InterfaceService addU8Order msg : {}", "不存在未同步U8销售订单信息！");
			}
		} catch (NumberFormatException e) {
			logger.error("u8InterfaceService account NumberFormatException error: {}", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("u8InterfaceService account Exception error: {}", e);
			e.printStackTrace();
		}
	}
	@Override
	public void addU8Accept() {
		// TODO Auto-generated method stub
		logger.info("u8InterfaceService addU8Accept");
		try {
			List<FinanceAccountspayVo> financeAccountspayVoList = financeAccountspayService.getVoByU8Missing("accept");
			if (financeAccountspayVoList != null) {
				for(FinanceAccountspayVo financeAccountspayVo:financeAccountspayVoList){
					String orderId=financeAccountspayVo.getOrderid();
					IndentU8Vo indentU8Vo = indentService.findU8VoById(Long.parseLong(orderId));
					if(indentU8Vo == null){
						continue;
					}
					if (indentU8Vo != null && indentU8Vo.getSubbranchId() != null
							&& !"".equals(indentU8Vo.getSubbranchId())) {
						SubbranchVo subbranchVo = subbranchService
								.selectByPrimaryKey(Long.parseLong(indentU8Vo.getSubbranchId()));
						indentU8Vo.setShopCode(subbranchVo.getNumber());
						indentU8Vo.setShopName(subbranchVo.getName());
						indentU8Vo.setDepartmentCode(subbranchVo.getDepartmentNum());
					}

					BigDecimal amount = new BigDecimal("0");
					for (IndentListVo indentListVo : indentU8Vo.getIndentList()) {
						BigDecimal supplyPrice = new BigDecimal(
								indentListVo.getSupplyPrice() == null ? "0" : indentListVo.getSupplyPrice() + "");
						BigDecimal num = new BigDecimal(
								indentListVo.getNumber() == null ? "0" : indentListVo.getNumber() + "");
						amount = amount.add(supplyPrice.multiply(num));
					}
					indentU8Vo.setAmount(amount);
					FinanceAccountspayItemVo financeAccountspayItemVo =financeAccountspayItemService.getFinanceAccountspayItemVo(Long.parseLong(financeAccountspayVo.getId()));
					try {
						String u8AccountId = U8EaiUtil.U8Accept(financeAccountspayVo, financeAccountspayItemVo);
						if (u8AccountId == null || "".equals(u8AccountId)) {
							logger.error("u8InterfaceService addU8Accept U8对接收款单失败 :{}", "返回信息为空！");
						} else {
							financeAccountspayVo.setU8accountsid(u8AccountId);
							financeAccountspayService.saveOrUpdate(financeAccountspayVo);
							logger.error("u8InterfaceService addU8Accept U8对接收款单成功 :{}",
									" u8AccountId=" + u8AccountId);
						}
					} catch (Exception e) {
						logger.error("u8InterfaceService addU8Accept U8对接收款单失败 :{}", e);
					}
				}
			} else {
				logger.error("u8InterfaceService addU8Accept msg : {}", "不存在未同步U8收款单信息！");
			}
		} catch (NumberFormatException e) {
			logger.error("u8InterfaceService account NumberFormatException error: {}", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("u8InterfaceService account Exception error: {}", e);
			e.printStackTrace();
		}
	}

	/**
	 * u8异常对接单列表
	 */
	@Override
	public Page<Map<String, Object>> u8FinanceAbnormallist(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<FinanceAbnormal> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			total = financeAccountspayExtendDao.queryTotalByMap(con);
			page.setTotalRecords(total);
			if(total > 0){
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = financeAccountspayExtendDao.queryListByMap(con);
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}

}
