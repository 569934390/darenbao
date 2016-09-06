package com.club.web.coupon.constant;


public enum CouponStatusEnum {
	/**
	 * 兑换券状态枚举类型
	 * @author Czj
	 *
	 */
		未分配("unallocation",0),未兑换("unexchanged",1),已兑换("exchanged",2),已禁用("forbidden",3);
		
		private String name;
	    private int dbData;
	    
	    
	    private CouponStatusEnum(String name,int dbData) {
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
	        for (CouponStatusEnum c : CouponStatusEnum.values()) {
	            if (c.getName().equals(name)) {
	                return c.dbData;
	            }
	        }
	        return 0;
	    }

		public static String getTextByDbData(Integer status) {
			String result = "";
			if(status != null){
				for (CouponStatusEnum c : CouponStatusEnum.values()) {
		            if (c.getDbData() == status) {
		                return c.name();
		            }
		        }
			}
			return result;
		}
	}


