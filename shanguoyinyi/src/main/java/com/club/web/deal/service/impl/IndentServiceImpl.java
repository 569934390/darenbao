package com.club.web.deal.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.ListUtils;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.deal.constant.IndentDealStatus;
import com.club.web.deal.constant.IndentPayType;
import com.club.web.deal.constant.IndentStatus;
import com.club.web.deal.constant.IndentType;
import com.club.web.deal.dao.extend.IndentExtendMapper;
import com.club.web.deal.domain.IndentDo;
import com.club.web.deal.domain.repository.IndentRepository;
import com.club.web.deal.exception.IndentException;
import com.club.web.deal.service.IndentListService;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.vo.CargoStockCheckVo;
import com.club.web.deal.vo.IndentExport;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.deal.vo.IndentListExport;
import com.club.web.deal.vo.IndentListVo;
import com.club.web.deal.vo.IndentMobileVo;
import com.club.web.deal.vo.IndentPageVo;
import com.club.web.deal.vo.IndentU8Vo;
import com.club.web.deal.vo.IndentVo;
import com.club.web.finance.service.FinanceAccountspayService;
import com.club.web.message.constant.MessageStatus;
import com.club.web.message.service.MessageService;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.service.StockManagerService;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.store.dao.base.po.SalesReturnReason;
import com.club.web.store.dao.extend.SalesReturnReasonExtendMapper;
import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.domain.repository.ShoppingCartRepository;
import com.club.web.store.service.CarriageRuleService;
import com.club.web.store.service.GoodService;
import com.club.web.store.service.GoodSkuService;
import com.club.web.store.service.SettlementBillService;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.store.vo.TradeGoodSkuVo;
import com.club.web.store.vo.TradeHeadStoreVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.DateParseUtil;
import com.club.web.util.IdGenerator;
import com.club.web.util.SmsUtil;
import com.club.web.util.sqlexecutor.SqlExecuteRepository;
import com.club.web.weixin.service.WeixinUserInfoService;
import com.club.web.weixin.util.WeiXinPayUtil;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 订单服务接口实现类
 * 
 * @author zhuzd
 *
 */
@Service
public class IndentServiceImpl implements IndentService {

	@Autowired
	private IndentRepository indentRepository;

	@Autowired
	private IndentListService indentListService;

	@Autowired
	private TradeHeadStoreService tradeHeadStoreService;

	@Autowired
	private SubbranchService subbranchService;

	@Autowired
	private WeixinUserInfoService clientService;

	@Autowired
	private StockManagerService stockManagerService;

	@Autowired
	private CarriageRuleService carriageRuleService;

	@Autowired
	private GoodService goodService;
	
	@Autowired
	private TradeGoodExtendMapper goodDao;
	
	@Autowired
	private StockManagerRepository stockManagerRepository;

	@Autowired
	private SqlExecuteRepository sqlExecuteRepository;

	@Autowired
	private SmsUtil smsUtil;

	@Autowired
	private SettlementBillService settlementBillService;

	@Autowired
	private WeiXinPayUtil weiXinPayUtil;

	@Autowired
	private IndentExtendMapper indentDao;
	
	@Autowired
	private SalesReturnReasonExtendMapper returnReasonDao;
	
	@Autowired
	private CouponDetailService couponDetailService; 
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FinanceAccountspayService financeAccountspayService;
	
	@Autowired
	private GoodSkuService goodSkuService;	
	
	@Value("${sms.address}")
	private String address;
	
	@Override
	public IndentVo findVoById(Long id) throws Exception {
		return getVoByDomain(indentRepository.findDoById(id));
	}
	
	@Override
	public void shipNotice(Long id) throws IndentException {
		IndentDo indentDo = indentRepository.findDoById(id);
		if(IndentStatus.待发货.getDbData() != indentDo.getStatus()){
			throw new IndentException("当前订单不为待发货状态，请重新刷新订单列表！");
		}
		indentDo.setShipNotice(1);
		indentDo.update();
		messageService.addNoticeMessage(indentDo.getBuyerId()+"", indentDo.getSubbranchId()+"", MessageStatus.提醒发货.getDbData(),
				"[订单]"+indentDo.getName()+" 客户提醒发货！");
	}

	@Override
	public List<IndentVo> findVoListByIds(String ids) throws Exception {
		return getVoListByDomainList(indentRepository.findDoListByIds(ids));
	}

	private int getCargoStockCheckCount(Map<String, Object> con) {
		List<CargoStockCheckVo> voList = indentRepository.findCardoStockCheckVoByMap(con);
		return ListUtils.isNotEmpty(voList) ? voList.size() : 0;
	}

