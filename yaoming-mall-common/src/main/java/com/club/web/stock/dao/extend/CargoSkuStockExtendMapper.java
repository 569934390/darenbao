package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoSkuStockMapper;
import com.club.web.stock.dao.base.po.CargoSkuStock;
import com.club.web.stock.vo.CargoSkuStockVo;

public interface CargoSkuStockExtendMapper extends CargoSkuStockMapper {
	CargoSkuStock queryBySkuId(@Param("skuId") long skuId);

	int queryStockMsgTotal(@Param("nodeList") List<Long> nodeList, @Param("brand") long brand,
			@Param("matchParam") String matchParam);

	List<CargoSkuStockVo> queryStockMsg(@Param("nodeList") List<Long> nodeList, @Param("brand") long brand,
			@Param("matchParam") String matchParam, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	List<CargoSkuStockVo> queryNormalStockMsgByIds(@Param("ids") List<Long> ids);

	int queryStockCountBySkuId(@Param("skuId") long skuId);

	int queryStockTotalBySkuId(@Param("skuId") long skuId);

	int queryStockTotalByCargoId(@Param("cargoId") long cargoId);

	void deleteStockBySkuId(@Param("skuId") long skuId);

	void deleteStockByCargoId(@Param("cargoId") long cargoId);
}