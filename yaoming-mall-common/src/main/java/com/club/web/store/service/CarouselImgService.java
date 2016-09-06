package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.vo.CarouselImgPrevVo;

/**
 * 轮播图-service接口
 * 
 * @author wqh
 * @add by 2016-04-12
 */
public interface CarouselImgService {

	/**
	 * 根据参数查询轮播图信息-最特色
	 * 
	 * @param page
	 * @add by 2016-04-12
	 */
	Page<Map<String, Object>> queryCarouselImgSer(Page<Map<String, Object>> page);

	/**
	 * 根据参数查询轮播图信息-凯渥
	 * 
	 * @param page
	 * @add by 2016-04-12
	 */
	Page<Map<String, Object>> queryBannerImgSer(Page<Map<String, Object>> page);

	/**
	 * 新增保存轮播图信息
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> saveCarouselImgSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 删除轮播图信息
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> delCarouselImgSer(Map<String, Object> paramMap);

	/**
	 * 修改轮播图信息排序
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> updateCarouselImgSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 修改轮播图信息状态
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> updateCarouselImgStatusSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 根据条件参数轮播图片
	 * 
	 * @param category
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	List<CarouselImgPrevVo> getCarouselByCatory(String category);

	/**
	 * 根据id查询富文本
	 * 
	 * @param id
	 * @return String
	 * @add by 2016-04-15
	 */
	Map<String, Object>  getRichTextById(String id);

}
