package com.club.web.store.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.vo.GoodVo;

@Configurable
public class GoodsListenerColumnImpl implements IGoodsColumnListener {
	@Autowired 
	private TradeGoodExtendMapper tradeGoodExtendDao;
	/**
	 * 查询栏目ID在商品中是否有被引用，被引用则返回false表示不能删除
	 */
	@Override
	public boolean deleteGoodsColumn(long goodsColumnId) {
		System.out.println("deletegoodsColumn "+ goodsColumnId);
		List<GoodVo> list=tradeGoodExtendDao.selectGoodByCategory(goodsColumnId);
		if(list != null && list.size()!= 0){
			return false;
		}else{
			return true;
		}
		 
	}
}
