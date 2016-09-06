package com.club.web.module.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.module.constant.PlatformType;
import com.club.web.module.service.VersionService;
import com.club.web.module.vo.VersionVo;
/**
 * 版本管理控制类
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/module/version")
public class VersionController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VersionService versionService;
	
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("version list ");
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = versionService.list(page);
		} catch (Exception e) {
			logger.error("version list error:", e);
		}
		return page;
	}
	
	/**
	 * 检查是否是最新版本,不是最新则返回最新的url下载列表
	 * @return
	 */
	@RequestMapping("/mobile/check")
	@ResponseBody
	public Map<String, Object> checkLastVersion(String type,String name,String code,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		try{
			VersionVo versionVo = versionService.getLastVersionVo(PlatformType.getDbDataByName(type));
			if(versionVo != null && versionVo.getCode() != null && code != null && versionVo.getCode() > Long.valueOf(code)){
				map.put("code", 1);
				map.put("msg", versionVo.getName());
				map.put("versionType", versionVo.getStatus());
				map.put("downloadWay", versionVo.getDownloadWay());
				map.put("url",versionVo.getUrl());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("/{action}")
	@ResponseBody
	public Map<String, Object> updateEffect(@PathVariable String action,String ids,HttpServletRequest request){
		logger.debug("version updateEffect ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", "操作成功！");
		try{
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			String username = loginMap.get("staffName") != null ? (String)loginMap.get("staffName") : "";
			String msg = versionService.updateEffect(ids,action,username);
			if(msg != null){
				map.put("code",0);
				map.put("msg",msg);
			}
		}catch(Exception e){
			map.put("code", 0);
			map.put("msg", "操作异常");
			logger.error("version updateEffect error:",e);
		}
		return map;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids){
		logger.debug("version delete ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", "操作成功！");
		try{
			map = versionService.deleteByIds(ids);
		}catch(Exception e){
			map.put("code", 0);
			map.put("msg", "操作失败");
			logger.error("version delete error:",e);
		}
		return map;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(String modelJson,HttpServletRequest request){
		logger.debug("version add");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			VersionVo versionVo = JsonUtil.toBean(modelJson, VersionVo.class);
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			String username = loginMap.get("staffName") != null ? (String)loginMap.get("staffName") : "";
			if(!versionService.add(username,versionVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("version add error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/modify")
	@ResponseBody
	public Map<String,Object> modify(String modelJson,HttpServletRequest request){
		logger.debug("version modify");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			VersionVo versionVo = JsonUtil.toBean(modelJson, VersionVo.class);
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			String username = loginMap.get("staffName") != null ? (String)loginMap.get("staffName") : "";
			if(!versionService.modify(username,versionVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("version modify error:",e);
		}
		return result;
	}	
}
