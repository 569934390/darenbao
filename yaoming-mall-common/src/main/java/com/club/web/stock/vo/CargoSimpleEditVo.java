package com.club.web.stock.vo;

public class CargoSimpleEditVo {
	private long cargoId;
	private String cargoName;
	private String cargoNo;
	private long classifyId;
	private long supplierId;
	private String supplierName;
	private long brandId;
	private String brandName;
	private long smallImageId;
	private String smallImageUrl;
	private long showImageGroupId;
	private long detailImageGroupId;
	public long getCargoId() {
		return cargoId;
	}
	public long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(long classifyId) {
		this.classifyId = classifyId;
	}
	public void setCargoId(long cargoId) {
		this.cargoId = cargoId;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public long getSmallImageId() {
		return smallImageId;
	}
	public void setSmallImageId(long smallImageId) {
		this.smallImageId = smallImageId;
	}
	public String getSmallImageUrl() {
		return smallImageUrl;
	}
	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
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
}