	private List<CargoStockCheckVo> getCargoStockCheckList(Map<String, Object> con) {
		return indentRepository.findCardoStockCheckVoByMap(con);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param ids
	 *            ,订单id列表，多个id用,隔开
	 * @param username
	 *            ,当前操作人员名称
	 * @param action
	 *            ,后续流程动作 参考IndentStatus的name属性值
	 * @param map
	 *            ,需要用到的参数值，例如文本content
	 * @throws Exception
	 */
	@Override
	public void updateStatus(String ids, String action, Map<String, Object> loginMap, Map<String, Object> map)
			throws IndentException,Exception {
		List<IndentVo> list = this.findVoListByIds(ids);
		if (ListUtils.isNotEmpty(list)) {
			Integer dbData = IndentStatus.getDbDataByName(action);
			Date time = new Date();
			String remarkId = map !=null && map.get("contentId") != null && StringUtils.isNotEmpty(map.get("contentId").toString())? map.get("contentId").toString() : null;
			String remark = map != null && map.get("content") != null && StringUtils.isNotEmpty(map.get("content").toString())? map.get("content").toString() : null;
			long userId = loginMap != null && loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : -1;
			for (IndentVo indentVo : list) {
				String username = loginMap != null && loginMap.get("staffName") != null ? (String) loginMap.get("staffName") : indentVo.getBuyerName();
				StringBuffer sb = new StringBuffer();
				switch (indentVo.getStatus()) {
					// 待付款("pay",1)
					case 1: {
						if (IndentStatus.待发货.getDbData() != dbData && IndentStatus.已取消.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}
						if (IndentStatus.待发货.getDbData() == dbData) {
							indentVo.setPayType(Integer.valueOf(map.get("payType").toString()));
							indentVo.setPayAccount(map.get("payAccount").toString());
							indentVo.setPaymentAmount(map.get("paymentAmount").toString());
							indentVo.setPayTime((Date) map.get("payTime"));
							stockManagerService.updateStockByOrder(getParamByIndentList(indentVo.getIndentList()), userId,
									5, Long.valueOf(indentVo.getId()));
							messageService.addNoticeMessage(indentVo.getBuyerId(), indentVo.getSubbranchId(), MessageStatus.待发货.getDbData(),
									"[订单]"+indentVo.getName()+" 客户已下单，请及时发货！");
						}
						if (IndentStatus.已取消.getDbData() == dbData) {
							goodService.updateGoodSkuNums(getParamByIndentList(indentVo.getIndentList()), 1);
							// accountService.indentRefundBill(indentVo.getId(),
							// indentVo.getPaymentAmount());
							stockManagerService.updateStockByOrder(getParamByIndentList(indentVo.getIndentList()), userId,
									7, Long.valueOf(indentVo.getId()));
							indentVo.setFinishTime(time);
							indentVo.setDealStatus(IndentDealStatus.交易关闭.getDbData());
						}
						break;
					}// 待发货("ship",2)
					case 2: {
						if (IndentStatus.退款申请.getDbData() != dbData && IndentStatus.待收货.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}
						if (IndentStatus.待收货.getDbData() == dbData) {
							indentVo.setShipper(username);
							indentVo.setShipTime(time);
							indentVo.setShipNotice(0);
							indentVo.setExpressNumber(map.get("expressNumber")+"");
							indentVo.setExpressCompany(map.get("expressCompany")+"");
							indentVo.setCarriage("0");
//							indentVo.setWeight(map.get("weight")+"");
//							indentVo.setCarriage(map.get("carriage")+"");
							stockManagerService.updateStockByOrder(getParamByIndentList(indentVo.getIndentList()), userId,
									6, Long.valueOf(indentVo.getId()));
							if(indentVo.getType() == IndentType.兑换券.getDbData()){
								smsUtil.sendCouponInfo(indentVo.getReceiverPhone(), indentVo.getTicketNum(),
										indentVo.getReceiver(), indentVo.getExpressCompany(), indentVo.getExpressNumber());
							} else if("s".equals(indentVo.getBuyType()) || "S".equals(indentVo.getBuyType())){
								smsUtil.sendBuyOtherInfo(indentVo.getReceiverPhone(), indentVo.getStoreName(),
										indentVo.getReceiver(), indentVo.getExpressCompany(), indentVo.getExpressNumber());
							} else{
								smsUtil.sendBuyMyselInfo(indentVo.getReceiverPhone(),indentVo.getStoreName(),
										indentVo.getReceiver(),indentVo.getName(),indentVo.getExpressCompany(),indentVo.getExpressNumber());
							}
						}if (IndentStatus.退款申请.getDbData() == dbData) {
							if(IndentType.兑换券.getDbData() == indentVo.getType()){
								throw new IndentException("兑换券订单不能退款！");
							}
							indentVo.setRefundId(remarkId);
							indentVo.setRefundRemark(remark);
							messageService.addNoticeMessage(indentVo.getBuyerId(), indentVo.getSubbranchId(), MessageStatus.申请退款.getDbData(),
									"[订单]"+indentVo.getName()+" 客户申请退款，请及时处理！");
						}
						break;
					}// 退款申请("refund",3)
					case 3: {
						if (IndentStatus.待发货.getDbData() != dbData && IndentStatus.已退款.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}if (IndentStatus.待发货.getDbData() == dbData) {
							indentVo.setRejectRefund(remark);
						}if (IndentStatus.已退款.getDbData() == dbData) {
							goodService.updateGoodSkuNums(getParamByIndentList(indentVo.getIndentList()), 1);
							// accountService.indentRefundBill(indentVo.getId(),
							// indentVo.getPaymentAmount());
							stockManagerService.updateStockByOrder(getParamByIndentList(indentVo.getIndentList()), userId,
									8, Long.valueOf(indentVo.getId()));
							indentVo.setFinishTime(time);
							indentVo.setDealStatus(IndentDealStatus.交易失败.getDbData());
							Map weixinMap = weiXinPayUtil.WxRePay(indentVo.getId(), indentVo.getId(),
									Double.valueOf(indentVo.getPaymentAmount()),
									Double.valueOf(indentVo.getPaymentAmount()), null, null);
							if (!(boolean) weixinMap.get("code")) {
								throw new Exception("订单退款失败！");
							}else{
								financeAccountspayService.updateByOrderId(indentVo.getId(), 1);
							}
						}
						break;
					}// 待收货("receive",5)
					case 5: {
						if (IndentStatus.退货申请.getDbData() != dbData && IndentStatus.待评价.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}
						if (IndentStatus.待评价.getDbData() == dbData) {
							indentVo.setDealStatus(IndentDealStatus.交易成功.getDbData());
							indentVo.setFinishTime(time);
							if(indentVo.getType() != IndentType.兑换券.getDbData()){
								settlementBillService.addSettlementBill(indentVo.getId());// 确认收货要写一笔结算清单								
							}
						}if (IndentStatus.退货申请.getDbData() == dbData) {
							indentVo.setReturnId(remarkId);
							indentVo.setReturnRemark(remark);
						}
						break;
					}// 退货申请("return",6)
					case 6: {
						if (IndentStatus.待收货.getDbData() != dbData && IndentStatus.退货中.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}if (IndentStatus.待收货.getDbData() == dbData) {
							indentVo.setRejectReturn(remark);
						}
						break;
					}// 退货中("returning",7)
					case 7: {
						if (IndentStatus.已退货.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}
						goodService.updateGoodSkuNums(getParamByIndentList(indentVo.getIndentList()), 1);
						// accountService.indentRefundBill(indentVo.getId(),
						// indentVo.getPaymentAmount());
						indentVo.setFinishTime(time);
						indentVo.setDealStatus(IndentDealStatus.交易失败.getDbData());
						Map weixinMap = weiXinPayUtil.WxRePay(indentVo.getId(), indentVo.getId(),
								Double.valueOf(indentVo.getPaymentAmount()), Double.valueOf(indentVo.getPaymentAmount()),
								null, null);
						if (!(boolean) weixinMap.get("code")) {
							throw new Exception("订单退款失败！");
						}else{
							financeAccountspayService.updateByOrderId(indentVo.getId(), 1);
						}
						break;
					}// 待评价("evaluate",12)
					case 12: {
						if (IndentStatus.已完成.getDbData() != dbData) {
							throw new Exception("更新订单状态失败！");
							// continue;
						}
						indentVo.setDealStatus(IndentDealStatus.交易成功.getDbData());
						break;
					}
					default: {
						throw new Exception("更新订单状态失败！");
						// continue;
					}
				}
				sb.append(getRemarkByParams(username, time, dbData, remark)).append(StringUtils.isEmpty(indentVo.getRemark()) ? "" : indentVo.getRemark());
				indentVo.setRemark(sb.toString());
				indentVo.setStatus(dbData);
				getDomainByVo(indentVo).update();
			}
		} else{
			throw new IndentException("不存在该订单！");
		}
	}

	@Override
	public List<IndentMobileVo> findVoMobileListByBuyerId(Long storeId,Long buyerId, String status, int startIndex, int pageSize)
			throws Exception {
		Map<String, Object> con = new HashMap<>();
		con.put("buyerId", buyerId);
		con.put("storeId", storeId);
		con.put("startIndex", startIndex);
		con.put("pageSize", pageSize);
		if (StringUtils.isNotEmpty(status)) {
			con.put("status", status);
		}
		return getMobileVoListByDomainList(indentRepository.findDoListByBuyerIdAndStoreId(con));
	}

	/**
	 * 查询店铺的订单列表
	 * 
	 * @param shopId
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return List<IndentMobileVo>
	 * @throws Exception
	 * @add by 2016-05-13
	 * 
	 */
	@Override
	public List<IndentExtendVo> getShopOrderListSer(Long shopId, Long userId, String status, int startIndex, int pageSize)
			throws Exception {
		Map<String, Object> con = new HashMap<>();
		con.put("shopId", shopId);
		con.put("userId", userId);
		con.put("startIndex", startIndex);
		con.put("pageSize", pageSize);
		if (!"0".equals(status)) {
			con.put("status", status);
		}
		List<IndentExtendVo> list = indentRepository.getShopOrderList(con);
		if (list != null) {
			list.stream().forEach(arg0 -> {
				List<IndentListVo> listDetail = indentListService.getVoListByIndentId(Long.valueOf(arg0.getId()));
				if (listDetail != null && listDetail.stream().count() > 0) {
					arg0.setPicUrl(listDetail.get(0).getTradeGoodImgUrl());
				}
			});
		}
		return list;
	}

	@Override
	public void add(IndentVo indentVo) throws IndentException,Exception {
		sqlExecuteRepository.disableForeignKeyChecks();
		try {
			// 关闭外键约束
			// trade_head_store_id
			// subbranch_id
			// buyer_id
			// referrer_id 可有可无
			// province
			// city
			// town
			// address
			// receiver_phone
			// buyer_remark
			// receiver
			// need_invoice
			// invoice_name
			// invoice_content
			// paymentAmount
			// buyerCarriage
			checkIndent(indentVo);
			Map<String, Object> buyer = null; 
			if (StringUtils.isNotEmpty(indentVo.getBuyerId())) {
				buyer = clientService.loadClientInfo(Long.parseLong(indentVo.getBuyerId()));
//				Map<String, Object> buyer = clientService.loadClientInfo(Long.parseLong(indentVo.getBuyerId()));
				// if(buyer != null && !buyer.isEmpty()){
				// Integer level = (Integer) buyer.get("LEVEL_ID");
				// if(level < 2){
				// throw new Exception("会员等级为普通会员，请先升级后再购买！");
				// }
				// }else{
				// throw new Exception("不存在该会员，请先登录！");
				// }
			}
			String indentId = IdGenerator.getDefault().nextId() + "";
			// name 例子：zts20160101125959000
			indentVo.setName(createIndentName());
			indentVo.setId(indentId);
			List<IndentListVo> indentListResult = new ArrayList<IndentListVo>();
			BigDecimal totalAmount = new BigDecimal(0);
			BigDecimal paymentAmount = new BigDecimal(0);
			Date time = new Date();
			Integer number = new Integer(0);
			List<Map> carriageList = new ArrayList<>();
			for (IndentListVo indentListVo : indentVo.getIndentList()) {
				if (indentListService.fillIndentListValue(indentVo.getSubbranchId(),indentId, indentListVo)) {
					indentListResult.add(indentListVo);
					BigDecimal tradeGoodAmount = new BigDecimal(indentListVo.getFinalAmount()).multiply(new BigDecimal(indentListVo.getNumber()));
					BigDecimal finalAmount = new BigDecimal(indentListVo.getFinalAmount()).multiply(new BigDecimal(indentListVo.getNumber()));
					addCarriageMap(carriageList,indentListVo.getTradeGoodSkuId(),finalAmount,indentVo.getTownCode());
					totalAmount = totalAmount.add(tradeGoodAmount);
					paymentAmount = paymentAmount.add(finalAmount);
					number += indentListVo.getNumber();
				}
			}
			BigDecimal carriage = carriageRuleService.getCarriageByRegionIdAndGoodId(carriageList);
			indentVo.setBuyerCarriage(carriage == null?"0":carriage+"");
			goodService.updateGoodSkuNums(getParamByIndentList(indentListResult), 0);
			// remark
			// type 默认正常交易
			indentVo.setType(indentVo.getType() != null ? indentVo.getType() : IndentType.正常交易.getDbData());
			indentVo.setBuyerCarriage(StringUtils.isEmpty(indentVo.getBuyerCarriage()) ? "0":indentVo.getBuyerCarriage());
			// 加邮费
			paymentAmount = paymentAmount.add(new BigDecimal(indentVo.getBuyerCarriage()));
			indentVo.setCreateTime(time);
			stockManagerService.updateStockByOrder(getParamByIndentList(indentListResult), -1, 4,
					Long.valueOf(indentVo.getId()));
			indentVo.setTotalAmount(totalAmount + "");
			indentVo.setNumber(number);
			indentVo.setIndentList(indentListResult);
			if(IndentType.兑换券.getDbData() == indentVo.getType()){
				if(StringUtils.isEmpty(indentVo.getTicketNum())){
					throw new Exception("请输入兑换券！");
				}
				indentVo.setStatus(IndentStatus.待发货.getDbData());
				indentVo.setPayType(IndentPayType.兑换券.getDbData());
				couponDetailService.modifyStatus(indentVo.getTicketNum());
				indentVo.setPayTime(time);
				stockManagerService.updateStockByOrder(getParamByIndentList(indentVo.getIndentList()), Long.valueOf(indentVo.getBuyerId()),
						5, Long.valueOf(indentVo.getId()));
				indentVo.setRemark(getRemarkByParams(buyer != null?(String)buyer.get("nickname"):"", time, IndentStatus.待发货.getDbData(), null));
				indentVo.setPaymentAmount("0");
			}else if(IndentType.正常交易.getDbData() == indentVo.getType()){
				indentVo.setStatus(IndentStatus.待付款.getDbData());
				indentVo.setPaymentAmount(paymentAmount + "");
			}
			getDomainByVo(indentVo).save();
			indentListService.addAll(indentListResult);
			List<Long> goodsIds = new ArrayList<>();
			for (IndentListVo indentListVo : indentListResult) {
				if(indentListVo != null && StringUtils.isNotEmpty(indentListVo.getTradeGoodSkuId())){
					goodsIds.add(Long.valueOf(indentListVo.getTradeGoodSkuId()));
				}
			}
			if(ListUtils.isNotEmpty(goodsIds) && StringUtils.isNotEmpty(indentVo.getBuyerId()) && StringUtils.isNotEmpty(indentVo.getSubbranchId())){
				shoppingCartRepository.deleteShoppingCartGoods(Long.valueOf(indentVo.getBuyerId()), goodsIds, Long.valueOf(indentVo.getSubbranchId()), 0, 1);
			}
		} finally {
			sqlExecuteRepository.enableForeignKeyChecks();
		}
	}
	
	private void addCarriageMap(List<Map> carriageList, String tradeGoodSkuId, BigDecimal finalAmount,
			String townCode) {
		TradeGoodSkuVo tradeGoodSkuVo = goodSkuService.getSkuvoById(Long.valueOf(tradeGoodSkuId));
		Map tempMap = new HashMap<>();
		if(ListUtils.isNotEmpty(carriageList)){
			for (Map map : carriageList) {
				if(tradeGoodSkuVo.getGoodId().equals(map.get("goodId")+"")){
					finalAmount.add(new BigDecimal(tempMap.get("money")+""));
					carriageList.remove(map);
					break;
				}
			}
		}
		tempMap.put("money", finalAmount);
		tempMap.put("goodId", tradeGoodSkuVo.getGoodId());
		tempMap.put("regionId", townCode);
		carriageList.add(tempMap);
	}

	/**
	 * 数据校验
	 * 去掉区
	 * @param indentVo
	 * @throws IndentException
	 * @throws Exception
	 */
	private void checkIndent(IndentVo indentVo) throws IndentException,Exception{
		if (indentVo == null) {
			throw new IndentException("订单生成失败！");
		}
		if(StringUtils.isEmpty(indentVo.getProvince()) || StringUtils.isEmpty(indentVo.getCity()) || StringUtils.isEmpty(indentVo.getAddress())){
			throw new IndentException("请填写收货地址！");
		}if(StringUtils.isEmpty(indentVo.getReceiver())){
			throw new IndentException("请填写收货人！");
		}if(StringUtils.isEmpty(indentVo.getReceiverPhone())){
			throw new IndentException("请填写收货人联系方式！");
		}
		if(ListUtils.isEmpty(indentVo.getIndentList())){
			throw new IndentException("请选择商品！");
		}
		if(StringUtils.isEmpty(indentVo.getSubbranchId())){
			throw new IndentException("很抱歉，您在的店铺出现异常，请重新进入！");
		}
		SubbranchVo subbranchVo = subbranchService.selectByPrimaryKey(Long.valueOf(indentVo.getSubbranchId()));
		if(subbranchVo == null){
			throw new IndentException("很抱歉，您在的店铺出现异常，请重新进入！");
		}
		if(StringUtils.isEmpty(indentVo.getSubbranchId())){
			throw new IndentException("添加订单失败！");
		}
		if( StringUtils.isEmpty(indentVo.getBuyerId())){
			throw new IndentException("添加订单失败！");
		}
		
	}
	
	private List<SkuGoodsParam> getParamByIndentList(List<IndentListVo> indentListVoList) {
		List<SkuGoodsParam> paramList = new ArrayList<SkuGoodsParam>();
		for (IndentListVo indentListVo : indentListVoList) {
			if (indentListVo != null && indentListVo.getTradeGoodSkuId() != null) {
//				TradeGoodSkuVo tradeGoodSkuVo = goodSkuService.getSkuvoById(Long.valueOf(indentListVo
//						.getTradeGoodSkuId()));
//				if (tradeGoodSkuVo != null) {
					SkuGoodsParam param = new SkuGoodsParam();
					param.setGoodSkuId(indentListVo.getTradeGoodSkuId());
					param.setCargoSkuId(indentListVo.getCargoSkuId());
					param.setNum(indentListVo.getNumber() + "");
					if(StringUtils.isNotEmpty(indentListVo.getTradeGoodSkuId())){
						GoodVo good = goodDao.findGoodBySkuId(Long.valueOf(indentListVo.getTradeGoodSkuId()));				
						if(good != null && good.getStatus() == 1){
							param.setGoodStatus(1);
						}
					}
					paramList.add(param);
//				}
			}
		}
		return paramList;
	}

	private String createIndentName() {
		return "zgcs" + DateParseUtil.formatDate(new Date(), Constants.INDENT_NAME_RULE);
	}

	private String getRemarkByParams(String username, Date time, Integer status, String content) {
		StringBuffer sb = new StringBuffer();
		sb.append(username).append(" 于 ").append(DateParseUtil.formatDate(time, Constants.YYYYMMDDHHMMSS))
				.append(" 将订单状态置为 '").append(IndentStatus.getTextByDbData(status))
				.append(StringUtils.isEmpty(content) ? "'<br/>" : " 审批备注：<br/>" + content + "<br/>");
		return sb.toString();
	}

	@Override
	public Map<String, Object> list(Page<Map<String, Object>> page) throws Exception {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		Map<String,Object> result = new HashMap<>();
		Map<String, Object> con = page.getConditons();
		List<IndentPageVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			total = indentDao.queryTotalByMap(con);
			page.setTotalRecords(total);
			if (total > 0) {
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = indentDao.queryIndentPoByMap(con);
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		result.put("page", page);
		result.putAll(indentDao.findAmountByMap(con));
		return result;
	}

	@Override
	public Page<Map<String, Object>> cargoChecklist(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<CargoStockCheckVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			total = this.getCargoStockCheckCount(con);
			page.setTotalRecords(total);
			if (total > 0) {
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = this.getCargoStockCheckList(con);
				for (CargoStockCheckVo cargoStockCheckVo : list) {
					List<CargoSkuItemVo> cargoSkuItemVolist = stockManagerRepository.queryGoodsSpecList(Long
							.valueOf(cargoStockCheckVo.getSkuId()));
					if (ListUtils.isNotEmpty(cargoSkuItemVolist)) {
						cargoStockCheckVo.setItem(cargoSkuItemVolist);
					}
				}

				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}

	@Override
	public int cargoCheckCount() {
		return this.getCargoStockCheckCount(new HashMap<>());
	}

	@Override
	public void delete(String ids) throws Exception {
		if(StringUtils.isNotEmpty(ids)){
			List<IndentVo> indents = this.findVoListByIds(ids);
			for (IndentVo indent : indents) {
				if(IndentStatus.待付款.getDbData() == indent.getStatus()){
					getDomainByVo(indent).delete();
				}else{
					indent.setDeleteFlag(true);
					getDomainByVo(indent).update();
				}
			}
		}
	}

	private List<IndentMobileVo> getMobileVoListByDomainList(List<IndentDo> srcs) throws Exception {
		List<IndentMobileVo> targets = new ArrayList<IndentMobileVo>();
		if (srcs != null && srcs.size() != 0) {
			for (IndentDo src : srcs) {
				targets.add(getMobileVoByDomain(src));
			}
		}
		return targets;
	}

	private IndentMobileVo getMobileVoByDomain(IndentDo src) {
		IndentMobileVo target = null;
		if (src != null) {
			target = new IndentMobileVo();
			target.setId(src.getId() == null ? null : src.getId() + "");
			target.setIndentList(indentListService.getVoListByIndentId(src.getId()));
			target.setTotalAmount(src.getTotalAmount() + "");
			target.setPaymentAmount(src.getPaymentAmount() + "");
			target.setNumber(src.getNumber());
			target.setStatus(src.getStatus());
			target.setCreateTime(src.getCreateTime());
			target.setDealStatus(src.getDealStatus());
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setBuyerCarriage(src.getBuyerCarriage()!=null?src.getBuyerCarriage().toString():null);
			target.setType(src.getType());
			if(src.getReturnId() != null){
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getReturnId());
				target.setReturnName(reason!=null&&StringUtils.isNotEmpty(reason.getReason())?reason.getReason():"");				
			}if(src.getRefundId() != null){
				// 后前增加退款id
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getRefundId());
				target.setRefundName(reason!=null&&StringUtils.isNotEmpty(reason.getReason())?reason.getReason():"");				
			}
		}
		return target;
	}

	private List<IndentVo> getVoListByDomainList(List<IndentDo> srcs) throws Exception {
		List<IndentVo> targets = new ArrayList<IndentVo>();
		if (srcs != null && srcs.size() != 0) {
			for (IndentDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}

//	private List<IndentDo> getDomainListByVoList(List<IndentVo> srcs) {
//		List<IndentDo> targets = new ArrayList<IndentDo>();
//		if (srcs != null && srcs.size() != 0) {
//			for (IndentVo src : srcs) {
//				targets.add(getDomainByVo(src));
//			}
//		}
//		return targets;
//	}
	
	private IndentU8Vo getU8VoByDomain(IndentDo src) {
		IndentU8Vo target = null;
		if (src != null) {
			target = new IndentU8Vo();
			target.setId(src.getId() == null ? null : src.getId() + "");
			target.setIndentList(indentListService.getVoListByIndentId(src.getId()));
			if (src.getTradeHeadStoreId() != null) {
				TradeHeadStoreVo tradeHeadStoreVo = tradeHeadStoreService.getTradeHeadStoreVoById(src
						.getTradeHeadStoreId());
				if (tradeHeadStoreVo != null) {
					target.setTradeHeadStoreId(src.getTradeHeadStoreId() + "");
					target.setStoreName(tradeHeadStoreVo.getName());
				}
			}
			if (src.getSubbranchId() != null) {
				SubbranchVo subbranchVo = subbranchService.selectByPrimaryKey(src.getSubbranchId());
				if (subbranchVo != null) {
					target.setSubbranchId(src.getSubbranchId() + "");
					target.setStoreName(subbranchVo.getName());
				}
			}
			target.setName(src.getName());
			target.setTotalAmount(src.getTotalAmount() + "");
			target.setPaymentAmount(src.getPaymentAmount() + "");
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(src.getCarriage()!=null?src.getCarriage().toString():null);
			target.setBuyerCarriage(src.getBuyerCarriage()!=null?src.getBuyerCarriage().toString():null);
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
			target.setBuyerId(src.getBuyerId() + "");
			target.setReferrerId(src.getReferrerId() + "");
			if (src.getBuyerId() != null) {
				Map<String, Object> buyer = clientService.loadClientInfo(src.getBuyerId());
				target.setBuyer(buyer);
			}
			if (src.getReferrerId() != null) {
				Map<String, Object> referrer = clientService.loadClientInfo(src.getReferrerId());
				target.setReferrer(referrer);
			}
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId() == null ? null : src.getRefundId()+"");
			target.setReturnId(src.getReturnId() == null ? null : src.getReturnId()+"");
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setRemark(src.getRemark());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
			if(src.getReturnId() != null){
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getReturnId());
				target.setReturnName(StringUtils.isNotEmpty(reason.getReason())?reason.getReason():null);
			}if(src.getRefundId() != null){
				// 后前增加退款id
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getRefundId());
				target.setRefundName(StringUtils.isNotEmpty(reason.getReason())?reason.getReason():null);
			}
		}
		return target;
	}

