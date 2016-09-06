package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.CarouselImgDo;
import com.club.web.store.vo.CarouselImgPrevVo;

/**
 * 轮播图仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-12
 */
public interface CarouselImgRepository extends CommonRepository {
	/**
	 * 根据参数查询轮播图对象-最特色
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return Map<String, Object>
	 * @add by 2016-04-12
	 */
	List<Map<String, Object>> queryCarouselImgList(int status, String matchParam, int startIndex, int pageSize);

	/**
	 * 根据参数查询轮播图对象-凯渥
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return Map<String, Object>
	 * @add by 2016-04-12
	 */
	List<Map<String, Object>> queryBannerImgList(int status, String matchParam, int startIndex, int pageSize);

	/**
	 * 根据参数查询轮播图对象总数-最特色
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-12
	 */
	int queryCarouselImgListTotal(int status, String matchParam);

	/**
	 * 根据参数查询轮播图对象总数-凯渥
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-12
	 */
	int queryBannerImgListTotal(int status, String matchParam);

	/**
	 * 创建对象
	 * 
	 * @param paramMap
	 * @return CarouselImgDo
	 * @add by 2016-04-13
	 */
	CarouselImgDo createCarouseObj(Map<String, Object> paramMap);

	/**
	 * 删除对象
	 * 
	 * @param ids
	 * @return void
	 * @add by 2016-04-13
	 */
	void delCarouselImg(List<Long> ids);

	/**
	 * 根据Id查询对象信息
	 * 
	 * @param id
	 * @return CarouselImgDo
	 * @add by 2016-04-13
	 */
	CarouselImgDo queryCarouselById(long id);

	/**
	 * 根据条件参数轮播图片
	 * 
	 * @param categoryId
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	List<CarouselImgPrevVo> getCarouselByCatory(long categoryId);

	/**
	 * 根据id查询富文本
	 * 
	 * @param id
	 * @return String
	 * @add by 2016-04-15
	 */
	Map<String, Object> getRichTextById(long id);
}
