package com.club.web.deal.constant;

/**
 * 订单状态枚举
 * @author zhuzd
 *
 */
public enum IndentStatus {

	待付款("pay",1),待发货("ship",2),退款申请("refund",3),待收货("receive",5),退货申请("return",6),
	退货中("returning",7),已退款("refunded",8),已退货("returned",9),已收货("received",10),已取消("cancel",11),
	待评价("evaluate",12),已完成("done",13);
	
	private String name;
    private int dbData;
    
    
    private IndentStatus(String name,int dbData) {
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
        for (IndentStatus c : IndentStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (IndentStatus c : IndentStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static IndentStatus getStatusByDbData(Integer status) {
		if(status != null){
			for (IndentStatus c : IndentStatus.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
