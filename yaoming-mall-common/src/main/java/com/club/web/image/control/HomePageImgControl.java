package com.club.web.image.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.image.service.HomePageImgService;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.HomePageImgVo;
import com.club.web.image.service.vo.ImageVo;

@Controller
@RequestMapping("HomePageImg")
public class HomePageImgControl {
	@Autowired
	HomePageImgService homePageImgService;

	@RequestMapping("saveHomePageImg")
	@ResponseBody
	public Map<String, Object> saveOrUpdateImg(HttpServletRequest request, String modelJson,String imgUrls) {
		HomePageImgVo homePageImgVo = JsonUtil.toBean(modelJson, HomePageImgVo.class);
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		Map<String, Object> result = new HashMap<String, Object>();
		long staffId=1L;
		if (loginMap != null && loginMap.get("staffId") != null) {
			 staffId = Long.parseLong(loginMap.get("staffId").toString());
		} else {
			return null;
		}
		
		try {
			homePageImgService.saveOrUpdateImg(1L,staffId,imgUrls);
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	@RequestMapping("getHomePageImg")
	@ResponseBody
	public Page getDefaultImg(@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "start", required = true) int start){
		Page page=new Page();
		page.setLimit(limit);
		page.setStart(start);
		page=homePageImgService.getimgList(page);
		
		return page;
		
	}
	@RequestMapping("deletHomePageImg")
	@ResponseBody
	public Map<String, Object> deletDefaultImg(HttpServletRequest request,@RequestParam(value = "imgIds", required = true) String imgIds){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		
		long staffId=1L;
		if (loginMap != null && loginMap.get("staffId") != null) {
			 staffId = Long.parseLong(loginMap.get("staffId").toString());
		} else {
			return null;
		}
		try {
			homePageImgService.deletHomePageImg(imgIds);
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
		
	}
}
