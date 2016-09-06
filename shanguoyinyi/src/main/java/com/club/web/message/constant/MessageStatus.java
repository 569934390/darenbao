package com.club.web.message.constant;

/**
 * 消息状态枚举
 * @author zhuzd
 *
 */
public enum MessageStatus {

	消息回复("notice",0),提醒发货("noticeShip",1),待发货("ship",2),申请退款("refund",3);
	
	private String name;
    private int dbData;
    
    
    private MessageStatus(String name,int dbData) {
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
        for (MessageStatus c : MessageStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (MessageStatus c : MessageStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static MessageStatus getStatusByDbData(Integer status) {
		if(status != null){
			for (MessageStatus c : MessageStatus.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
