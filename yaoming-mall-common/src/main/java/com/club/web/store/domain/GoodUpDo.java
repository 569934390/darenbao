/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.domain;


/**
 *@Title:
 *@Description:
 *@Author:Czj
 *@Since:2016年4月7日
 *@Version:1.1.0
 */
public class GoodUpDo {

	// 上架的skuId
		private long cargoSkuId;
		// 上架的数量
		private long num;
		//商品SkuId
		private long goodSkuId;
		/**
		 * @return the cargoSkuId
		 */
		public long getCargoSkuId() {
			return cargoSkuId;
		}
		/**
		 * @param cargoSkuId the cargoSkuId to set
		 */
		public void setCargoSkuId(long cargoSkuId) {
			this.cargoSkuId = cargoSkuId;
		}
		/**
		 * @return the num
		 */
		public long getNum() {
			return num;
		}
		/**
		 * @param num the num to set
		 */
		public void setNum(long num) {
			this.num = num;
		}
		/**
		 * @return the goodSkuId
		 */
		public long getGoodSkuId() {
			return goodSkuId;
		}
		/**
		 * @param goodSkuId the goodSkuId to set
		 */
		public void setGoodSkuId(long goodSkuId) {
			this.goodSkuId = goodSkuId;
		}
		
}
