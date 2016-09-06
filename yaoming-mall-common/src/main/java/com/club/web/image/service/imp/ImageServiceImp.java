package com.club.web.image.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.BeanUtils;
import com.club.web.image.domain.ImageDo;
import com.club.web.image.domain.repository.DoRepository;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
@Service("imageSaveService")
public class ImageServiceImp implements ImageService {
	
	Logger logger =LoggerFactory.getLogger(getClass());
	
	@Autowired
	DoRepository imageRepository;
	
	/**
	 * 调用dao层的save方法把记录存入数据库
	 * 参数url
	 */
	@Override
	public ImageVo saveImage(String url) {
		
		// TODO Auto-generated method stub
		   ImageVo imageVo = new ImageVo();
		   ImageDo imageDo=imageRepository.create(url);
			//返回一个Image对象
		   imageDo=imageRepository.saveImage(imageDo);
		   BeanUtils.copyProperties(imageDo, imageVo);
	       return imageVo;	    
	}
	
	/**
	 * 调用dao层的方法根据id查询图片对象
	 * 参数ImageDo：domain类型对象用来保存值
	 */
	@Override
	public ImageVo selectImageById(long id) {
		
		// TODO Auto-generated method stub	
		ImageVo imageVo = new ImageVo();
		ImageDo imageDo = imageRepository.selectImageById(id);
		 BeanUtils.copyProperties(imageDo, imageVo);
		 
	    return imageVo;   
	}
	
	/**
	 * 根据groupId返回一组image
	 */
	public List<ImageVo> selectImagesByGroupId(long groupId){
		List<ImageVo> listVo = new ArrayList<ImageVo>();
		List<ImageDo> listDo = imageRepository.selectImagesByGroupId(groupId);
		for(ImageDo imageDo: listDo){
			ImageVo  imageVo = new ImageVo();
			BeanUtils.copyProperties(imageDo, imageVo);
			listVo.add(imageVo);
		}
		 return listVo;
	}
	
	/**
	 * 
	 * @param urlList 一组url
	 * @return 返回一组图片
	 * @Description:
	 */
	public List<ImageVo> selectImagesByUrl(List<String> urlList){
		List<ImageVo> voList = new ArrayList<ImageVo>();
		for(String url:urlList){
			ImageVo imageVo = new ImageVo();
			ImageDo imageDo =imageRepository.selectImageByUrl(url);
			BeanUtils.copyProperties(imageDo, imageVo);
			voList.add(imageVo);
		}
		return voList;
	}
	
	
	public int  saveOrUpdateByGroupId(long groupId,Long creator,String array[]){
		try {
			imageRepository.deleteByGroupId(groupId);
			for(int i=0;i<array.length;i++){
				ImageDo imageDo = imageRepository.create(groupId, creator, array[i]);
				imageRepository.saveImage(imageDo);
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Imageservice--按组更新图片抛出异常！");
			logger.error("ImageServiceImp saveOrUpdateByGroupId error : {}", e);
			throw e;
		}
	}
	
	/**更新图片
	 * 
	 */
	public void updateImage(ImageVo imagevo){
		
		imageRepository.updateImage(imagevo);
	}
	
	/**
	 * 根据id删除图片
	 */
	public void deleteById(long id){
		imageRepository.deleteById(id);
	}
	
	
}
