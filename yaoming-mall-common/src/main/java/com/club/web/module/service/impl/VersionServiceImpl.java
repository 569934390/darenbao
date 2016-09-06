package com.club.web.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.module.constant.VersionEffect;
import com.club.web.module.constant.PlatformType;
import com.club.web.module.constant.VersionStatus;
import com.club.web.module.domain.VersionDo;
import com.club.web.module.domain.VersionDo2;
import com.club.web.module.domain.repository.VersionRepository;
import com.club.web.module.service.VersionService;
import com.club.web.module.vo.VersionVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
/**
 * 版本管理服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class VersionServiceImpl implements VersionService {
	
	@Autowired
	private VersionRepository versionRepository;

	/**
	 * 获取最新版本
	 */
	@Override
	public VersionVo getLastVersionVo(Integer platform) {
		return getVoByDomain(versionRepository.getLastVersionDo(platform));
	}

	/**
	 * version列表
	 */
	@Override
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<VersionVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			con.put("startIndex", startIndex);
			con.put("pageSize", pageSize);			
			total = versionRepository.queryTotalByMap(con);
			page.setTotalRecords(total);
			if(total > 0){
				list = getVoListByDomainList(versionRepository.queryDoByMap(con));
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
		
	}

	/**
	 * 更新生效，失效状态
	 */
	@Override
	public String updateEffect(String ids, String effect, String username) {
		if(StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(effect)){
			Date updateTime = new Date();
			if(VersionEffect.失效.getName().equals(effect)){
				List<VersionDo> doList = versionRepository.findDoListByIds(ids);
				for (VersionDo  versionDo: doList) {
					versionDo.updateEffect(effect,username,updateTime);
				}
			}else{
				VersionDo2 versionDo = versionRepository.findDoById(Long.valueOf(ids));
				if(versionDo != null){
					if(!VersionEffect.生效.equals(versionDo.getEffect())){
						if(VersionStatus.企业版.getDbData() == versionDo.getStatus() && StringUtils.isEmpty(versionDo.getUrl())){
							if(PlatformType.安卓.getDbData() == versionDo.getPlatform()){
								return "企业版的'"+versionDo.getName()+"'版本生效失败：安卓下载链接为空！请填写数据！";
							}if(PlatformType.苹果.getDbData() == versionDo.getPlatform()){
								return "企业版的'"+versionDo.getName()+"'版本生效失败：苹果下载链接为空！请填写数据！";
							}
						}if(VersionStatus.正式版.getDbData() == versionDo.getStatus() && PlatformType.苹果.getDbData() == versionDo.getPlatform() && StringUtils.isEmpty(versionDo.getUrl())){
							return "正式版的'"+versionDo.getName()+"'版本生效失败：苹果下载链接为空！请填写数据！";
						}
						List<VersionDo> doAbleList = versionRepository.findDoAllAble(versionDo.getPlatform());
						for (VersionDo doAble : doAbleList) {
							doAble.updateEffect(VersionEffect.失效.getName(),null,null);
						}
						versionDo.updateEffect(effect,username,updateTime);
					}
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> deleteByIds(String ids) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("msg", "操作成功！");
		boolean flag = true;
		List<VersionDo> versionList = versionRepository.findDoListByIds(ids);
		for (VersionDo versionDo : versionList) {
			if(VersionEffect.生效.getDbData() == versionDo.getEffect()){
				flag = false;
				result.put("code", 0);
				result.put("msg", "存在已生效版本，请先将版本置为失效后再进行删除！");
				break;
			}
		}
		if(flag){
			for (VersionDo versionDo : versionList) {
				versionDo.delete();
			}
		}
		return result;
	}

	@Override
	public boolean add(String username, VersionVo versionVo) {
		versionVo.setId(IdGenerator.getDefault().nextId()+"");
		versionVo.setCreater(username);
		versionVo.setModifier(username);
		versionVo.setUpdateTime(new Date());
		versionVo.setEffect(VersionEffect.失效.getDbData());
		getDomainByVo(versionVo).save();
		return true;
	}

	@Override
	public boolean modify(String username, VersionVo versionVo) {
		VersionDo2 target = versionRepository.findDoById(Long.valueOf(versionVo.getId()));
		target.setName(versionVo.getName()!=null?versionVo.getName():"");
		target.setCode(versionVo.getCode());
		target.setDescription(versionVo.getDescription()!= null?versionVo.getDescription():"");
		target.setStatus(versionVo.getStatus()!=null?versionVo.getStatus():null);
		target.setUpdateTime(new Date());
		target.setUrl(versionVo.getUrl());
		target.setPlatform(versionVo.getPlatform());
		target.setDownloadWay(versionVo.getDownloadWay());
		target.setModifier(username);
		target.update();
		return true;
	}
	
	private List<VersionVo> getVoListByDomainList(List<VersionDo> srcs){
		List<VersionVo> targets = new ArrayList<VersionVo>();
		if(srcs != null && srcs.size() != 0){
			for (VersionDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}
	
	/*private List<VersionDo> getDomainListByVoList(List<VersionVo> srcs){
		List<VersionDo> targets = new ArrayList<VersionDo>();
		if(srcs != null && srcs.size() != 0){
			for (VersionVo src : srcs) {
				targets.add(getDomainByVo(src));
			}
		}
		return targets;
	}*/
	
	public VersionVo getVoByDomain(VersionDo src){
		VersionVo target = null;
		if(src != null){
			target = new VersionVo();
			target.setId(src.getId() == null ? null:src.getId()+"");
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

	private VersionDo2 getDomainByVo(VersionVo src){
		VersionDo2 target = null;
		if(src != null){
			target = new VersionDo2();
			target.setId(src.getId() == null ? null:Long.valueOf(src.getId()));
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
