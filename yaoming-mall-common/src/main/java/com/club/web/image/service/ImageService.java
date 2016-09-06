package com.club.web.image.service;

import java.io.File;
import java.util.List;

import com.club.web.image.dao.base.po.Image;
import com.club.web.image.service.vo.ImageVo;

/**
 * 
 * @author Caizj
 *
 */
public interface ImageService {
   public ImageVo saveImage(String url);
   public ImageVo selectImageById(long id);
   public List<ImageVo> selectImagesByGroupId(long groupId);
   public List<ImageVo> selectImagesByUrl(List<String> urlList);
   public int  saveOrUpdateByGroupId(long groupId,Long creator,String array[]);
   public void updateImage(ImageVo imageVo);
   public void deleteById(long id);
}
