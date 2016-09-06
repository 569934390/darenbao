package com.club.web.store.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.deal.domain.repository.IndentRepository;
import com.club.web.store.dao.base.po.Subbranch;
import com.club.web.store.dao.extend.SubbranchExtendMapper;
import com.club.web.store.service.IndentBillService;
import com.club.web.store.service.SettlementBillService;
import com.club.web.store.service.TimeCycleService;
import com.club.web.store.vo.ExportExcelBillVo;
import com.club.web.store.vo.IndentBillVo;
import com.club.web.store.vo.MerchantBillVo;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.store.vo.TimeCycleVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.DateParseUtil;
import com.club.web.util.ExportExcel2;
import com.club.web.util.IdGenerator;

/**
 * @Title: SettlementBillServiceImpl.java
 * @Package com.club.web.store.service.impl
 * @Description: 待结算账单Service层实现类
 * @author hqLin
 * @date 2016/05/04
 * @version V1.0
 */
@Service("settlementBillService")
@PropertySource("classpath:/config/server_address.properties")
public class SettlementBillServiceImpl implements SettlementBillService {

	@Autowired
	private IndentRepository indentRepository;

	@Autowired
	private SubbranchExtendMapper subbranchExtendMapper;

	@Autowired
	private IndentBillService indentBillService;

	@Autowired
	private TimeCycleService settleTimeService;

	@Value("${server.address}")
	private String serverAddress;

	@Override
	public Map<String, Object> selectSettlementBill(Page<Map<String, Object>> page, String orderId, String shopId,
			Date startTime, Date endTime, int status) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		BigDecimal paymentAmountCount = BigDecimal.ZERO;
		BigDecimal supplyPriceCount = BigDecimal.ZERO;
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		TimeCycleVo settleTimeVo = settleTimeService.getSettleTime();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("shopId", shopId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("cycle", settleTimeVo.getDuration());
		map.put("status", status);
		map.put("startIndex", page.getStart());
		map.put("pageSize", page.getLimit());

		List<MerchantBillVo> list = indentRepository.selectSettlementBill(map);
		result.setResultList(CommonUtil.listObjTransToListMap(list));
		int count = indentRepository.selectSettlementBillCountPage(map);
		result.setTotalRecords(count);

		for (MerchantBillVo merchantBillVo : list) {
			paymentAmountCount = paymentAmountCount.add(merchantBillVo.getPaymentAmount());
			supplyPriceCount = supplyPriceCount
					.add(merchantBillVo.getSupplyPrice() != null ? merchantBillVo.getSupplyPrice() : BigDecimal.ZERO);
		}

		returnMap.put("page", result);
		returnMap.put("paymentAmountCount", paymentAmountCount);
		returnMap.put("supplyPriceCount", supplyPriceCount);

		return returnMap;
	}

	@Override
	public List<SubbranchVo> getSubbranch() {
		List<SubbranchVo> subbranchVos = new ArrayList<SubbranchVo>();
		List<Subbranch> subbranchs = subbranchExtendMapper.selectByPage(null, null, 0, 9999);
		for (Subbranch subbranch : subbranchs) {
			SubbranchVo subbranchVo = new SubbranchVo();
			subbranchVo.setId(subbranch.getId() + "");
			subbranchVo.setName(subbranch.getName());
			subbranchVos.add(subbranchVo);
		}

		return subbranchVos;
	}