	public IndentVo getVoByDomain(IndentDo src) throws Exception {
		IndentVo target = null;
		if (src != null) {
			target = new IndentVo();
			target.setId(src.getId() == null ? null : src.getId() + "");
			target.setIndentList(indentListService.getVoListByIndentId(src.getId()));
			if (src.getTradeHeadStoreId() != null) {
				TradeHeadStoreVo tradeHeadStoreVo = tradeHeadStoreService.getTradeHeadStoreVoById(src
						.getTradeHeadStoreId());
				if (tradeHeadStoreVo != null) {
					target.setTradeHeadStoreId(src.getTradeHeadStoreId() + "");
					target.setStoreName(tradeHeadStoreVo.getName());
				}
			}
			if (src.getSubbranchId() != null) {
				SubbranchVo subbranchVo = subbranchService.selectByPrimaryKey(src.getSubbranchId());
				if (subbranchVo != null) {
					target.setSubbranchId(src.getSubbranchId() + "");
					target.setStoreName(subbranchVo.getName());
				}
			}
			target.setName(src.getName());
			target.setTotalAmount(src.getTotalAmount() + "");
			target.setPaymentAmount(src.getPaymentAmount() + "");
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(src.getCarriage()!=null?src.getCarriage().toString():null);
			target.setBuyerCarriage(src.getBuyerCarriage()!=null?src.getBuyerCarriage().toString():null);
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
			target.setBuyerId(src.getBuyerId() + "");
			target.setReferrerId(src.getReferrerId() + "");
			if (src.getBuyerId() != null) {
				Map<String, Object> buyer = clientService.loadClientInfo(src.getBuyerId());
				target.setBuyer(buyer);
			}
			if (src.getReferrerId() != null) {
				Map<String, Object> referrer = clientService.loadClientInfo(src.getReferrerId());
				target.setReferrer(referrer);
			}
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId() == null ? null : src.getRefundId()+"");
			target.setReturnId(src.getReturnId() == null ? null : src.getReturnId()+"");
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setRemark(src.getRemark());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
			if(src.getReturnId() != null){
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getReturnId());
				target.setReturnName(StringUtils.isNotEmpty(reason.getReason())?reason.getReason():null);
			}if(src.getRefundId() != null){
				// 后前增加退款id
				SalesReturnReason reason = returnReasonDao.selectByPrimaryKey(src.getRefundId());
				target.setRefundName(StringUtils.isNotEmpty(reason.getReason())?reason.getReason():null);
			}
		}
		return target;
	}
	
	private IndentVo getVoForWeixinByDomain(IndentDo src) {
		IndentVo target = null;
		if (src != null) {
			target = new IndentVo();
			target.setId(src.getId() == null ? null : src.getId() + "");
			target.setName(src.getName());
			target.setTotalAmount(src.getTotalAmount() + "");
			target.setPaymentAmount(src.getPaymentAmount() + "");
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(src.getCarriage()!=null?src.getCarriage().toString():null);
			target.setBuyerCarriage(src.getBuyerCarriage()!=null?src.getBuyerCarriage().toString():null);
			target.setShipper(src.getShipper());
			target.setShipTime(src.getShipTime());
			target.setReceiver(src.getReceiver());
			target.setStatus(src.getStatus());
			target.setPayType(src.getPayType());
			target.setShipNotice(src.getShipNotice());
			target.setPayAccount(src.getPayAccount());
			target.setNeedInvoice(src.getNeedInvoice());
			target.setInvoiceName(src.getInvoiceName());
			target.setInvoiceContent(src.getInvoiceContent());
			target.setRemark(src.getRemark());
			target.setFinishTime(src.getFinishTime());
			target.setBuyerId(src.getBuyerId() + "");
			target.setReferrerId(src.getReferrerId() + "");
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId() == null ? null : src.getRefundId()+"");
			target.setReturnId(src.getReturnId() == null ? null : src.getReturnId()+"");
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setRemark(src.getRemark());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
		}
		return target;
	}


	private IndentDo getDomainByVo(IndentVo src) {
		IndentDo target = null;
		if (src != null) {
			target = new IndentDo();
			target.setId(src.getId() == null ? null : Long.valueOf(src.getId()));
			target.setTradeHeadStoreId(src.getTradeHeadStoreId() == null ? null : Long.valueOf(src
					.getTradeHeadStoreId()));
			target.setSubbranchId(StringUtils.isEmpty(src.getSubbranchId()) ? null : Long.valueOf(src.getSubbranchId()));
			target.setBuyerId(StringUtils.isEmpty(src.getBuyerId()) ? null : Long.valueOf(src.getBuyerId()));
			target.setReferrerId(StringUtils.isEmpty(src.getReferrerId()) ? null : Long.valueOf(src.getReferrerId()));
			target.setName(src.getName());
			target.setTotalAmount(BigDecimal.valueOf(Double.valueOf(src.getTotalAmount())));
			target.setPaymentAmount(BigDecimal.valueOf(Double.valueOf(src.getPaymentAmount())));
			target.setCreateTime(src.getCreateTime());
			target.setPayTime(src.getPayTime());
			target.setNumber(src.getNumber());
			target.setType(src.getType());
			target.setProvince(src.getProvince());
			target.setCity(src.getCity());
			target.setTown(src.getTown());
			target.setAddress(src.getAddress());
			target.setReceiverPhone(src.getReceiverPhone());
			target.setBuyerRemark(src.getBuyerRemark());
			target.setExpressNumber(src.getExpressNumber());
			target.setExpressCompany(src.getExpressCompany());
			target.setWeight(src.getWeight());
			target.setCarriage(StringUtils.isEmpty(src.getCarriage())?null:new BigDecimal(src.getCarriage()));
			target.setBuyerCarriage(StringUtils.isEmpty(src.getBuyerCarriage())?null:new BigDecimal(src.getBuyerCarriage()));
			target.setShipper(src.getShipper());
			target.setShipTime(src.getShipTime());
			target.setReceiver(src.getReceiver());
			target.setStatus(src.getStatus());
			target.setShipNotice(src.getShipNotice());
			target.setPayType(src.getPayType());
			target.setPayAccount(src.getPayAccount());
			target.setNeedInvoice(src.getNeedInvoice());
			target.setInvoiceName(src.getInvoiceName());
			target.setInvoiceContent(src.getInvoiceContent());
			target.setRemark(src.getRemark());
			target.setFinishTime(src.getFinishTime());
			target.setDeleteFlag(src.getDeleteFlag());
			target.setDealStatus(src.getDealStatus());
			target.setRefundId(src.getRefundId() == null ? null : Long.valueOf(src.getRefundId()));
			target.setReturnId(src.getReturnId() == null ? null : Long.valueOf(src.getReturnId()));
			target.setRefundRemark(src.getRefundRemark());
			target.setRejectRefund(src.getRejectRefund());
			target.setReturnRemark(src.getReturnRemark());
			target.setRejectReturn(src.getRejectReturn());
			target.setRemark(src.getRemark());
			target.setTicketNum(src.getTicketNum());
			target.setBuyType(src.getBuyType());
		}
		return target;
	}

	/**
	 * 查询店铺订单统计值
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-17
	 */
	@Override
	public Map<String, Object> getOrderTotalMsgSer(Long shopId) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar canlendar = Calendar.getInstance();
		Date nowDate = canlendar.getTime();
		canlendar.add(Calendar.DATE, -1);
		Date lastDate = canlendar.getTime();
		Map<String, Object> map = indentDao.getHistoryOrderTotalMsg(shopId);
		BigDecimal today = indentDao.getTodayOrderTotalMsg(shopId, format.format(nowDate));
		BigDecimal last = indentDao.getLastOrderTotalMsg(shopId, format.format(lastDate));
		if (map != null) {
			result.put("historyNumTotal", map.get("historyNumTotal") != null ? map.get("historyNumTotal").toString() : "0");
			result.put("hostoryAmtTotal", map.get("hostoryAmtTotal") != null ? map.get("hostoryAmtTotal").toString() : "0.00");
		} else {
			result.put("historyNumTotal", "0");
			result.put("hostoryAmtTotal", "0.00");
		}
		if (today == null) {
			today = new BigDecimal("0");
		}
		if (last == null) {
			last = new BigDecimal("0.00");
		}
		result.put("todayNumTotal", today);
		result.put("tomorowAmtTotal", last.setScale(2, RoundingMode.HALF_DOWN).toString());
		return result;
	}

	@Override
	public IndentU8Vo findU8VoById(long orderId) {
		return getU8VoByDomain(indentRepository.findDoById(orderId));
	}

	@Override
	public IndentVo findVoForWeixinCallBackById(Long id){
		return getVoForWeixinByDomain(indentRepository.findDoById(id));
	}

	@Override
	public void exportExcel(String conditionStr, HttpServletResponse response) throws Exception{
			Map<String,Object> con = JsonUtil.toMap(conditionStr);
			List<IndentExport> list = indentDao.queryIndentExportByMap(con);
            writeExcel(list,response); 
	}

	private void writeExcel(List<IndentExport> list, HttpServletResponse response)throws Exception {
		ServletOutputStream outputStream = null;
		WritableWorkbook wbook = null;
		try{			
			outputStream = response.getOutputStream();
	        response.reset();
	        
	        String fileName = "订单信息-"+DateParseUtil.formatDate(new Date(), Constants.YYYYMMDD)+".xls";
	        // 文件名为中文不乱码
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	        response.setContentType("application/msexcel;charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        
	        wbook = jxl.Workbook.createWorkbook(outputStream);
	        String tmptitle = "订单信息列表";
	        WritableSheet wsheet = wbook.createSheet(tmptitle, 0);
	        initHeader(wsheet);
	        initContext(wsheet,list);
		}catch(Exception e){
			throw e;
		}finally{
			if(wbook != null){
				wbook.write();
		        wbook.close();
			}if(outputStream != null){
		        outputStream.flush();
		        outputStream.close();
			}
		}
	}

	private void initContext(WritableSheet wsheet, List<IndentExport> list) throws Exception{
		if(ListUtils.isNotEmpty(list)){
			WritableCellFormat ccf = new WritableCellFormat();
	        ccf.setBorder(Border.ALL,BorderLineStyle.THIN);
	        ccf.setVerticalAlignment(VerticalAlignment.CENTRE);
	        ccf.setAlignment(Alignment.LEFT);
	        ccf.setWrap(true);
			int mergeRow = 1;
			for(int row = 0; row < list.size(); row++){
				IndentExport indent = list.get(row);
				int indentListSize = indent.getIndentList().size();
				if(indentListSize > 1){
					wsheet.mergeCells(0,mergeRow,0,mergeRow+indentListSize-1);
					wsheet.mergeCells(1,mergeRow,1,mergeRow+indentListSize-1);
					wsheet.mergeCells(2,mergeRow,2,mergeRow+indentListSize-1);
					wsheet.mergeCells(3,mergeRow,3,mergeRow+indentListSize-1);
					wsheet.mergeCells(4,mergeRow,4,mergeRow+indentListSize-1);
					wsheet.mergeCells(5,mergeRow,5,mergeRow+indentListSize-1);
					wsheet.mergeCells(6,mergeRow,6,mergeRow+indentListSize-1);
					wsheet.mergeCells(7,mergeRow,7,mergeRow+indentListSize-1);
					wsheet.mergeCells(8,mergeRow,8,mergeRow+indentListSize-1);
				}
				wsheet.addCell(new Label(0, mergeRow, indent.getName(), ccf));
				wsheet.addCell(new Label(1, mergeRow, indent.getReceiver(), ccf));
				wsheet.addCell(new Label(2, mergeRow, indent.getReceiverPhone(), ccf));
				wsheet.addCell(new Label(3, mergeRow, indent.getFullAddress(), ccf));
				wsheet.addCell(new Label(4, mergeRow, indent.getNeedInvoiceText(), ccf));
				wsheet.addCell(new Label(5, mergeRow, indent.getInvoiceText(), ccf));
				wsheet.addCell(new Label(6, mergeRow, indent.getBuyerCarriage(), ccf));
				wsheet.addCell(new Label(7, mergeRow, indent.getPaymentAmount(), ccf));
				wsheet.addCell(new Label(8, mergeRow, indent.getBuyerRemark(), ccf));
				for(int listRow = 0; listRow < indent.getIndentList().size(); listRow++){
					IndentListExport indentList = indent.getIndentList().get(listRow);
					wsheet.addCell(new Label(9, mergeRow+listRow, indentList.getCargoNo(), ccf));
					wsheet.addCell(new Label(10, mergeRow+listRow, indentList.getTradeGoodName(), ccf));
					wsheet.addCell(new Label(11, mergeRow+listRow, indentList.getFinalAmount(), ccf));
					wsheet.addCell(new Label(12, mergeRow+listRow, indentList.getNumber()+"", ccf));
					wsheet.addCell(new Label(13, mergeRow+listRow, indent.getBuyTypeText(), ccf));
				}
				mergeRow += indentListSize;
			}
		}		
	}

	private void initHeader(WritableSheet wsheet) throws Exception {
		WritableCellFormat ccf = new WritableCellFormat();
        ccf.setBorder(Border.ALL, BorderLineStyle.THIN);
        ccf.setAlignment(Alignment.LEFT);
        ccf.setBackground(Colour.SKY_BLUE);
        ccf.setWrap(true);
        wsheet.setColumnView(0,23);
        wsheet.setColumnView(1,10);
        wsheet.setColumnView(2,15);
        wsheet.setColumnView(3,35);
        wsheet.setColumnView(4,6);
        wsheet.setColumnView(5,30);
        wsheet.setColumnView(6,6);
        wsheet.setColumnView(7,22);
        wsheet.setColumnView(8,35);
        wsheet.setColumnView(9,15);
        wsheet.setColumnView(10,30);
        wsheet.setColumnView(11,10);
        wsheet.setColumnView(12,10);
        wsheet.setColumnView(13,10);
		wsheet.addCell(new Label(0, 0, "订单号", ccf));
		wsheet.addCell(new Label(1, 0, "收货人",ccf));
		wsheet.addCell(new Label(2, 0, "收货人手机号",ccf));
		wsheet.addCell(new Label(3, 0, "收货地址",ccf));
		wsheet.addCell(new Label(4, 0, "发票",ccf));
		wsheet.addCell(new Label(5, 0, "抬头",ccf));
		wsheet.addCell(new Label(6, 0, "运费",ccf));
		wsheet.addCell(new Label(7, 0, "实际支付金额（含运费）",ccf));
		wsheet.addCell(new Label(8, 0, "买家留言",ccf));
		wsheet.addCell(new Label(9, 0, "货品编号",ccf));
		wsheet.addCell(new Label(10, 0, "商品名称",ccf));
		wsheet.addCell(new Label(11, 0, "商品金额",ccf));
		wsheet.addCell(new Label(12, 0, "商品数量",ccf));
		wsheet.addCell(new Label(13, 0, "购买类型",ccf));
	}

	@Override
	public void updateList(Map<String, Object> con) throws IndentException, Exception {
		List<IndentPageVo> list = indentDao.queryIndentPoByMap(con);
		if(ListUtils.isNotEmpty(list)){
			StringBuffer ids = new StringBuffer("");
			for (IndentPageVo indentPageVo : list) {
				ids.append(indentPageVo.getId()).append(ListUtils.SPLIT);
			}
			this.updateStatus(ids.toString(), IndentStatus.待评价.getName(), null, null);
		}
	}

}
