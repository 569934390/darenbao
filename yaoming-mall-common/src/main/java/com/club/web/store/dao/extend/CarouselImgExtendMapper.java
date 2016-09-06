package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.CarouselImgMapper;
import com.club.web.store.domain.CarouselImgDo;
import com.club.web.store.vo.CarouselImgPrevVo;

public interface CarouselImgExtendMapper extends CarouselImgMapper {
	List<Map<String, Object>> queryCarouselImgList(@Param("status") int status, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	List<Map<String, Object>> queryBannerImgList(@Param("status") int status, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int queryCarouselImgListTotal(@Param("status") int status, @Param("matchParam") String matchParam);

	int queryBannerImgListTotal(@Param("status") int status, @Param("matchParam") String matchParam);

	void delCarouselImg(@Param("ids") List<Long> ids);

	CarouselImgDo queryCarouselById(@Param("id") long id);

	List<CarouselImgPrevVo> getCarouselByCatory(@Param("categoryId") long categoryId);

	Map<String, Object> getRichTextById(@Param("id") long id);
}