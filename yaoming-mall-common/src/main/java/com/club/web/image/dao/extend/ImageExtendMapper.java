/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.image.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.image.dao.base.ImageMapper;
import com.club.web.image.dao.base.po.Image;
import com.club.web.image.service.vo.HomePageImgVo;

/**
 *@Title:自定义mapper接口
 *@Description:
 *@Author:Caizj
 *@Since:2016年3月23日
 *@Version:1.1.0
 */
public interface ImageExtendMapper extends ImageMapper{
   public List<Image> selectImagesByGroupId(@Param("groupId")long groupId);
   public Image selectImageByUrl(String url);
   public void  deleteByGroupId(@Param("groupId")long groupId);
   List<HomePageImgVo> selectMoreColumnImg(@Param("groupId")long groupId,@Param("start")int start,@Param("end")int end);
}
