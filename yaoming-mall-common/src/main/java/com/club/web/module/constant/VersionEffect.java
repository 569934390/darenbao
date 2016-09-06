package com.club.web.module.constant;

/**
 * 生效，失效 枚举
 * @author zhuzd
 *
 */
public enum VersionEffect {

	生效("able",1),失效("disable",0);
	
	private String name;
    private int dbData;
    
    
    private VersionEffect(String name,int dbData) {
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
        for (VersionEffect c : VersionEffect.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (VersionEffect c : VersionEffect.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static VersionEffect getStatusByDbData(Integer status) {
		if(status != null){
			for (VersionEffect c : VersionEffect.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
