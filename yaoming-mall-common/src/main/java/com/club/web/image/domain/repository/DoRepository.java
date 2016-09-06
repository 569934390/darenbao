package com.club.web.image.domain.repository;

import java.util.List;

import com.club.web.image.domain.ImageDo;
import com.club.web.image.service.vo.ImageVo;


public interface DoRepository {
	public ImageDo saveImage(ImageDo imageDo);
	public ImageDo create(String url);
	public ImageDo selectImageById(long id);
	public List<ImageDo> selectImagesByGroupId(long groupId);
	public ImageDo selectImageByUrl(String url);
	public void  deleteByGroupId(long groupId);
	public void updateImage(ImageVo imageVo);
	public void deleteById(long id);
	public ImageDo create(long groupId,Long creator,String url);
}
