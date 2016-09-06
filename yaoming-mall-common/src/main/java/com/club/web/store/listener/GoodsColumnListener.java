package com.club.web.store.listener;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class GoodsColumnListener implements IGoodsColumnListener {
	
	@Override
	public boolean deleteGoodsColumn(long goodsColumnId) {
		System.out.println("deletegoodsColumn "+ goodsColumnId);
		
		return true;
	}
}
