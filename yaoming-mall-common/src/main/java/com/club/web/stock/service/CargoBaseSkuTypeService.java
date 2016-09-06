package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.domain.CargoBaseSkuTypeDo;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;

/**   
* @Title: CargoBaseSkuTypeService.java
* @Package com.club.web.stock.service 
* @Description: 商品基础规格Service接口类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuTypeService {
	
	/**
     * 新增规格
     * @param baseSkuType 规格对象值
     */
	Map<String, Object> addBaseSkuType(CargoBaseSkuTypeVo baseSkuType);
    
    /**
     * 根据条件查询规格
     * @param skuName 规格名称
     * @param skuType 规格类型
     * @return 规格列表
     */
    Page<Map<String, Object>> selectBySkuNameAndSkuType(Page<Map<String, Object>> page, String skuName, String skuType);
    
    /**
     * 修改规格名称
     * @param baseSkuType 修改的规格值
     * @return
     */
    Map<String, Object> editBaseSkuType(CargoBaseSkuTypeVo baseSkuType);
    
    /**
     * 删除规格类型
     * @param idStr 规格ID串
     * @return
     */
    Map<String, Object> deleteBaseSkuType(String idStr);
    
    // TODO service的返回值和参数都不能有domain对象
    CargoBaseSkuTypeDo selectCargoBaseSkuTypeById(Long id);
    
    List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList();
}
