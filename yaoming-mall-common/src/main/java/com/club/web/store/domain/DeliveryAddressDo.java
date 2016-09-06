package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.club.web.store.domain.repository.DeliveryAddressRepository;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
@Configurable
public class DeliveryAddressDo {
	@Autowired
	DeliveryAddressRepository deliveryAddressRepository;
	
    private Long id;

    private String name;

    private String address;

    private String province;

    private String city;

    private String county;

    private String mobile;

    private String postcode;

    private String note;

    private Date createTime;

	private String provinceName;

    private String cityName;

    private String countyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	Assert.isTrue(name!=""&&name!=null, "地址不能为空");
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
    	
    	Assert.isTrue(address!=""&&address!=null, "地址不能为空");
        this.address = address == null ? null : address.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
    	Assert.notNull(mobile, "门店电话不能为空");
    	this.mobile = mobile == null ? null : mobile.trim();
    	Assert.isTrue(CommonUtil.isMobile(mobile) || CommonUtil.isPhone(mobile),"请输入正确的电话!");
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
    
    public int insert(){
    	int result=0;
    	this.createTime=new Date();
    	this.id = IdGenerator.getDefault().nextId();
    	result=deliveryAddressRepository.saveDeliveryAddress(this);
    	return result;
    }
    public int update(){
    	
    	
    	int result=deliveryAddressRepository.updateDeliveryAddress(this);
    	return result;
    }
    
}