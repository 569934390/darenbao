package com.club.web.store.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.store.dao.base.po.CarouselImg;
import com.club.web.store.dao.extend.CarouselImgExtendMapper;
import com.club.web.store.domain.CarouselImgDo;
import com.club.web.store.domain.repository.CarouselImgRepository;
import com.club.web.store.vo.CarouselImgPrevVo;
import com.club.web.util.IdGenerator;

/**
 * @author wqh
 *
 */
@Repository
public class CarouselImgRepositoryImpl implements CarouselImgRepository {
	@Autowired
	CarouselImgExtendMapper caouselDao;

	/**
	 * 根据参数查询轮播图对象
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return Map<String, Object>
	 * @add by 2016-04-12
	 */
	@Override
	public List<Map<String, Object>> queryCarouselImgList(int status, String matchParam, int startIndex, int pageSize) {
		List<Map<String, Object>> list = caouselDao.queryCarouselImgList(status, matchParam, startIndex, pageSize);
		return list;
	}

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
	@Override
	public List<Map<String, Object>> queryBannerImgList(int status, String matchParam, int startIndex, int pageSize) {
		List<Map<String, Object>> list = caouselDao.queryBannerImgList(status, matchParam, startIndex, pageSize);
		return list;
	}

	/**
	 * 根据参数查询轮播图对象总数
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-12
	 */
	@Override
	public int queryCarouselImgListTotal(int status, String matchParam) {
		int total = caouselDao.queryCarouselImgListTotal(status, matchParam);
		return total;
	}

	/**
	 * 根据参数查询轮播图对象总数-凯渥
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-04-12
	 */
	@Override
	public int queryBannerImgListTotal(int status, String matchParam) {
		int total = caouselDao.queryBannerImgListTotal(status, matchParam);
		return total;
	}

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof CarouselImgDo) {
				CarouselImgDo carouse = (CarouselImgDo) t;
				CarouselImg carousePo = new CarouselImg();
				BeanUtils.copyProperties(carousePo, carouse);
				caouselDao.insert(carousePo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public <T> void update(T t) throws Exception {
		if (t != null) {
			if (t instanceof CarouselImgDo) {
				CarouselImgDo carouse = (CarouselImgDo) t;
				CarouselImg carousePo = new CarouselImg();
				BeanUtils.copyProperties(carousePo, carouse);
				caouselDao.updateByPrimaryKeyWithBLOBs(carousePo);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * 创建轮播图对象
	 * 
	 * @param paramMap
	 * @return CarouselImgDo
	 * @add by 2016-04-13
	 */
	@Override
	public CarouselImgDo createCarouseObj(Map<String, Object> paramMap) {
		CarouselImgDo carousel = null;
		long id = -1;
		if (paramMap.containsKey("carouse_id")) {
			id = paramMap.get("carouse_id") != null && !"".equals(paramMap.get("carouse_id").toString()) ? Long
					.valueOf(paramMap.get("carouse_id").toString()) : -1;
		}
		int lineStatus = Integer.valueOf(paramMap.get("lineStatus").toString());
		carousel = queryCarouselById(id);
		String picUrl = paramMap.get("picUrl").toString();
		int lastIndex = picUrl.indexOf("-img");
		if (lastIndex > 0) {
			picUrl = picUrl.substring(0, lastIndex);
		}
		if (carousel != null) {
			carousel.setFlag(0);
			carousel.setId(id);
			carousel.setExtendId(carousel.getPicUrl());
			carousel.setRemk(paramMap.get("remk").toString());
			carousel.setStatus(Integer.valueOf(paramMap.get("status").toString()));
			carousel.setCategory(Long.valueOf(paramMap.get("category").toString()));
			carousel.setSort(Integer.valueOf(paramMap.get("sort").toString()));
			carousel.setLineStatus(lineStatus);
			carousel.setUpdateTime(new Date());
			carousel.setPicUrl(picUrl);
			if (lineStatus == 0) {
				carousel.setRichText(paramMap.get("richTextVal") != null?paramMap.get("richTextVal").toString():"");
			} else {
				carousel.setRichText(paramMap.get("richTextUrl") != null?paramMap.get("richTextUrl").toString():"");
			}
		} else {
			carousel = new CarouselImgDo();
			carousel.setFlag(1);
			carousel.setId(IdGenerator.getDefault().nextId());
			carousel.setRemk(paramMap.get("remk").toString());
			carousel.setStatus(Integer.valueOf(paramMap.get("status").toString()));
			carousel.setCategory(Long.valueOf(paramMap.get("category").toString()));
			carousel.setSort(Integer.valueOf(paramMap.get("sort").toString()));
			carousel.setLineStatus(lineStatus);
			carousel.setCreateTime(new Date());
			carousel.setUpdateTime(new Date());
			carousel.setPicUrl(picUrl);
			if (lineStatus == 0) {
				carousel.setRichText(paramMap.get("richTextVal") != null?paramMap.get("richTextVal").toString():"");
			} else {
				carousel.setRichText(paramMap.get("richTextUrl") != null?paramMap.get("richTextUrl").toString():"");
			}
		}
		return carousel;
	}

	/**
	 * 删除对象
	 * 
	 * @param ids
	 * @return void
	 * @add by 2016-04-13
	 */
	@Override
	public void delCarouselImg(List<Long> ids) {
		caouselDao.delCarouselImg(ids);
	}

	/**
	 * 根据Id查询对象信息
	 * 
	 * @param id
	 * @return CarouselImgDo
	 * @add by 2016-04-13
	 */
	@Override
	public CarouselImgDo queryCarouselById(long id) {
		CarouselImgDo carousel = caouselDao.queryCarouselById(id);
		return carousel;
	}

	/**
	 * 根据条件参数轮播图片
	 * 
	 * @param categoryId
	 * @return List<CarouselImgPrevVo>
	 * @add by 2016-04-15
	 */
	public List<CarouselImgPrevVo> getCarouselByCatory(long categoryId) {
		List<CarouselImgPrevVo> list = caouselDao.getCarouselByCatory(categoryId);
		return list;
	}

	/**
	 * 根据id查询富文本
	 * 
	 * @param id
	 * @return String
	 * @add by 2016-04-15
	 */
	@Override
	public Map<String, Object> getRichTextById(long id) {
		Map<String, Object> result = caouselDao.getRichTextById(id);
		return result;
	}

}
