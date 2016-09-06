package com.club.web.module.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.module.dao.base.po.CommonText;
import com.club.web.module.dao.extend.CommonTextExtendMapper;
import com.club.web.module.domain.CommonTextDo;
import com.club.web.module.domain.repository.CommonTextRepository;


/**
 * 通用文本仓库接口实现类
 * @author zhuzd
 *
 */
@Repository
public class CommonTextRepositoryImpl implements CommonTextRepository{

	@Autowired
	private CommonTextExtendMapper commonTextDao;

	@Override
	public CommonTextDo findTextDoByType(int type) {
		return getDomainByPo(commonTextDao.findTextByType(type));
	}
	
	@Override
	public CommonTextDo findTextDoById(Long id) {
		return getDomainByPo(commonTextDao.selectByPrimaryKey(id));
	}

	@Override
	public int add(CommonTextDo commonTextDo) {
		return commonTextDao.insert(getPoByDomain(commonTextDo));
	}

	@Override
	public int modify(CommonTextDo commonTextDo) {
		return commonTextDao.updateByPrimaryKeyWithBLOBs(getPoByDomain(commonTextDo));
	}
	
	private List<CommonText> getPoListByDomainList(List<CommonTextDo> srcs){
		List<CommonText> targets = new ArrayList<CommonText>();
		if(srcs != null && srcs.size() != 0){
			for (CommonTextDo src : srcs) {
				targets.add(getPoByDomain(src));
			}
		}
		return targets;
	}
	
	private List<CommonTextDo> getDomainListByPoList(List<CommonText> srcs){
		List<CommonTextDo> targets = new ArrayList<CommonTextDo>();
		if(srcs != null && srcs.size() != 0){
			for (CommonText src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}
	
	private CommonText getPoByDomain(CommonTextDo src){
		CommonText target = null;
		if(src != null){
			target = new CommonText();
			target.setId(src.getId());
			target.setType(src.getType());
			target.setContent(src.getContent());
			target.setFileUrl(src.getFileUrl());
		}
		return target;
	}

	private CommonTextDo getDomainByPo(CommonText src){
		CommonTextDo target = null;
		if(src != null){
			target = new CommonTextDo();
			target.setId(src.getId());
			target.setType(src.getType());
			target.setContent(src.getContent());
			target.setFileUrl(src.getFileUrl());
		}
		return target;
	}	
}
