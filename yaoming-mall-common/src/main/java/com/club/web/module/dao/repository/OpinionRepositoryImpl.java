package com.club.web.module.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.module.dao.base.po.Opinion;
import com.club.web.module.dao.extend.OpinionExtendMapper;
import com.club.web.module.domain.OpinionDo;
import com.club.web.module.domain.repository.OpinionRepository;
import com.club.web.module.vo.ClientVo;

/**
 * 意见反馈仓库接口实现类
 * @author zhuzd
 *
 */
@Repository
public class OpinionRepositoryImpl implements OpinionRepository{


	@Autowired
	private OpinionExtendMapper opinionDao;

	@Override
	public void add(OpinionDo opinionDo) {
		opinionDao.insert(getPoByDomain(opinionDo));
	}
	
	@Override
	public void modify(OpinionDo opinionDo) {
		opinionDao.updateByPrimaryKeySelective(getPoByDomain(opinionDo));
	}

	@Override
	public OpinionDo findDoById(Long id) {
		return getDomainByPo(opinionDao.selectByPrimaryKey(id));
	}


	@Override
	public ClientVo findClientVoById(Long clientId) {
		return opinionDao.findClientVoById(clientId);
	}

	@Override
	public int queryTotalByMap(Map<String, Object> con) {
		return opinionDao.queryTotalByMap(con);
	}

	@Override
	public List<OpinionDo> queryOpinionDoByMap(Map<String, Object> con) {
		return getDomainListByPoList(opinionDao.queryOpinionPoByMap(con));
	}
	
	private List<Opinion> getPoListByDomainList(List<OpinionDo> srcs){
		List<Opinion> targets = new ArrayList<Opinion>();
		if(srcs != null && srcs.size() != 0){
			for (OpinionDo src : srcs) {
				targets.add(getPoByDomain(src));
			}
		}
		return targets;
	}
	
	private List<OpinionDo> getDomainListByPoList(List<Opinion> srcs){
		List<OpinionDo> targets = new ArrayList<OpinionDo>();
		if(srcs != null && srcs.size() != 0){
			for (Opinion src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}
	
	private Opinion getPoByDomain(OpinionDo src){
		Opinion target = null;
		if(src != null){
			target = new Opinion();
			target.setId(src.getId());
			target.setClientId(src.getClientId());
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

	private OpinionDo getDomainByPo(Opinion src){
		OpinionDo target = null;
		if(src != null){
			target = new OpinionDo();
			target.setId(src.getId());
			target.setClientId(src.getClientId());
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
}
