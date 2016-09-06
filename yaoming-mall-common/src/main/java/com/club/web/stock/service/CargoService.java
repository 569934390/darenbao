package com.club.web.stock.service;

import java.util.List;

import com.club.core.common.Page;
import com.club.web.stock.vo.CargoInfoVo;
import com.club.web.stock.vo.CargoSaveVo;
import com.club.web.stock.vo.CargoSimpleInfoVo;
import com.club.web.stock.vo.CargoSkuSimpleVo;

/**
 * 
 * @author yunpeng.xiong
 *
 */
public interface CargoService {
	public void saveCargo(long creatorId, CargoSaveVo cargoSaveVo);

	public Page<CargoSimpleInfoVo> queryCargoList(Page<CargoSimpleInfoVo> page);

	public boolean delete(Long[] ids);

	public List<CargoSkuSimpleVo> getSkuList(long cargoId);

	public CargoInfoVo getCargoInfo(long cargoId);

	public boolean isSkuCanDelete(long skuId);

	int queryCargoCountByClassifyIds(List<Long> classifyIds);
}
