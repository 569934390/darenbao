package com.club.web.stock.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.CargoSkuItemRepository;

@Configurable
public class CargoSkuItemDo extends BaseDo {
	
    private long id;
    private long cargoSkuTypeId;
    private long cargoBaseSkuItemId;
    private String value;
    private String name;

    @Autowired private CargoSkuItemRepository cargoSkuItemRepository;
    
	public void delete() {
		cargoSkuItemRepository.delete(id);
	}

	public void update() {
		cargoSkuItemRepository.update(this);
	}

	public void insert() {
		cargoSkuItemRepository.insert(this);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCargoSkuTypeId() {
		return cargoSkuTypeId;
	}
	public void setCargoSkuTypeId(long cargoSkuTypeId) {
		this.cargoSkuTypeId = cargoSkuTypeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCargoBaseSkuItemId() {
		return cargoBaseSkuItemId;
	}
	public void setCargoBaseSkuItemId(long cargoBaseSkuItemId) {
		this.cargoBaseSkuItemId = cargoBaseSkuItemId;
	}

}