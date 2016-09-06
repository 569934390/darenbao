package com.club.web.spread.constant;

/**
 * 商品上架下架类型枚举
 * @author Czj
 *
 */
public enum SpreadContentTypeEnum {

	商品("good",1),店铺("store",0);
	
	private String name;
    private int dbData;
    
    
    private SpreadContentTypeEnum(String name,int dbData) {
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
        for (SpreadContentTypeEnum c : SpreadContentTypeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (SpreadContentTypeEnum c : SpreadContentTypeEnum.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
}
