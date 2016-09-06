package com.club.web.image.dao.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.image.dao.base.po.Image;
import com.club.web.image.dao.extend.ImageExtendMapper;
import com.club.web.image.domain.ImageDo;
import com.club.web.image.domain.repository.DoRepository;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.util.IdGenerator;


/**
 * 
 * @author caizj
 * 图片管理
 */
@Repository
public class ImageRepository implements DoRepository{
	
    @Autowired    ImageExtendMapper imageDao;
    
	@Override
	public ImageDo saveImage(ImageDo imageDo) {
		// TODO Auto-generated me
		Image image =new Image();
		BeanUtils.copyProperties(imageDo, image);
    	imageDao.insert(image);
    	//返回该记录的id值
		return imageDo;
	}
	
	/**
	 * 接收一个url参数，返回一个ImageDo对象。
	 * 类似于一个工厂，每次都产生新的对象，非单例模式
	 */
	@Override
	public ImageDo create(String url){
		Image image = new  Image();
		ImageDo imageDo =  new ImageDo();
		image.setId(IdGenerator.getDefault().nextId());
		image.setGroupid(IdGenerator.getDefault().nextId());
		image.setCreateTime(new Date());
		image.setCreateBy(1L);
		image.setPicUrl(url);
		BeanUtils.copyProperties(image, imageDo);
		return imageDo;
	}
	
	/**
	 * 创建一个新的对象，重载了上面的create方法
	 */
	@Override
	public ImageDo create(long groupId,Long creator,String url){
		Image image = new  Image();
		ImageDo imageDo =  new ImageDo();
		image.setId(IdGenerator.getDefault().nextId());
		image.setGroupid(groupId);
		if(creator !=null){
			image.setCreateBy(creator);
		}
		image.setPicUrl(url);
		image.setCreateTime(new Date());
		BeanUtils.copyProperties(image, imageDo);
		return imageDo;
	};
	
	@Override
	public ImageDo selectImageById(long id) {
		// TODO Auto-generated me
    	//根据id查询图片对象
		ImageDo imageDo = new ImageDo();
		Image image = imageDao.selectByPrimaryKey(id);
		BeanUtils.copyProperties(image, imageDo);
		return imageDo;
	}
	
	@Override
	public List<ImageDo> selectImagesByGroupId(long groupId) {
		// TODO Auto-generated me
    	//根据id查询图片对象
		List<ImageDo> result = new ArrayList<>();
		List<Image> list = imageDao.selectImagesByGroupId(groupId);
		for (Image image : list) {
			ImageDo ido = new ImageDo();
			BeanUtils.copyProperties(image, ido);
			result.add(ido);
		}
		return result;
	}
	
	public ImageDo selectImageByUrl(String url){
		ImageDo imageDo = new ImageDo();
		Image image=imageDao.selectImageByUrl(url);
		BeanUtils.copyProperties(image, imageDo);
		return imageDo;
	}
	
	public void  deleteByGroupId(long groupId){
		imageDao.deleteByGroupId(groupId);
	};
	
	public void updateImage(ImageVo imageVo){
		Image image =new Image();
		BeanUtils.copyProperties(imageVo, image);
		imageDao.updateByPrimaryKey(image);
	}
	
	public void deleteById(long id){
		imageDao.deleteByPrimaryKey(id);
	}
	
}
