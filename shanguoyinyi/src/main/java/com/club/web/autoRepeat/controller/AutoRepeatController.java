package com.club.web.autoRepeat.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.autoRepeat.service.AutoRepeatService;
import com.club.web.autoRepeat.vo.AutorepeatVo;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;
import com.club.web.util.IdGenerator;

@Controller
@RequestMapping("/autoRepeat")
public class AutoRepeatController {
	
	private Logger logger = LoggerFactory.getLogger(AutoRepeatController.class);

	@Autowired
    private AutoRepeatService autoRepeatService;

	/**
	 * 根据名称分页查询自动回复所有记录
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/autoRepeatPage")
	@ResponseBody
	public Map<String, Object> autoRepeatPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

//		// 根据查询分页信息和查询查询分页结果

		Map<String, Object> result = new HashMap<String, Object>();
		Page<Map<String, Object>> pagecontitons=page;
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			pagecontitons.setConditons(JsonUtil.toMap(conditionStr));
		}
        try{
        	// 根据查询分页信息和查询查询分页结果
    		page = autoRepeatService.selectAutoRepeat(pagecontitons);
    		result.put("page", page);
    		result.put("success", true);
        }catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询所有自动回复失败");
		}	
		return result;
	}
	
	
	/**
	 * 添加或者编辑自动回复
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/addorEditAutoRepeat")
	@ResponseBody
	public Map<String, Object> addorEditAutoRepeat(String modelJson,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		AutorepeatVo autorepeatVo = JsonUtil.toBean(modelJson, AutorepeatVo.class);
		if(autorepeatVo != null){
			//以ID来判断是新增或者修改操作
			if(autorepeatVo.getId() != null && !autorepeatVo.getId().isEmpty() && !autorepeatVo.getId().equals("")){
				try{
					autoRepeatService.editAutoRepeat(autorepeatVo);
					result.put("success", true);
					result.put("msg", "编辑成功");
				}catch (Exception e) {
					result.put("success", false);
					result.put("msg", "编辑失败");
					logger.error("编辑自动回复失败-controller",e.getMessage());
				}
				
			} else{
				try {
					autorepeatVo.setId(IdGenerator.getDefault().nextId() + "");
					autoRepeatService.addAutoRepeat(autorepeatVo);
					result.put("success", true);
					result.put("msg", "新增成功");
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "新增失败");
					logger.error("新增自动回复失败-controller",e.getMessage());
				}
			}
		} else{
			result.put("success", false);
			result.put("msg", "对象为空");
		}
		
		return result;
	}
	
	
	 /**
     * 批量删除推广
     * @param idStr 推广分类的id数组
     * @return
     */
	@RequestMapping("/deleteAutoRepeat")
	@ResponseBody
    public Map<String, Object> deleteSpread(String IdStr) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(IdStr != null){
			try {
				autoRepeatService.deleteAutoRepeat(IdStr);
				result.put("success", true);
			} 
			 catch (Exception e) {
				result.put("success", false);
				result.put("msg", "删除失败");
			}
		} else{
			result.put("success", false);
			result.put("msg", "要删除的记录ID不能为空");
		}
			
		return result;
	}
	
	
	/**
	 * 根据名称分页查询自动回复所有记录
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/defaultRepeatPage")
	@ResponseBody
	public Map<String, Object> defaultRepeatPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

//		// 根据查询分页信息和查询查询分页结果

		Map<String, Object> result = new HashMap<String, Object>();
		Page<Map<String, Object>> pagecontitons=page;
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			pagecontitons.setConditons(JsonUtil.toMap(conditionStr));
		}
        try{
        	// 根据查询分页信息和查询查询分页结果
    		page = autoRepeatService.selectDefaultRepeat(pagecontitons);
    		result.put("page", page);
    		result.put("success", true);
        }catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询所有自动回复失败");
		}	
		return result;
	}
	
	
	/**
	 * 添加或者编辑默认回复消息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/editDefaultRepeat")
	@ResponseBody
	public Map<String, Object> editDefaultRepeat(String modelJson,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		DefaultrepeatVo defaultrepeatVo = JsonUtil.toBean(modelJson, DefaultrepeatVo.class);
		if(defaultrepeatVo != null){
			//以ID来判断是新增或者修改操作
			if(defaultrepeatVo.getId() != null && !defaultrepeatVo.getId().isEmpty() && !defaultrepeatVo.getId().equals("")){
				try{
					autoRepeatService.editDefaultRepeat(defaultrepeatVo);
					result.put("success", true);
					result.put("msg", "编辑成功");
				}catch (Exception e) {
					result.put("success", false);
					result.put("msg", "编辑失败");
					logger.error("编辑自动回复失败-controller",e.getMessage());
				}
				
			} 
		} else{
			result.put("success", false);
			result.put("msg", "对象为空");
		}
		
		return result;
	}

}
