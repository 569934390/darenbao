package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.CarouselImg;

public interface CarouselImgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarouselImg record);

    int insertSelective(CarouselImg record);

    CarouselImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarouselImg record);

    int updateByPrimaryKeyWithBLOBs(CarouselImg record);

    int updateByPrimaryKey(CarouselImg record);
}