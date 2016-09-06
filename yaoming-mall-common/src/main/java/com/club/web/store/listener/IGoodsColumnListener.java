package com.club.web.store.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.util.listener.IBaseListener;

public interface IGoodsColumnListener extends IBaseListener {
	
	/**
	 * 删除栏目 
	 * @return true:成功,false:失败
	 */
	public boolean deleteGoodsColumn(long goodsColumnId);
}
