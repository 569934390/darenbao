package com.club.web.stock.service;

import java.util.List;

import com.club.core.common.Page;
import com.club.web.stock.vo.CargoClassifyAppVo;
import com.club.web.stock.vo.CargoClassifyVo;
/**
 * 货品分类服务接口
 * @author zhuzd
 *
 */
public interface CargoClassifyService {

	boolean updateStatus(String ids, Integer status,long userId) throws Exception;

	boolean deleteByIds(String ids) throws Exception;

	boolean add(long userId,CargoClassifyVo cargoClassifyVo) throws Exception;

	boolean modify(long userId,CargoClassifyVo cargoClassifyVo) throws Exception;
	
	CargoClassifyVo findVoByIdAndStatus(Long id,Integer status)throws Exception;
	
	List<CargoClassifyVo> getVoListByParentId(Long parentId,Integer status) throws Exception;

	Object getAppParents(String parentId) throws Exception;

	Page<CargoClassifyVo> list() throws Exception;
	
	List<Long> getAllIdsByIdAndStatus(Long id,Integer status) throws Exception;

	List<CargoClassifyVo> getVoAllList(Integer status);

	List<CargoClassifyAppVo> mobileFirstAndSecondList();

}
