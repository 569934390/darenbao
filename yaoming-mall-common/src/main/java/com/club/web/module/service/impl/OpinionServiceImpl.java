package com.club.web.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.ListUtils;
import com.club.framework.util.StringUtils;
import com.club.web.module.constant.PlatformType;
import com.club.web.module.dao.extend.OpinionExtendMapper;
import com.club.web.module.domain.OpinionDo;
import com.club.web.module.domain.repository.OpinionRepository;
import com.club.web.module.service.OpinionService;
import com.club.web.module.vo.ClientVo;
import com.club.web.module.vo.OpinionVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
/**
 * 意见反馈服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class OpinionServiceImpl implements OpinionService {
	
	@Autowired
	private OpinionRepository opinionRepository;

	@Autowired
	private OpinionExtendMapper opinionDao;
	
	@Override
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<OpinionVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			total = opinionRepository.queryTotalByMap(con);
			page.setTotalRecords(total);
			if(total > 0){
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = getVoListByDomainList(opinionRepository.queryOpinionDoByMap(con));
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}
	

	@Override
	public boolean add(OpinionVo opinionVo) {
		opinionVo.setId(IdGenerator.getDefault().nextId()+"");
		opinionVo.setCreateTime(new Date());
		if(StringUtils.isNotEmpty(opinionVo.getClientId())){
			try{
				ClientVo client = null;
				client = opinionDao.findClientVoById(Long.valueOf(opinionVo.getClientId()));
				if(client != null){
					opinionVo.setClientName(client.getName());
					opinionVo.setClientPhone(client.getPhone());
				}
			}catch(Exception e){}
		}
		getDomainByVo(opinionVo).save();
		return true;
	}
	
	private List<OpinionVo> getVoListByDomainList(List<OpinionDo> srcs){
		List<OpinionVo> targets = new ArrayList<OpinionVo>();
		if(srcs != null && srcs.size() != 0){
			for (OpinionDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}
	
	/*private List<OpinionDo> getDomainListByVoList(List<OpinionVo> srcs){
		List<OpinionDo> targets = new ArrayList<OpinionDo>();
		if(srcs != null && srcs.size() != 0){
			for (OpinionVo src : srcs) {
				targets.add(getDomainByVo(src));
			}
		}
		return targets;
	}*/
	
	private OpinionVo getVoByDomain(OpinionDo src){
		OpinionVo target = null;
		if(src != null){
			target = new OpinionVo();
			target.setId(src.getId() == null ? null:src.getId()+"");
			target.setClientId(src.getClientId() == null ? null:src.getClientId()+"");
			target.setVersionName(src.getVersionName());
			target.setType(src.getType());
			target.setDescription(src.getDescription());
			target.setCreateTime(src.getCreateTime());
			target.setClientName(src.getClientName());	
			target.setClientPhone(src.getClientPhone());
			target.setPlatform(src.getPlatform());
			target.setPlatformText(PlatformType.getTextByDbData(src.getPlatform()));
		}
		return target;
	}

	private OpinionDo getDomainByVo(OpinionVo src){
		OpinionDo target = null;
		if(src != null){
			target = new OpinionDo();
			target.setId(src.getId() == null ? null:Long.valueOf(src.getId()));
			target.setClientId(StringUtils.isEmpty(src.getClientId()) ? null:Long.valueOf(src.getClientId()));
			target.setType(src.getType());
			target.setDescription(src.getDescription());
			target.setVersionName(src.getVersionName());
			target.setCreateTime(src.getCreateTime());	
			target.setClientName(src.getClientName());	
			target.setClientPhone(src.getClientPhone());
			target.setPlatform(src.getPlatform());
		}
		return target;
	}


	@Override
	public boolean deleteByIds(String idstr) {
		List<Long> ids = ListUtils.strToLongList(idstr);
		if(ListUtils.isNotEmpty(ids)){
			opinionDao.deleteByIds(ids);
		}
		return true;
	}


	@Override
	public boolean weixinAdd(OpinionVo opinionVo) {
		opinionVo.setId(IdGenerator.getDefault().nextId()+"");
		opinionVo.setCreateTime(new Date());
		if(StringUtils.isNotEmpty(opinionVo.getClientId())){
			try{
				ClientVo client = null;
				client = opinionDao.findWeixinClientVoById(Long.valueOf(opinionVo.getClientId()));
				if(client != null){
					opinionVo.setClientName(client.getName());
					opinionVo.setClientPhone(client.getPhone());
				}
			}catch(Exception e){}
		}
		getDomainByVo(opinionVo).save();
		return true;
	}	
}
