package com.club.web.stock.domain;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

import com.club.web.stock.domain.repository.CargoBaseSkuTypeRepository;
import com.club.web.stock.domain.repository.CargoBrandRepository;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.stock.domain.repository.CargoRepository;
import com.club.web.stock.domain.repository.CargoSkuRepository;
import com.club.web.stock.domain.repository.CargoSkuTypeRepository;
import com.club.web.stock.domain.repository.CargoSupplierRepository;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.listener.CargoListenerManager;

@Configurable
public class CargoDo extends BaseDo {
	
    private long id;
    private long supplierId;
    private long brandId;
    private long classifyId;
    private String name;
    private String cargoNo;
    private String description;
    private long smallImageId;
    private long showImageGroupId;
    private long detailImageGroupId;
    
    private List<CargoSkuTypeDo> skuTypeList;
    
    @Autowired private CargoRepository cargoRepository;
    @Autowired private CargoSkuRepository cargoSkuRepository;
    @Autowired private CargoSkuTypeRepository cargoSkuTypeRepository;
    @Autowired private CargoClassifyRepository cargoClassifyRepository;
    @Autowired private CargoSupplierRepository cargoSupplierRepository;
    @Autowired private CargoBrandRepository cargoBrandRepository;
	@Autowired private CargoListenerManager goodsListener;
	@Autowired private StockManagerRepository stockManagerRepository;
    
    @Autowired private CargoBaseSkuTypeRepository cargoBaseSkuTypeRepository;
    
    public CargoSkuDo getSku(long skuId) {
    	CargoSkuDo csDo = cargoSkuRepository.getById(skuId);
    	if(csDo!=null && csDo.getCargoId()==id)
    		return csDo;
    	return null;
    }
    
    public int getTotalCount(){
    	return stockManagerRepository.queryStockTotalByCargoId(id);
    }
    
    public List<CargoSkuDo> getSkuList() {
    	return cargoSkuRepository.getListByCargoId(id);
    }
    public List<CargoSkuTypeDo> getSkuTypeList() {
    	if(skuTypeList==null)
    		skuTypeList = cargoSkuTypeRepository.getListByCargoId(id);
    	return skuTypeList;
    }
    public CargoSkuItemDo getSkuItem(long itemId){
    	List<CargoSkuTypeDo> skuTypeList = getSkuTypeList();
    	for (CargoSkuTypeDo cstDo : skuTypeList)
    		for (CargoSkuItemDo csiDo : cstDo.getSkuItemList())
    			if(csiDo.getCargoBaseSkuItemId()==itemId)
    				return csiDo;
    	return null;
    }
    
    public boolean deleteAllSku(){
    	boolean flag = true;
    	for(CargoSkuDo csDo: getSkuList()) {
			Assert.state(csDo.getTotalCout()==0, "SKU["+csDo.getCode()+"]尚有"+csDo.getTotalCout()+"件未处理");
			if(!goodsListener.deleteCargoSku(csDo.getId())) {
				flag = false;
				continue;
			}
			csDo.delete();
    	}
    	return flag;
    }
    
    public void deleteSku(long... skuIds) {
    	if(skuIds!=null)
	    	for (long skuId : skuIds)
				for (CargoSkuDo csDo : getSkuList())
					if(csDo.getId()==skuId) {
						Assert.state(csDo.getTotalCout()==0, "SKU["+csDo.getCode()+"]尚有"+csDo.getTotalCout()+"件未处理");
						if(goodsListener.deleteCargoSku(skuId))
							csDo.delete();
					}
    }
    
    public void updateSku(long creatorId, long skuId, String code, BigDecimal price) {
    	CargoSkuDo csDo = getSku(skuId);
    	Assert.notNull(csDo, "更新SKU错误");
    	csDo.setCode(code);
    	csDo.setPrice(price);
    	csDo.setUpdateBy(creatorId);
    	csDo.update();
    }
    
    public CargoSkuDo addSku(long creatorId, String code, BigDecimal price, long[] skuItems) {
    	checkSkuItems(skuItems);
    	CargoSkuDo csDo = cargoSkuRepository.create(creatorId, id, code, price);
    	try{
    		csDo.insert();
    	} catch(Exception e) {
			if(e instanceof BatchUpdateException || e instanceof DuplicateKeyException)
				throw new RuntimeException("货品保存失败，SKU编号["+csDo.getCode()+"]已存在");
    	}
    	csDo.setSkuItems(creatorId, skuItems);
    	return csDo;
    }
    
