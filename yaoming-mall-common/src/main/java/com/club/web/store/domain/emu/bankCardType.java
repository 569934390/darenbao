package com.club.web.store.domain.emu;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.club.framework.util.ChineseEnumJsonSerializer;

/**
 * @author 鑫荣
 */
@JsonSerialize(using=ChineseEnumJsonSerializer.class)
public enum bankCardType {
	client("会员"),store("分店"),headStore("总店");
	
	private final String chineseName;
	private bankCardType(String chineseName) {
		this.chineseName=chineseName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public static bankCardType valueOf(int value){
		return bankCardType.values()[value];
	}
}