package com.club.web.module.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.ListUtils;
import com.club.web.module.dao.base.po.Version;
import com.club.web.module.dao.extend.VersionExtendMapper;
import com.club.web.module.domain.VersionDo;
import com.club.web.module.domain.VersionDo2;
import com.club.web.module.domain.repository.VersionRepository;

/**
 * 版本管理仓库接口实现类
 * @author zhuzd
 *
 */
@Repository
public class VersionRepositoryImpl implements VersionRepository{


	@Autowired
	private VersionExtendMapper versionExtendDao;
	
	/**
	 * 获取对应ios或者android最新版本
	 */
	public VersionDo2 getLastVersionDo(Integer platform) {
		return getDomainByPo(versionExtendDao.findLastVersion(platform));
	}
	
	@Override
	public int queryTotalByMap(Map<String, Object> con){
		return versionExtendDao.queryTotalByMap(con);
	}

	@Override
	public List<VersionDo> queryDoByMap(Map<String, Object> con) {
		return getDomainListByPoList(versionExtendDao.queryPoByMap(con));
	}
	
	@Override
	public void updateEffect(VersionDo versionDo) {
		versionExtendDao.updateByPrimaryKeySelective(getPoByDomain(versionDo));		
	}

	@Override
	public List<VersionDo> findDoListByIds(String ids) {
		return getDomainListByPoList(versionExtendDao.findListByIds(ListUtils.strToLongList(ids)));
	}

	@Override
	public List<VersionDo> findDoAllAble(Integer platform) {
		return getDomainListByPoList(versionExtendDao.findAllAble(platform));
	}
	
	@Override
	public void deleteById(Long id) {
		versionExtendDao.deleteByPrimaryKey(id);			
	}

	@Override
	public void add(VersionDo versionDo) {
		versionExtendDao.insert(getPoByDomain(versionDo));
	}
	
	@Override
	public void update(VersionDo versionDo) {
		versionExtendDao.updateByPrimaryKeySelective(getPoByDomain(versionDo));
	}

	@Override
	public VersionDo2 findDoById(Long id) {
		return getDomainByPo(versionExtendDao.selectByPrimaryKey(id));
	}

	@Override
	public VersionDo2 findDoByCode(String versionCode) {
		return getDomainByPo(versionExtendDao.findByCode(versionCode));
	}
	
//	private List<Version> getPoListByDomainList(List<VersionDo> srcs){
//		List<Version> targets = new ArrayList<Version>();
//		if(srcs != null && srcs.size() != 0){
//			for (VersionDo src : srcs) {
//				targets.add(getPoByDomain(src));
//			}
//		}
//		return targets;
//	}
	
	private List<VersionDo> getDomainListByPoList(List<Version> srcs){
		List<VersionDo> targets = new ArrayList<VersionDo>();
		if(srcs != null && srcs.size() != 0){
			for (Version src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}
	
	private Version getPoByDomain(VersionDo src){
		Version target = null;
		if(src != null){
			target = new Version();
			target.setId(src.getId());
			target.setName(src.getName());
			target.setCode(src.getCode());
			target.setDescription(src.getDescription());
			target.setModifier(src.getModifier());
			target.setCreater(src.getCreater());
			target.setUpdateTime(src.getUpdateTime());
			target.setUrl(src.getUrl());
			target.setPlatform(src.getPlatform());
			target.setStatus(src.getStatus());
			target.setEffect(src.getEffect());
			target.setDownloadWay(src.getDownloadWay());
		}
		return target;
	}

	private VersionDo2 getDomainByPo(Version src){
		VersionDo2 target = null;
		if(src != null){
			target = new VersionDo2();
			target.setId(src.getId());
			target.setName(src.getName());
			target.setCode(src.getCode());
			target.setDescription(src.getDescription());
			target.setModifier(src.getModifier());
			target.setCreater(src.getCreater());
			target.setUpdateTime(src.getUpdateTime());
			target.setUrl(src.getUrl());
			target.setPlatform(src.getPlatform());
			target.setStatus(src.getStatus());
			target.setEffect(src.getEffect());
			target.setDownloadWay(src.getDownloadWay());
		}
		return target;
	}

}
