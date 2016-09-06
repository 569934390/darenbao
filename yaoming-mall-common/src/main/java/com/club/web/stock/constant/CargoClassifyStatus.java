package com.club.web.stock.constant;

/**
 * 货品分类状态枚举
 * @author zhuzd
 *
 */
public enum CargoClassifyStatus {

	// TODO 枚举类型，还是别使用中文，看起来怪怪的，有空的时候改掉好了
	// 可以使用Eclipse的rename功能，一次性改完所有地方
	启用("start",1),禁用("stop",0);
	
	private String name;
    private int dbData;
    
    
    private CargoClassifyStatus(String name,int dbData) {
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
        for (CargoClassifyStatus c : CargoClassifyStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (CargoClassifyStatus c : CargoClassifyStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static String getNameByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (CargoClassifyStatus c : CargoClassifyStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.getName();
	            }
	        }
		}
		return result;
	}
}
