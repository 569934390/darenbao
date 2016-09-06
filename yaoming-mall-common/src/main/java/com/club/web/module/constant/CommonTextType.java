package com.club.web.module.constant;

/**
 * 通用文本类型枚举
 * @author zhuzd
 *
 */
public enum CommonTextType {

	关于我们("our",1),我的协议("my",2),登录注册协议("register",3);
	
	private String name;
    private int dbData;
    
    
    private CommonTextType(String name,int dbData) {
        this.name = name;
        this.dbData = dbData;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public int getDbData() {
		return dbData;
	}

	public void setDbData(int dbData) {
		this.dbData = dbData;
	}

	@Override
    public String toString() {
        return this.dbData+"";
    }
	
	public static int getDbDataByName(String name) {
        for (CommonTextType c : CommonTextType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (CommonTextType c : CommonTextType.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static CommonTextType getStatusByDbData(Integer status) {
		if(status != null){
			for (CommonTextType c : CommonTextType.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
