package com.club.web.store.listener;

import org.springframework.stereotype.Component;

import com.club.web.util.listener.BaseListenerMgr;

@Component
public class GoodsColumnListenerManager extends BaseListenerMgr<IGoodsColumnListener> implements IGoodsColumnListener {

	public GoodsColumnListenerManager() {
		super(IGoodsColumnListener.class);
	}
	
	@Override
	public boolean deleteGoodsColumn(long goodsColumnId) {
		boolean flag = true;
		for (IGoodsColumnListener clazz : getClasses())
			if(!(flag = clazz.deleteGoodsColumn(goodsColumnId)))
				break;
		return flag;
	}

}