    private void checkSkuItems(long[] skuItems) {
    	Assert.state(skuItems!=null && skuItems.length>0, "SKU选定项错误");
    	List<CargoSkuTypeDo> skuTypeList = getSkuTypeList();
    	Assert.state(skuTypeList.size()==skuItems.length, "SKU选定项规格类型错误");
    	Set<Long> selectSkuTypeId = new HashSet<>();
		NEXT_CARGO_SKU_TYPE: 
    	for (CargoSkuTypeDo cstDo : skuTypeList) {
    		boolean flag = false;
    		for (long skuItem : skuItems)
    			for (CargoSkuItemDo csiDo : cstDo.getSkuItemList())
    				if(csiDo.getCargoBaseSkuItemId()==skuItem){
    					flag = true;
    					selectSkuTypeId.add(cstDo.getId());
    					continue NEXT_CARGO_SKU_TYPE;
    				}
    		Assert.state(flag, "SKU选定项不匹配");
    	}
    	Assert.state(selectSkuTypeId.size()==skuItems.length, "SKU选定项不正确");
    }
    
    /**
     * 保存SKU列表
     */
    public List<CargoSkuTypeDo> setSkuTypes(long creatorId, long... skuTypeIds) {
		List<CargoSkuTypeDo> list = getSkuTypeList();
		// 清除所有没在这次保存列表中的skuType
		CARGO_SKU_TYPE_LIST: 
		for(int i=list.size()-1; i>=0; i--) {
	    	for (long skuTypeId : skuTypeIds)
	    		if(list.get(i).getCargoBaseSkuTypeId()==skuTypeId)
	    			continue CARGO_SKU_TYPE_LIST;
			list.remove(i).delete();
		}
		// 增加所有额外的skuType
    	for (long skuTypeId : skuTypeIds) {
			boolean flag = false;
			CargoSkuTypeDo temp = null;
			for(int i=0; i<list.size(); i++)
				if(list.get(i).getCargoBaseSkuTypeId()==skuTypeId) {
					flag = true;
					temp = list.get(i);
					break;
				}
			CargoBaseSkuTypeDo cbstDo;
			try{
				cbstDo = cargoBaseSkuTypeRepository.selectCargoBaseSkuTypeById(skuTypeId);
			}catch(Exception e){
				cbstDo = null;
			}
			if(flag) { // 已存在的数据做更新
//				if(cbstDo!=null){
//					temp.setName(cbstDo.getName());
//					temp.setType(cbstDo.getType());
//					temp.setUpdateBy(creatorId);
//					temp.update();
//				}
			} else { // 不存在的数据做插入
				Assert.notNull(cbstDo, "数据对象不能为空");
				temp = cargoSkuTypeRepository.create(creatorId, cbstDo.getId(), id, cbstDo.getName(), cbstDo.getType());
				temp.insert();
				list.add(temp);
			}
		}
    	return getSkuTypeList();
    }
    
    public void insert(){
    	cargoRepository.insert(this);
    }
    
    public void update(){
    	cargoRepository.update(this);
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public CargoSupplierDo getSupplier(){
		return cargoSupplierRepository.getCargoSupplierDoById(supplierId);
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public CargoBrandDo getBrand(){
		return cargoBrandRepository.getCargoBrandDoById(brandId);
	}
	public List<CargoClassifyDo> getClassifyList(){
		List<CargoClassifyDo> list = new ArrayList<>();
		CargoClassifyDo classify = cargoClassifyRepository.findDoById(classifyId);
		list.add(classify);
		while(classify!=null && classify.getParentId()!=null)
			if((classify = cargoClassifyRepository.findDoById(classify.getParentId()))!=null && classify.getName()!=null && !classify.getName().isEmpty())
				list.add(0, classify);
		return list;
	}
	public long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(long classifyId) {
		this.classifyId = classifyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getSmallImageId() {
		return smallImageId;
	}
	public void setSmallImageId(long smallImageId) {
		this.smallImageId = smallImageId;
	}
	public long getShowImageGroupId() {
		return showImageGroupId;
	}
	public void setShowImageGroupId(long showImageGroupId) {
		this.showImageGroupId = showImageGroupId;
	}
	public long getDetailImageGroupId() {
		return detailImageGroupId;
	}
	public void setDetailImageGroupId(long detailImageGroupId) {
		this.detailImageGroupId = detailImageGroupId;
	}

	public void delete() {
		setSkuTypes(0);
		cargoRepository.delete(id);
	}
}
