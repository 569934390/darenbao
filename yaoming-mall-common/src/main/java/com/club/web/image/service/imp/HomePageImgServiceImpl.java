package com.club.web.image.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.image.dao.extend.ImageExtendMapper;
import com.club.web.image.domain.ImageDo;
import com.club.web.image.domain.repository.DoRepository;
import com.club.web.image.service.HomePageImgService;
import com.club.web.image.service.vo.HomePageImgVo;
@Service("homePageImgService")
public class HomePageImgServiceImpl implements HomePageImgService{
	@Autowired
	DoRepository imageRepository;
	
	@Autowired
	ImageExtendMapper imageExtendMapper;
	@Override
	public Page getimgList(Page page) {
		List<HomePageImgVo> listVo = new ArrayList<HomePageImgVo>();
		//List<ImageDo> listDo = imageRepository.selectImagesByGroupId(Long.parseLong(page.getConditons().get("groupId").toString()));
		List<ImageDo> listDo = imageRepository.selectImagesByGroupId(1L);
		for(ImageDo imageDo: listDo){
			HomePageImgVo  imageVo = new HomePageImgVo();
			imageVo.setCreateBy(imageDo.getCreateBy()+"");
			imageVo.setCreateTime(imageDo.getCreateTime());
			imageVo.setGroupid(imageDo.getGroupid()+"");
			imageVo.setId(imageDo.getId()+"");
			imageVo.setPicUrl(imageDo.getPicUrl());
			listVo.add(imageVo);
		}
		if (listVo!=null&&listVo.size()>0) {
			page.setTotalRecords(listVo.size());
			page.setResultList(listVo);
		}
		
		 return page;
	}
	

	@Override
	public int saveOrUpdateImg(Long creator, Long groupId, String imgs) {
		String[] array=imgs.split(",");
		try {
			
			for(int i=0;i<array.length;i++){
				ImageDo imageDo = imageRepository.create(groupId, creator, array[i]);
				imageRepository.saveImage(imageDo);
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Imageservice--按组更新图片抛出异常！");
			
		}
		return 0;
	}
	@Override
	public int deletHomePageImg(String imgIds){
		if (!StringUtils.isEmpty(imgIds)) {
			String[] imgidList=imgIds.split(",");
			for (String imgId:imgidList) {
				if (!StringUtils.isEmpty(imgId))
					imageRepository.deleteById(Long.parseLong(imgId));
			}
		}
		return 0;
	}


	@Override
	public Page selectMoreColumnImg(Page page) {
		page.setResultList(imageExtendMapper.selectMoreColumnImg(1L, page.getStart(), page.getLimit()));
		return page;
	}
}
