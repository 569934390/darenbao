package com.club.web.module.constant;

/**
 * 是否强制下载枚举
 * @author zhuzd
 *
 */
public enum VersionDownloadWay {

	非强制下载("optional",0),强制下载("coercive",1);
	
	private String name;
    private int dbData;
    
    
    private VersionDownloadWay(String name,int dbData) {
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
        for (VersionDownloadWay c : VersionDownloadWay.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (VersionDownloadWay c : VersionDownloadWay.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static VersionDownloadWay getStatusByDbData(Integer status) {
		if(status != null){
			for (VersionDownloadWay c : VersionDownloadWay.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
