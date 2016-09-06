package com.club.web.stock.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.stock.dao.base.CargoMapper;
import com.club.web.stock.vo.CargoSimpleEditVo;
import com.club.web.stock.vo.CargoSimpleInfoVo;

/**
 * 
 * @author yunpeng.xiong
 *
 */
public interface CargoExtendMapper extends CargoMapper {

	public List<CargoSimpleInfoVo> queryCargoList(Map<String, Object> params);
	public int queryCargoListCount(Map<String, Object> params);
	public CargoSimpleEditVo getCargoSimpleEditVo(long cargoId);
}
