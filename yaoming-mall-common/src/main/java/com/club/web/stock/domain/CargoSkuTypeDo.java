package com.club.web.stock.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.club.web.stock.domain.repository.CargoBaseSkuItemRepository;
import com.club.web.stock.domain.repository.CargoSkuItemRepository;
import com.club.web.stock.domain.repository.CargoSkuTypeRepository;
import com.club.web.stock.vo.CargoBaseSkuItemVo;

@Configurable
public class CargoSkuTypeDo extends BaseDo {
	
    private long id;
    private long cargoId;
    private long cargoBaseSkuTypeId;
    private int type;
    private String name;
    
    private List<CargoSkuItemDo> skuItemList;

    @Autowired private CargoSkuTypeRepository cargoSkuTypeRepository;
    @Autowired private CargoSkuItemRepository cargoSkuItemRepository;
    
    @Autowired private CargoBaseSkuItemRepository cargoBaseSkuItemRepository;
    
    public List<CargoSkuItemDo> getSkuItemList(){
    	if(skuItemList == null)
    		skuItemList = cargoSkuItemRepository.getListBySkuTypeId(id);
    	return skuItemList;
    }

	public void setSkuItems(long creatorId, long[] skuItemIds) {
		List<CargoSkuItemDo> list = getSkuItemList();
		// 清除所有没在这次保存列表中的skuItem
		CARGO_SKU_ITEM_LIST: 
		for(int i=list.size()-1; i>=0; i--) {
	    	for (long skuItemId : skuItemIds)
	    		if(list.get(i).getCargoBaseSkuItemId()==skuItemId)
	    			continue CARGO_SKU_ITEM_LIST;
			list.remove(i).delete();
		}
		// 增加所有额外的skuItem
    	for (long skuItemId : skuItemIds) {
			boolean flag = false;
			CargoSkuItemDo temp = null;
			for(CargoSkuItemDo csiDo: list)
				if(csiDo.getCargoBaseSkuItemId()==skuItemId) {
					flag = true;
					temp = csiDo;
					break;
				}
			CargoBaseSkuItemVo cbsiDo = cargoBaseSkuItemRepository.selectSkuItemById(skuItemId);
			if(flag) { // 已存在的数据做更新
//				if(cbsiDo!=null) {
//					temp.setValue(cbsiDo.getValue());
//					temp.setName(cbsiDo.getName());
//					temp.setUpdateBy(creatorId);
//					temp.update();
//				}
			} else { // 不存在的数据做插入
				Assert.notNull(cbsiDo, "数据对象不能为空");
				temp = cargoSkuItemRepository.create(creatorId, id, Long.valueOf(cbsiDo.getId()), cbsiDo.getName(), cbsiDo.getValue());
				temp.insert();
				list.add(temp);
			}
		}
	}

	public void delete() {
		setSkuItems(0, new long[]{});
		cargoSkuTypeRepository.delete(id);
	}

	public void insert() {
		cargoSkuTypeRepository.insert(this);
	}

	public void update() {
		cargoSkuTypeRepository.update(this);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCargoId() {
		return cargoId;
	}
	public void setCargoId(long cargoId) {
		this.cargoId = cargoId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCargoBaseSkuTypeId() {
		return cargoBaseSkuTypeId;
	}
	public void setCargoBaseSkuTypeId(long cargoBaseSkuTypeId) {
		this.cargoBaseSkuTypeId = cargoBaseSkuTypeId;
	}
    
}