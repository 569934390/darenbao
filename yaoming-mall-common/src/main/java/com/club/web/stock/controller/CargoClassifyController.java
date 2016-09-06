package com.club.web.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.web.common.Constants;
import com.club.web.stock.constant.CargoClassifyStatus;
import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.service.CargoClassifyService;
import com.club.web.stock.vo.CargoClassifyAppVo;
import com.club.web.stock.vo.CargoClassifyVo;

/**
 * 货品分类Controller
 * @author zhuzd
 */
@Controller
@RequestMapping("/cargo/classify")
public class CargoClassifyController {
	
	private ClubLogManager logger = ClubLogManager.getLogger(this.getClass());
	
	@Autowired
	private CargoClassifyService cargoClassifyService;
			
	@RequestMapping("/list")
	@ResponseBody
	public Page<CargoClassifyVo> list() throws BaseAppException {
		logger.debug("cargoclassify list ");
		Page<CargoClassifyVo> page = new Page<CargoClassifyVo>(0,Integer.MAX_VALUE);
		try{
			page = cargoClassifyService.list();
		}catch(Exception e){
			logger.error("cargoclassify list error:",e);
		}
		return page;
	}
	
	@RequestMapping("/detail")
	@ResponseBody
	public CargoClassifyVo detail(String id,Integer status) throws BaseAppException {
		logger.debug("cargoclassify detail ");
		CargoClassifyVo vo = new CargoClassifyVo();
		try{
			vo = cargoClassifyService.findVoByIdAndStatus(Long.valueOf(id),status);
		}catch(Exception e){
			logger.error("cargoclassify detail error:",e);
		}
		return vo;
	}
	
	/**
	 * 禁用,启用操作
	 * @param ids
	 * @param action
	 * @param request
	 * @return
	 */
	@RequestMapping("/{action}")
	@ResponseBody
	public Map<String,Object> action(String ids,@PathVariable String action,HttpServletRequest request){
		logger.debug("cargoclassify update status '"+action+"' ");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			long userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;
			if(!cargoClassifyService.updateStatus(ids,CargoClassifyStatus.getDbDataByName(action),userId)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("cargoclassify update status '"+action+"' error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> deleteByIds(String ids){
		logger.debug("delete cargoclassify ");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			cargoClassifyService.deleteByIds(ids);
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("cargoclassify delete error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(String modelJson,HttpServletRequest request){
		logger.debug("add cargoclassify ");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			CargoClassifyVo cargoClassifyVo = JsonUtil.toBean(modelJson, CargoClassifyVo.class);
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			long userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;		
			if(!cargoClassifyService.add(userId,cargoClassifyVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("cargoclassify add error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/modify")
	@ResponseBody
	public Map<String,Object> modify(String modelJson,HttpServletRequest request){
		logger.debug("update cargoclassify ");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			CargoClassifyVo cargoClassifyVo = JsonUtil.toBean(modelJson, CargoClassifyVo.class);
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			long updaterId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;	
			if(!cargoClassifyService.modify(updaterId,cargoClassifyVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("cargoclassify modify error:",e);
		}
		return result;
	}	
	
	/**
	 * 获取次一级列表
	 * @param parentId
	 * @param status
	 * @return
	 */
	@RequestMapping("/queryByParentId")
	@ResponseBody
	public List<CargoClassifyVo> queryByParentId(String parentId,Integer status){
		logger.debug("queryByParentId cargoclassify ");
		try {
			return cargoClassifyService.getVoListByParentId(Long.valueOf(parentId),status);
		} catch (Exception e) {
			logger.error("cargoclassify queryByParentId error:",e);
		}
		return null;
	}
	
	/**
	 * 获取第一级列表
	 * @param status
	 * @return
	 */
	@RequestMapping("/queryParents")
	@ResponseBody
	public List<CargoClassifyVo> queryParents(Integer status){
		logger.debug("queryParents cargoclassify ");
		try {
			return cargoClassifyService.getVoListByParentId(ClassifyConstant.CARGO_CLASSIFY,status);
		} catch (Exception e) {
			logger.error("cargoclassify queryParents error:",e);
		}
		return null;
	}
	
	/**
	 * 获取手机端第一级列表
	 * @param status
	 * @return
	 */
	@RequestMapping("/mobile/parents")
	@ResponseBody
	public Object appParents(String parentId,HttpServletResponse response){
		logger.debug("queryParents appParents ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return cargoClassifyService.getAppParents(parentId);
		} catch (Exception e) {
			logger.error("cargoclassify appParents error:",e);
		}
		return null;
	}
	
	/**
	 * 获取所有货品分类
	 * @param status
	 * @return
	 */
	@RequestMapping("/mobile/firstAndSecondList")
	@ResponseBody
	public List<CargoClassifyAppVo> mobileFirstAndSecondList(HttpServletResponse response){
		logger.debug("allList mobileFirstAndSecondList ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return cargoClassifyService.mobileFirstAndSecondList();
		} catch (Exception e) {
			logger.error("cargoclassify mobileFirstAndSecondList error:",e);
		}
		return null;
	}
	
	/**
	 * 获取所有货品分类
	 * @param status
	 * @return
	 */
	@RequestMapping("/allList")
	@ResponseBody
	public List<CargoClassifyVo> allList(Integer status){
		logger.debug("allList cargoclassify ");
		try {
			return cargoClassifyService.getVoAllList(status);
		} catch (Exception e) {
			logger.error("cargoclassify allList error:",e);
		}
		return null;
	}
}
