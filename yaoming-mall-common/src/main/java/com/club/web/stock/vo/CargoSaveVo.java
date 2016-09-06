package com.club.web.stock.vo;

public class CargoSaveVo {
	private long cargoId;
	private String cargoNo;
	private String cargoName;
	private long classifyId;
	private long supplierId;
	private long brandId;
	private ImageVo smallImage;
	private ImageGroupVo showImages;
	private ImageGroupVo detailImages;
	private CargoSkuTypeSaveVo[] skuTypes;
	private long[] skuDelete;
	private CargoSkuChangeVo[] skuChange;
	private CargoSkuAddVo[] skuAdd;
	public long getCargoId() {
		return cargoId;
	}
	public void setCargoId(long cargoId) {
		this.cargoId = cargoId;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(long classifyId) {
		this.classifyId = classifyId;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public ImageVo getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(ImageVo smallImage) {
		this.smallImage = smallImage;
	}
	public ImageGroupVo getShowImages() {
		return showImages;
	}
	public void setShowImages(ImageGroupVo showImages) {
		this.showImages = showImages;
	}
	public ImageGroupVo getDetailImages() {
		return detailImages;
	}
	public void setDetailImages(ImageGroupVo detailImages) {
		this.detailImages = detailImages;
	}
	public CargoSkuTypeSaveVo[] getSkuTypes() {
		return skuTypes;
	}
	public void setSkuTypes(CargoSkuTypeSaveVo[] skuTypes) {
		this.skuTypes = skuTypes;
	}
	public long[] getSkuDelete() {
		return skuDelete;
	}
	public void setSkuDelete(long[] skuDelete) {
		this.skuDelete = skuDelete;
	}
	public CargoSkuChangeVo[] getSkuChange() {
		return skuChange;
	}
	public void setSkuChange(CargoSkuChangeVo[] skuChange) {
		this.skuChange = skuChange;
	}
	public CargoSkuAddVo[] getSkuAdd() {
		return skuAdd;
	}
	public void setSkuAdd(CargoSkuAddVo[] skuAdd) {
		this.skuAdd = skuAdd;
	}
	
}
