package com.club.web.deal.dao.extend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.deal.dao.base.IndentMapper;
import com.club.web.deal.dao.base.po.Indent;
import com.club.web.deal.vo.CargoStockCheckVo;
import com.club.web.deal.vo.IndentExport;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.deal.vo.IndentPageVo;
import com.club.web.store.vo.ExportExcelBillVo;
import com.club.web.store.vo.MerchantBillVo;

/**
 * 订单Dao扩展类
 * 
 * @author zhuzd
 *
 */
public interface IndentExtendMapper extends IndentMapper {

	int queryTotalByMap(Map<String, Object> con);

	List<IndentPageVo> queryIndentPoByMap(Map<String, Object> con);

	List<Indent> findListByIds(List<Long> strToLongList);

	List<Indent> findListByBuyerIdAndStoreId(Map<String, Object> con);

	List<IndentExtendVo> getShopOrderList(Map<String, Object> con);

	List<CargoStockCheckVo> findCardoStockCheckVoByMap(Map<String, Object> con);

	List<MerchantBillVo> selectSettlementBill(Map<String, Object> map);

	int selectSettlementBillCountPage(Map<String, Object> map);

	List<ExportExcelBillVo> selectExcelBill(Map<String, Object> map);

	Map<String, Object> getHistoryOrderTotalMsg(@Param("shopId") Long shopId);

	BigDecimal getTodayOrderTotalMsg(@Param("shopId") Long shopId, @Param("nowDate") String nowDate);

	BigDecimal getLastOrderTotalMsg(@Param("shopId") Long shopId, @Param("lastDate") String lastDate);

	Map<String, Object> findAmountByMap(Map<String, Object> con);
	
	List<IndentExport> queryIndentExportByMap(Map<String, Object> con);

	int indentExist(Long indentId);
}