package com.club.web.spread.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.spread.service.SpreadClassifyService;
import com.club.web.spread.service.impl.SpreadClassifyServiceImpl;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.util.IdGenerator;

/**
 * @Description: 营销推广分类controller
 * @author czj
 *
 */
@Controller
@RequestMapping("/spreadClassify")
public class SpreadClassifyController {
    @Autowired
   private SpreadClassifyService spreadClassifyService;
    private Logger logger = LoggerFactory.getLogger(SpreadClassifyController.class);
	/**
	 * 查询所有的营销推广分类信息
	 * @param page 分页信息
	 * @return
	 */
	@RequestMapping("/selectAllSpreadClassify")
	@ResponseBody
	public Page<Map<String, Object>> selectAllSpreadClassify(Page<Map<String, Object>> page, 
			HttpServletRequest request) {
		page = spreadClassifyService.selectSpreadClassify(page, request);	
		return page;
	}
	
	/**
	 * 查询所有的营销推广分类
	 * @param 
	 * @return 所有分类的list
	 */
	@RequestMapping("/findAllSpreadClassify")
	@ResponseBody
	public List<MarketSpreadClassifyVo> findAllSpreadClassify(
			HttpServletRequest request) {
		try{
			return spreadClassifyService.findAllClassify();	
		}catch (Exception e) {
			logger.error("查询推广分类数量异常:", e);
		}
		return null;
	}
	
	
	
	/**
     * 新增/编辑营销推广分类信息
     * @param modelJson 分类对象值字符串
     */
	@RequestMapping("/addOrUpdateSpreadClassify")
	@ResponseBody
	public Map<String, Object> addOrUpdateSpreadClassify(String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		MarketSpreadClassifyVo marketSpreadClassifyVo = JsonUtil.toBean(modelJson, MarketSpreadClassifyVo.class);
		if(marketSpreadClassifyVo != null){
			//以ID来判断是新增或者修改操作
			if(marketSpreadClassifyVo.getId() != null && !marketSpreadClassifyVo.getId().isEmpty()){
			    result.putAll(spreadClassifyService.editSpreadClassify(marketSpreadClassifyVo));
				result.put("success", true);
				result.put("msg", "编辑成功");
			} else{
				marketSpreadClassifyVo.setId(IdGenerator.getDefault().nextId() + "");
				spreadClassifyService.addSpreadClassify(marketSpreadClassifyVo, request);	
				result.put("success", true);
				result.put("msg", "新增成功");
			}
		} else{
			result.put("success", false);
			result.put("msg", "新增失败");
		}
		
		return result;
	}
	
	
    /**
     * 批量删除推广分类
     * @param idStr 推广分类的id数组
     * @return
     */
	@RequestMapping("/deleteSpreadClassify")
	@ResponseBody
    public Map<String, Object> deleteSpreadClassify(String idStr) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			try {
				spreadClassifyService.deleteSpreadClassify(idStr);
				result.put("success", true);
			} 
			 catch (Exception e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		} else{
			result.put("success", false);
			result.put("msg", "推广分类的ID不能为空");
		}
			
		return result;
	}
	
	/**
	 * 批量修改启动/禁用状态
	 * @param idStr
	 * @param status
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
    public Map<String, Object> changeStatus(String idStr, int status) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){			
			result.putAll(spreadClassifyService.changeStatus(idStr, status));
			result.put("success", true);
		} else{
			result.put("success", false);
			result.put("msg", "推广分类ID不能为空");
		}
			
		return result;
	}
	
	/**
	 * 查询所有启用的推广分类
	 * @param 
	 * @return 所有分类的list
	 */
	@RequestMapping("/findAllSpreadClassifyOnStatus")
	@ResponseBody
	public List<MarketSpreadClassifyVo> findAllSpreadClassifyOnStatus(
			HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try{
			return spreadClassifyService.findAllClassifyOnStatus();	
		}catch (Exception e) {
			logger.error("查询所有启用的推广分类数量异常:", e);
		}
		return null;
	}
	
}
