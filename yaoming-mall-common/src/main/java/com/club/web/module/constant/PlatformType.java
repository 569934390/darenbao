package com.club.web.module.constant;

/**
 * 平台枚举
 * @author zhuzd
 *
 */
public enum PlatformType {

	安卓("android",0),苹果("ios",1),其他("other",2);
	
	private String name;
    private int dbData;
    
    
    private PlatformType(String name,int dbData) {
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
        for (PlatformType c : PlatformType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 2;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (PlatformType c : PlatformType.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static PlatformType getStatusByDbData(Integer status) {
		if(status != null){
			for (PlatformType c : PlatformType.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
