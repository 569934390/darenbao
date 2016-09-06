package com.club.web.module.constant;

/**
 * 企业版,正式版 版本状态枚举
 * @author zhuzd
 *
 */
public enum VersionStatus {

	正式版("oe",1),企业版("ee",0);
	
	private String name;
    private int dbData;
    
    
    private VersionStatus(String name,int dbData) {
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
        for (VersionStatus c : VersionStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (VersionStatus c : VersionStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static VersionStatus getStatusByDbData(Integer status) {
		if(status != null){
			for (VersionStatus c : VersionStatus.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
