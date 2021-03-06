package com.club.web.deal.constant;

/**
 * 订单付款方式枚举
 * @author zhuzd
 *
 */
public enum IndentPayType {

	微信支付("wechat",1),支付宝支付("alipay",2),兑换券("兑换券",3);
	
	private String name;
    private int dbData;
    
    
    private IndentPayType(String name,int dbData) {
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
        for (IndentPayType c : IndentPayType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (IndentPayType c : IndentPayType.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static IndentPayType getStatusByDbData(Integer status) {
		if(status != null){
			for (IndentPayType c : IndentPayType.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
