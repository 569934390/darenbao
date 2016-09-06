package com.club.web.stock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.BeanUtils;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.CargoBaseSkuItemDo;
import com.club.web.stock.domain.repository.CargoBaseSkuItemRepository;
import com.club.web.stock.service.CargoBaseSkuItemService;
import com.club.web.stock.vo.CargoBaseSkuItemVo;
import com.club.web.util.IdGenerator;

/**   
* @Title: CargoBaseSkuItemServiceImpl.java
* @Package com.club.web.stock.service.impl 
* @Description: 商品基础规格选项service层接口实现类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/

@Service("cargoBaseSkuItemService")
public class CargoBaseSkuItemServiceImpl implements CargoBaseSkuItemService {
	
	private Logger logger = LoggerFactory.getLogger(CargoBaseSkuTypeServiceImpl.class);
	
	@Autowired
	CargoBaseSkuItemRepository cargoBaseSkuItemRepository;
	
	@Autowired
	ImageService imageService;

	@Override
    public CargoBaseSkuItemVo addBaseSkuItem(CargoBaseSkuItemVo baseSkuItem) {
		CargoBaseSkuItemDo cargoBaseSkuItemDo = new CargoBaseSkuItemDo();
		BeanUtils.copyProperties(baseSkuItem, cargoBaseSkuItemDo);
		cargoBaseSkuItemDo.setId(IdGenerator.getDefault().nextId());
		cargoBaseSkuItemDo.setBaseSkuTypeId(Long.valueOf(baseSkuItem.getBaseSkuTypeId()));
		baseSkuItem.setId(cargoBaseSkuItemDo.getId()+"");
		// 如果传过来的图片不为空。则保存记录
		if("3".equals(baseSkuItem.getCode())){
			if (baseSkuItem.getValue() != null && !"".equals(baseSkuItem.getValue())) {
				try {
					ImageVo imageVo = imageService.saveImage(baseSkuItem.getValue());
					cargoBaseSkuItemDo.setValue(imageVo.getId() + "");
					baseSkuItem.setId(imageVo.getId()+"");
				} catch (Exception e) {
					logger.error("规格选项新增图片异常:", e);
				}
			}			
		}
		if("1".equals(baseSkuItem.getCode())){//规格类型为文本时，name栏位赋value的值
			cargoBaseSkuItemDo.setName(cargoBaseSkuItemDo.getValue());
		}
		try {
			cargoBaseSkuItemRepository.addBaseSkuItem(cargoBaseSkuItemDo);			
		} catch (Exception e) {
			logger.error("新增规格选项异常:", e);
		}
		
		return baseSkuItem;
    }
	
	@Override
	public List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(Long id, String type) {
		List<CargoBaseSkuItemVo> baseSkuItemVoLst = new ArrayList<CargoBaseSkuItemVo>();
		if("3".equals(type)){
			try {
				baseSkuItemVoLst = cargoBaseSkuItemRepository.selectSkuItemAndImgBySkuTypeId(id);				
			} catch (Exception e) {
				logger.error("根据ID查询规格选项异常:", e);
			}
		} else{
			try {
				baseSkuItemVoLst = cargoBaseSkuItemRepository.selectSkuItemBySkuTypeId(id);				
			} catch (Exception e) {
				logger.error("根据ID查询规格选项异常:", e);
			}
		}
		
		return baseSkuItemVoLst;
	}
	
	@Override
	public Map<String, Object> deleteSkuItemByBaseSkuTypeId(Long baseSkuTypeId, Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			cargoBaseSkuItemRepository.deleteSkuItemByBaseSkuTypeId(baseSkuTypeId,id);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("删除规格选项异常:", e);
		}
		
		return result;
	}
}
