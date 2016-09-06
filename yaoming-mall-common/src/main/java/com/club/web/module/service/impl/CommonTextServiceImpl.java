package com.club.web.module.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.module.domain.CommonTextDo;
import com.club.web.module.domain.repository.CommonTextRepository;
import com.club.web.module.service.CommonTextService;
import com.club.web.module.vo.CommonTextVo;
import com.club.web.util.IdGenerator;
/**
 * 通用文本服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class CommonTextServiceImpl implements CommonTextService {
	
	@Autowired
	private CommonTextRepository commonTextRepository;

	@Override
	public CommonTextVo findTextVoByType(int type) {
		return getVoByDomain(commonTextRepository.findTextDoByType(type));
	}

	@Override
	public boolean addOrModify(CommonTextVo commonTextVo) {
		commonTextVo.setId(StringUtils.isEmpty(commonTextVo.getId())?IdGenerator.getDefault().nextId()+"":commonTextVo.getId());
		getDomainByVo(commonTextVo).saveOrUpdate();
		return true;
	}
	
	private List<CommonTextVo> getVoListByDomainList(List<CommonTextDo> srcs){
		List<CommonTextVo> targets = new ArrayList<CommonTextVo>();
		if(srcs != null && srcs.size() != 0){
			for (CommonTextDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}
	
	private List<CommonTextDo> getDomainListByVoList(List<CommonTextVo> srcs){
		List<CommonTextDo> targets = new ArrayList<CommonTextDo>();
		if(srcs != null && srcs.size() != 0){
			for (CommonTextVo src : srcs) {
				targets.add(getDomainByVo(src));
			}
		}
		return targets;
	}
	
	private CommonTextVo getVoByDomain(CommonTextDo src){
		CommonTextVo target = null;
		if(src != null){
			target = new CommonTextVo();
			target.setId(src.getId() == null ? null:src.getId()+"");
			target.setType(src.getType());
			target.setContent(src.getContent());
			target.setFileUrl(src.getFileUrl());
		}
		return target;
	}

	private CommonTextDo getDomainByVo(CommonTextVo src){
		CommonTextDo target = null;
		if(src != null){
			target = new CommonTextDo();
			target.setId(src.getId() == null ? null:Long.valueOf(src.getId()));
			target.setType(src.getType());
			target.setContent(src.getContent());
			target.setFileUrl(src.getFileUrl());
		}
		return target;
	}
	
}