	@Override
	public void settlementBill(String idStr, int status) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			IndentBillVo indentBillVo = new IndentBillVo();
			indentBillVo.setIndentId(id);
			indentBillVo.setStatus(status);
			indentBillService.updateIndentBill(indentBillVo);
		}
	}

	@Override
	public void addSettlementBill(String indentId) {
		IndentBillVo indentBillVo = new IndentBillVo();
		indentBillVo.setId(IdGenerator.getDefault().nextId() + "");
		indentBillVo.setIndentId(indentId);
		indentBillVo.setStatus(1);
		indentBillVo.setCreateTime(new Date());

		indentBillService.addIndentBill(indentBillVo);
	}

	@Override
	public String exportExcel(String orderId, String shopId, Date startTime, Date endTime, int status, 
			HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal paymentAmountCount = BigDecimal.ZERO;
		BigDecimal supplyPriceCount = BigDecimal.ZERO;
		TimeCycleVo settleTimeVo = settleTimeService.getSettleTime();
		map.put("orderId", orderId);
		map.put("shopId", shopId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("cycle", settleTimeVo.getDuration());
		map.put("status", status);
		if (status == 2) {
			map.put("payStatus", "已结算待付款");
		} else if (status == 3) {
			map.put("payStatus", "已结算已付款");
		}

		List<ExportExcelBillVo> list = indentRepository.exportExcel(map);
		for (ExportExcelBillVo merchantBillVo : list) {
			paymentAmountCount = paymentAmountCount.
					add(merchantBillVo.getPaymentAmount()!= null ? merchantBillVo.getPaymentAmount() : BigDecimal.ZERO);
			supplyPriceCount = supplyPriceCount.
					add(merchantBillVo.getSupplyPrice() != null ? merchantBillVo.getSupplyPrice() : BigDecimal.ZERO);
		}

		ExportExcel2<ExportExcelBillVo> ex = new ExportExcel2<ExportExcelBillVo>();
		String[] headers = { "店铺名称", "姓名", "银行卡号", "银行全称", "开户行", "商品名称", "商品个数", 
				"支付时间", "结算周期", "可结算日期", "支付金额(含运费)", "运费", "结算金额", "付款状态" };
		
		// 获取服务器tomcat服务器项目部署的路径的绝对地址
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		String serverRealPath = application.getRealPath("/");
		isExist(serverRealPath + "billList");
		String fileName = "billList/bill-" + System.currentTimeMillis() + ".xls";
		OutputStream outputStream = new FileOutputStream(serverRealPath + fileName);
		
		ex.exportExcel(headers, list, outputStream, "yyyy-MM-dd hh:mm:ss",paymentAmountCount,supplyPriceCount);
		outputStream.close();

		return "/" + fileName;
	}

	@Override
	public Map<String, Object> selectTotalBill() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BigDecimal paymentAmountCount = BigDecimal.ZERO;// 总收入
		BigDecimal settlementAmountCount = BigDecimal.ZERO; // 待结算金额
		BigDecimal payAmountCount = BigDecimal.ZERO; // 已结算待付款金额
		BigDecimal alreadypayAmountCount = BigDecimal.ZERO; // 已结算金额
		TimeCycleVo settleTimeVo = settleTimeService.getSettleTime();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cycle", settleTimeVo.getDuration());

		List<MerchantBillVo> list = indentRepository.selectSettlementBill(map);
		for (MerchantBillVo merchantBillVo : list) {
			paymentAmountCount = paymentAmountCount.add(merchantBillVo.getPaymentAmount());
			BigDecimal supplyPrice = merchantBillVo.getSupplyPrice();
			if (supplyPrice != null) {
				if (merchantBillVo.getStatus() == 2) {
					payAmountCount = payAmountCount.add(supplyPrice);
				} else if (merchantBillVo.getStatus() == 3) {
					alreadypayAmountCount = alreadypayAmountCount.add(supplyPrice);
				} else {
					settlementAmountCount = settlementAmountCount.add(supplyPrice);
				}
			}
		}

		returnMap.put("paymentAmountCount", paymentAmountCount);
		returnMap.put("settlementAmountCount", settlementAmountCount);
		returnMap.put("payAmountCount", payAmountCount);
		returnMap.put("alreadypayAmountCount", alreadypayAmountCount);

		return returnMap;
	}

	/**
	 * 查询店铺结算信息
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@Override
	public Map<String, Object> getShopBill(long shopId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
//		int[] arr = { 1, 2, 3 };
		String[] arrIndex = { "noSettled", "canSettled", "settled" };
		map.put("shopId", shopId);
		List<MerchantBillVo> list = indentRepository.selectSettlementBill(map);
		BigDecimal money1 = BigDecimal.ZERO; //已结算
		BigDecimal money2 = BigDecimal.ZERO; //可结算
		BigDecimal money3 = BigDecimal.ZERO; //未结算
		TimeCycleVo settleTimeVo = settleTimeService.getSettleTime();
		Date date = DateParseUtil.getBeforeDate(new Date(), Integer.valueOf(settleTimeVo.getDuration()));
		
		if (list != null && list.stream().count() > 0) {
			/*for (int i = 0; i < 3; i++) {
				money = new BigDecimal(0.00);
				for (MerchantBillVo vo : list) {
					if (arr[i] == vo.getStatus()&&vo.getSupplyPrice()!=null) {
						money.add(vo.getSupplyPrice());
					}
				}
				result.put(arrIndex[i], money.setScale(2, RoundingMode.HALF_DOWN).toString());
			}*/
			for (MerchantBillVo vo : list) {
				if (3 == vo.getStatus()) {
					if(vo.getSupplyPrice() != null){
						money1 = money1.add(vo.getSupplyPrice());
					}
				} else if(2 == vo.getStatus()){
					if(vo.getSupplyPrice() != null){
						money2 = money2.add(vo.getSupplyPrice());
					}
				} else if(1 == vo.getStatus()){
					if(vo.getFinishTime().getTime() > date.getTime()){
						if(vo.getSupplyPrice() != null){
							money3 = money3.add(vo.getSupplyPrice());
						}
					} else{
						if(vo.getSupplyPrice() != null){
							money2 = money2.add(vo.getSupplyPrice());
						}
					}
				}
			}
			result.put("settled", money1.setScale(2, RoundingMode.HALF_DOWN).toString());
			result.put("canSettled", money2.setScale(2, RoundingMode.HALF_DOWN).toString());
			result.put("noSettled", money3.setScale(2, RoundingMode.HALF_DOWN).toString());
		} else {
			for (int i = 0; i < 3; i++) {
				result.put(arrIndex[i], "0.00");
			}
		}
		return result;
	}

	public static void isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	@Override
	public boolean indentExist(String indentId){
		
		return indentRepository.indentExist(Long.valueOf(indentId));
	}

	/**
	 * 获取供货价
	 */
	public Map<String, Object> getShopIdAndSkuId(long shopId, long skuId) {
		return indentBillService.getShopIdAndSkuId(shopId,skuId);
	}
}