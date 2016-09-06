package com.club.web.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.stock.domain.CargoBaseSkuTypeDo;
import com.club.web.stock.domain.repository.CargoBaseSkuTypeRepository;
import com.club.web.stock.service.CargoBaseSkuTypeService;
import com.club.web.stock.vo.CargoBaseSkuTypeEnum;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;
import com.club.web.util.CommonUtil;

/**   
* @Title: CargoBaseSkuTypeServiceImpl.java
* @Package com.club.web.stock.service.impl 
* @Description: 商品基础规格Service接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Service("cargoBaseSkuTypeService")
public class CargoBaseSkuTypeServiceImpl implements CargoBaseSkuTypeService {
	
	private Logger logger = LoggerFactory.getLogger(CargoBaseSkuTypeServiceImpl.class);
	
	@Autowired
	CargoBaseSkuTypeRepository cargoBaseSkuTypeRepository;

	/**
     * 新增规格
     * @param baseSkuType 规格对象值
     */
	@Override
    public Map<String, Object> addBaseSkuType(CargoBaseSkuTypeVo baseSkuType) {
		Map<String, Object> result = new HashMap<String, Object>();
		CargoBaseSkuTypeDo cargoBaseSkuTypeDo = cargoBaseSkuTypeRepository.voChangeDo(baseSkuType);
		try {
			result.put("success", true);
			result.put("msg", "新增成功");
			cargoBaseSkuTypeRepository.addBaseSkuType(cargoBaseSkuTypeDo);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("新增规格异常:", e);
		}
		return result;
    }
	
	@Override
	public Page<Map<String, Object>> selectBySkuNameAndSkuType(Page<Map<String, Object>> page, String skuName, String skuType) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		List<CargoBaseSkuTypeVo> list = new ArrayList<CargoBaseSkuTypeVo>();
		Long count = (long) 0;
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		if(skuName.isEmpty()){
			skuName = null;			
		} else{
			skuName = "%" + skuName +"%";
		}
		if("0".equals(skuType) || skuType.isEmpty()){
			skuType = null;			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("skuName", skuName);
		map.put("skuType", skuType);
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		try {
			list = cargoBaseSkuTypeRepository.selectBySkuNameAndSkuType(map);
		} catch (Exception e) {
			logger.error("查询规格异常:", e);
		}
		for(CargoBaseSkuTypeVo cargoBaseSkuTypeVoTmp : list){
			cargoBaseSkuTypeVoTmp.setTypeName(CargoBaseSkuTypeEnum.getTxt(Integer.valueOf(cargoBaseSkuTypeVoTmp.getType())));
		}
		try {
			count = cargoBaseSkuTypeRepository.queryCargoBaseSkuTypeCountPage(map);			
		} catch (Exception e) {
			logger.error("查询规格笔数异常:", e);
		}
		result.setResultList(CommonUtil.listObjTransToListMap(list));
		result.setTotalRecords(count.intValue());
		
		return result;
	}
	
	@Override
	public Map<String, Object> editBaseSkuType(CargoBaseSkuTypeVo baseSkuType) {
		Map<String, Object> result = new HashMap<String, Object>();
		CargoBaseSkuTypeDo cargoBaseSkuTypeDo = cargoBaseSkuTypeRepository.voChangeDo(baseSkuType);
		try {
			result.put("success", true);
			result.put("msg", "编辑成功");
			cargoBaseSkuTypeRepository.editBaseSkuType(cargoBaseSkuTypeDo);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("编辑规格异常:", e);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> deleteBaseSkuType(String idStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		try {
			for (String id : ids) {
				result.put("success", true);
				result.put("msg", "删除成功");
				cargoBaseSkuTypeRepository.deleteBaseSkuType(Long.parseLong(id));
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("删除规格异常:", e);
		}
		return result;
	}
	
	// TODO service的返回值和参数都不能有domain对象
	@Override
	public CargoBaseSkuTypeDo selectCargoBaseSkuTypeById(Long id) {
		CargoBaseSkuTypeDo cargoBaseSkuTypeDo = cargoBaseSkuTypeRepository.create();
		try {
			cargoBaseSkuTypeDo = cargoBaseSkuTypeRepository.selectCargoBaseSkuTypeById(id);
		} catch (Exception e) {
			logger.error("根据ID查询规格异常:", e);
		}
		
		return cargoBaseSkuTypeDo;
	}
	
	@Override
	public List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList() {
		List<CargoBaseSkuTypeVo> cargoBaseSkuTypeVoLst = new ArrayList<CargoBaseSkuTypeVo>();
		try {
			cargoBaseSkuTypeVoLst = cargoBaseSkuTypeRepository.selectCargoBaseSkuTypeList();
		} catch (Exception e) {
			logger.error("查询规格数组异常:", e);
		}
		
		return cargoBaseSkuTypeVoLst;
	}
}
