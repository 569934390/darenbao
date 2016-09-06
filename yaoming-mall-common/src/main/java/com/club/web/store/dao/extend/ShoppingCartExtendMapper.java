package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.ShoppingCartMapper;
import com.club.web.store.domain.ShoppingCartDo;
import com.club.web.store.vo.ShoppingCartVo;

public interface ShoppingCartExtendMapper extends ShoppingCartMapper {
	ShoppingCartDo queryCartObjByCondition(@Param("userId") long userId, @Param("goodsId") long goodsId,
			@Param("storeId") long storeId, @Param("optionType") int optionType, @Param("status") int status);

	void deleteShoppingCartGoods(@Param("userId") long userId, @Param("goodsIds") List<Long> goodsIds,
			@Param("shopId") long shopId, @Param("optionType") int optionType, @Param("status") int status);

	List<ShoppingCartVo> queryShoppingCartByUserId(@Param("userId") long userId, @Param("shopId") long shopId,
			@Param("optionType") int optionType, @Param("status") int status);

	List<ShoppingCartVo> queryShoppingStoreByUserId(@Param("userId") long userId, @Param("shopId") long shopId,
			@Param("optionType") int optionType, @Param("status") int status);

	int getCartCount(@Param("userId") long userId, @Param("shopId") long shopId, @Param("optionType") int optionType);

}