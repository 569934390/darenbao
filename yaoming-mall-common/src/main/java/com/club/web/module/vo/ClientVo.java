package com.club.web.module.vo;

/**
 * 用户Vo
 * @author zhuzd
 *
 */
public class ClientVo {

	private String id;

    private String name;

    private String phone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
