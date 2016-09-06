package com.club.web.store.dao.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.Subbranch;
import com.club.web.store.dao.base.po.SubbranchGoodSoldout;
import com.club.web.store.dao.extend.SubbranchExtendMapper;
import com.club.web.store.dao.extend.SubbranchGoodsSoldoutExtendMapper;
import com.club.web.store.domain.SubbranchDo;
import com.club.web.store.domain.SubbranchGoodsSoldoutDo;
import com.club.web.store.domain.repository.SubbranchRepository;
import com.club.web.util.IdGenerator;

@Repository
public class SubbranchRepositoryImpl implements SubbranchRepository {

	@Autowired
	SubbranchExtendMapper subbranchExtendMapper;
	@Autowired
	SubbranchGoodsSoldoutExtendMapper subbranchGoodsSoldoutExtendMapper;

	@Override
	public int saveSubbranch(SubbranchDo subbranchDo) {
		Subbranch subbranch = new Subbranch();
		BeanUtils.copyProperties(subbranchDo, subbranch);

		int result = subbranchExtendMapper.insert(subbranch);

		return result;
	}

	@Override
	public int updateSubbranch(SubbranchDo subbranchDo) {
		Subbranch subbranch = new Subbranch();
		BeanUtils.copyProperties(subbranchDo, subbranch);

		int result = subbranchExtendMapper.updateByPrimaryKeySelective(subbranch);

		return result;
	}

	@Override
	public int updateSubbranchState(SubbranchDo subbranchDo) {
		Subbranch subbranch = new Subbranch();
		BeanUtils.copyProperties(subbranchDo, subbranch);
		int result = subbranchExtendMapper.updateByPrimaryKeySelective(subbranch);
		return result;
	}

	@Override
	public int saveSubbranchGoodsSoldout(SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo) {
		int result = 0;
		SubbranchGoodSoldout subbranchGoodSoldout = new SubbranchGoodSoldout();
		BeanUtils.copyProperties(subbranchGoodsSoldoutDo, subbranchGoodSoldout);
		if (subbranchGoodsSoldoutExtendMapper.selectByGoodId(subbranchGoodSoldout.getGoodId()) == 0) {
			result = subbranchGoodsSoldoutExtendMapper.insert(subbranchGoodSoldout);
		}
		return result;
	}

	@Override
	public int deleteSubbranchGoodsSoldout(SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo) {
		SubbranchGoodSoldout subbranchGoodSoldout = new SubbranchGoodSoldout();
		BeanUtils.copyProperties(subbranchGoodsSoldoutDo, subbranchGoodSoldout);
		int result = subbranchGoodsSoldoutExtendMapper.deleteSubbranchGoodsSoldout(subbranchGoodSoldout);
		return result;
	}

	@Override
	public int deleteSubbranch(SubbranchDo subbranchDo) {
		Subbranch subbranch = new Subbranch();
		BeanUtils.copyProperties(subbranchDo, subbranch);
		int result = subbranchExtendMapper.deleteByPrimaryKey(subbranch.getId());
		return result;
	}

	@Override
	public SubbranchGoodsSoldoutDo create(long shopId, long goodId) {
		SubbranchGoodsSoldoutDo subObj = new SubbranchGoodsSoldoutDo();
		subObj.setId(IdGenerator.getDefault().nextId());
		subObj.setSubranchId(shopId);
		subObj.setGoodId(goodId);
		subObj.setCreateTime(new Date());
		return subObj;
	}

	@Override
	public void save(SubbranchGoodsSoldoutDo obj) {
		SubbranchGoodSoldout subbranchGoodSoldout = new SubbranchGoodSoldout();
		BeanUtils.copyProperties(obj, subbranchGoodSoldout);
		subbranchGoodsSoldoutExtendMapper.insert(subbranchGoodSoldout);
	}

}
