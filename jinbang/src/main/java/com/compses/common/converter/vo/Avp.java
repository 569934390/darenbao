package com.compses.common.converter.vo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class Avp {
	public Avp(){
		
	}

	private String name;
	private String code;
	private String type;
	private String expression;
	private String alias;
	private String customize;
	private String option;
	private String flags;
	private String vendorId;
	private String src="固定值";
	private String avpValue;
	private String desc;

	private List<Avp> avp;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAvpValue() {
		return avpValue;
	}

	public void setAvpValue(String avpValue) {
		this.avpValue = avpValue;
	}

	public void setAvp(List<Avp> avp) {
		this.avp = avp;
	}

	public String getCustomize() {
		return customize;
	}

	public void setCustomize(String customize) {
		this.customize = customize;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<Avp> getAvp() {
		return avp;
	}

	public Map<String,Object> convertToMap(){
		Map<String,Object> attrs=new HashMap<String,Object>();
		try {
			attrs=BeanUtils.describe(this);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attrs;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
