package com.club.web.store.constant;

/**
 * 商品上架下架类型枚举
 * @author Czj
 *
 */
public enum GoodStatus {

	上架("up",1),下架("down",0),未上架("new",2);
	
	private String name;
    private int dbData;
    
    
    private GoodStatus(String name,int dbData) {
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
        for (GoodStatus c : GoodStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (GoodStatus c : GoodStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
}
