package com.club.web.store.domain.repository;

import com.club.web.store.domain.SubbranchDo;
import com.club.web.store.domain.SubbranchGoodsSoldoutDo;
import com.club.web.store.vo.SubbranchGoodSoldoutVo;

public interface SubbranchRepository {

	int saveSubbranch(SubbranchDo subbranchDo);

	int updateSubbranch(SubbranchDo subbranchDo);

	int updateSubbranchState(SubbranchDo subbranchDo);

	int saveSubbranchGoodsSoldout(SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo);

	int deleteSubbranchGoodsSoldout(SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo);

	int deleteSubbranch(SubbranchDo subbranchDo);

	SubbranchGoodsSoldoutDo create(long shopId, long goodId);

	void save(SubbranchGoodsSoldoutDo obj);
}
