package com.club.web.stock.vo;

import java.util.ArrayList;
import java.util.List;

public class CargoInfoVo {
	private String cargoId;
	private String cargoNo;
	private String cargoName;
	private List<CargoSimpleIdNameVo> classifyList = new ArrayList<>();
	private CargoSimpleIdNameVo supplier;
	private CargoSimpleIdNameVo brand;
	private ImageVo smallImage;
	private ImageGroupVo showImages;
	private ImageGroupVo detailImages;
	private List<CargoSkuTypeSimpleVo> skuTypes;
	private List<CargoSkuSimpleVo> skus;
	public String getCargoId() {
		return cargoId;
	}
	public void setCargoId(String cargoId) {
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
	public List<CargoSimpleIdNameVo> getClassifyList() {
		return classifyList;
	}
	public void addClassify(String id, String name){
		classifyList.add(new CargoSimpleIdNameVo(id, name));
	}
	public CargoSimpleIdNameVo getSupplier() {
		return supplier;
	}
	public void setSupplier(String id, String name) {
		this.supplier = new CargoSimpleIdNameVo(id, name);
	}
	public CargoSimpleIdNameVo getBrand() {
		return brand;
	}
	public void setBrand(String id, String name) {
		this.brand = new CargoSimpleIdNameVo(id, name);
	}
	public ImageVo getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(ImageVo smallImage) {
		this.smallImage = smallImage;
	}
	public void setSmallImage(long id, String url) {
		this.smallImage = new ImageVo();
		this.smallImage.setId(id);
		this.smallImage.setUrl(url);
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
	public List<CargoSkuTypeSimpleVo> getSkuTypes() {
		return skuTypes;
	}
	public void setSkuTypes(List<CargoSkuTypeSimpleVo> skuTypes) {
		this.skuTypes = skuTypes;
	}
	public List<CargoSkuSimpleVo> getSkus() {
		return skus;
	}
	public void setSkus(List<CargoSkuSimpleVo> skus) {
		this.skus = skus;
	}
	public static class CargoSimpleIdNameVo {
		private String id;
		private String name;
		public CargoSimpleIdNameVo(String id, String name) {
			this.id = id;
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}
	}
	public static class CargoSkuTypeSimpleVo {
		private String id;
		private String name;
		private String type;
		private List<CargoSkuItemSimpleVo> items;
		public CargoSkuTypeSimpleVo(String id, String name, String type, List<CargoSkuItemSimpleVo> items) {
			this.id = id;
			this.name = name;
			this.type = type;
			this.items = items;
		}
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getType() {
			return type;
		}
		public List<CargoSkuItemSimpleVo> getItems() {
			return items;
		}
	}
	public static class CargoSkuItemSimpleVo {
		private String id;
		private String name;
		private String value;
		public CargoSkuItemSimpleVo(String id, String name, String value) {
			this.id = id;
			this.name = name;
			this.value = value;
		}
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return value;
		}
	}
}
