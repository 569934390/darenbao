package com.club.web.deal.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.deal.domain.IndentDo;
import com.club.web.deal.vo.CargoStockCheckVo;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.store.vo.ExportExcelBillVo;
import com.club.web.store.vo.MerchantBillVo;

/**
 * 订单仓库接口
 * 
 * @author zhuzd
 *
 */
public interface IndentRepository {

	IndentDo findDoById(Long id);

	List<IndentDo> findDoListByIds(String ids);

	void modify(IndentDo indentDo);

	void add(IndentDo indentDo);

	List<IndentDo> findDoListByBuyerIdAndStoreId(Map<String, Object> con);

	List<IndentExtendVo> getShopOrderList(Map<String, Object> con);

	List<CargoStockCheckVo> findCardoStockCheckVoByMap(Map<String, Object> con);

	List<MerchantBillVo> selectSettlementBill(Map<String, Object> map);

	int selectSettlementBillCountPage(Map<String, Object> map);

	List<ExportExcelBillVo> exportExcel(Map<String, Object> map);

	void delete(Long id);

	boolean indentExist(Long indentId);
}