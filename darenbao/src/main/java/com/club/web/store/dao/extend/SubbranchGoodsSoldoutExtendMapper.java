package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.SubbranchGoodSoldoutMapper;
import com.club.web.store.dao.base.po.SubbranchGoodSoldout;
import com.club.web.store.domain.SubbranchGoodsSoldoutDo;

public interface SubbranchGoodsSoldoutExtendMapper extends SubbranchGoodSoldoutMapper {

	int selectByGoodId(Long goodId);

	int selectBySubbranchIdCount(Long subbranchId);

	List<SubbranchGoodSoldout> selectBySubbranchIdList(@Param("subbranchId") Long subbranchId,
			@Param("start") int start, @Param("limit") int limit);

	int deleteSubbranchGoodsSoldout(SubbranchGoodSoldout subbranchGoodSoldout);

	int getShopGoodStatus(@Param("goodId") long goodId, @Param("shopId") long shopId);

	void deleteSubbranchGoodsSoldoutByCondition(@Param("shopId") long shopId, @Param("list") List<Long> list);
}
