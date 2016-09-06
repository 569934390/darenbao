package com.club.web.store.constant;

/**
 * 自动时间类型
 * @author zhuzd
 *
 */
public enum TimeCycleType {

	结算周期("settleTime",0),订单自动收货有效时间("indentShipTime",1);
	
	private String name;
    private int dbData;
    
    
    private TimeCycleType(String name,int dbData) {
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
        for (TimeCycleType c : TimeCycleType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (TimeCycleType c : TimeCycleType.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
}
