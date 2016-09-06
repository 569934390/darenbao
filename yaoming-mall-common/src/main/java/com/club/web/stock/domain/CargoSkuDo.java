package com.club.web.stock.domain;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.club.web.stock.domain.repository.CargoRepository;
import com.club.web.stock.domain.repository.CargoSkuItemRepository;
import com.club.web.stock.domain.repository.CargoSkuRepository;
import com.club.web.stock.domain.repository.StockManagerRepository;

@Configurable
public class CargoSkuDo extends BaseDo {
	
    private long id;
    private long cargoId;
    private String code;
    private BigDecimal price;
	
	@Autowired private CargoRepository cargoRepository;
	@Autowired private CargoSkuRepository cargoSkuRepository;
	@Autowired private CargoSkuItemRepository cargoSkuItemRepository;
	@Autowired private StockManagerRepository stockManagerRepository;
	
	public CargoDo getCargo(){
		return cargoRepository.getCargoById(cargoId);
	}
	public List<CargoSkuItemDo> getItemList(){
		return cargoSkuItemRepository.getListBySkuId(id);
	}
	public void delete() {
		deleteItems();
		cargoSkuRepository.delete(id);
	}
	public void update() {
		cargoSkuRepository.update(this);
	}
	public void insert() {
		cargoSkuRepository.insert(this);
	}
	private void deleteItems(){
		cargoSkuItemRepository.deleteSelectedItemsBySkuId(id);
	}
	public List<CargoSkuItemDo> setSkuItems(long creatorId, long[] skuItems) {
		deleteItems();
		CargoDo cDo = cargoRepository.getCargoById(cargoId);
    	for (long skuItem : skuItems) {
			CargoSkuItemDo csiDo = cDo.getSkuItem(skuItem);
			Assert.notNull(csiDo, "数据对象不能为空");
			cargoSkuItemRepository.insertSelectedSkuItem(creatorId, id, csiDo.getId());
		}
    	return getItemList();
	}
	
	public int getTotalCout(){
		return stockManagerRepository.queryStockTotalBySkuId(id);
	}
	
	public String getSpecs(){
		List<CargoSkuItemDo> list = getItemList();
		StringBuilder sb = new StringBuilder();
		for (CargoSkuItemDo csido : list)
			sb.append(csido.getName()).append(",");
		if(sb.length()>0)
			return sb.deleteCharAt(sb.length()-1).toString();
		return "";
	}

	public long getId() {
		return id;
	}
	public long getCargoId() {
		return cargoId;
	}
	public String getCode() {
		return code;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setCargoId(long cargoId) {
		this.cargoId = cargoId;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
}