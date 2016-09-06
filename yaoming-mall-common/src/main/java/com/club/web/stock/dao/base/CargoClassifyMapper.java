package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoClassify;
/**
 * 货品分类Dao
 * @author zhuzd
 *
 */
public interface CargoClassifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoClassify record);

    int insertSelective(CargoClassify record);

    CargoClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoClassify record);

    int updateByPrimaryKey(CargoClassify record);
}