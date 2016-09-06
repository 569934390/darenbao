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
import com.club.framework.util.StringUtils;
import com.club.web.spread.service.SpreadManagerService;
import com.club.web.spread.service.impl.SpreadManagerServiceImpl;
import com.club.web.spread.vo.GoodandCargoSimpleInfoVo;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.spread.vo.MarketSpreadManagerVo;
import com.club.web.spread.vo.SpreadVo;
import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.vo.CargoSimpleInfoVo;
import com.club.web.store.controller.GoodController;
import com.club.web.store.vo.GoodListVo;
import com.club.web.util.IdGenerator;

@Controller
@RequestMapping("/spreadManager")
public class SpreadManagerController {
	
   private Logger logger = LoggerFactory.getLogger(SpreadManagerController.class);
   @Autowired
   SpreadManagerService spreadManagerService;
   
	/**
	 * 查询所有的营销推广信息
	 * @param page 分页信息
	 * @param conditionStr 条件名称
	 * @return
	 */
	@RequestMapping("/spreadPage")
	@ResponseBody
	public Page<Map<String, Object>> selectAllSpread(Page<Map<String, Object>> page,String conditionStr,
			HttpServletRequest request) {
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = spreadManagerService.selectSpread(page, request)	;
		return page;
	}
	
	/**
	 * 分页查询商品和货品信息
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Page<GoodandCargoSimpleInfoVo> getList(Page<GoodandCargoSimpleInfoVo> page, String conditionStr) {
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null)
			page.setConditons(JsonUtil.toMap(conditionStr));
		// 根据查询分页信息和查询查询分页结果
		return spreadManagerService.queryGoodandCargoList(page);

	}
	
	/**
     * 新增/编辑营销推广信息
     * @param modelJson 推广信息对象值字符串
     */
	@RequestMapping("/addOrUpdateSpreadInfo")
	@ResponseBody
	public Map<String, Object> addOrUpdateSpread(String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		SpreadVo spreadVo = JsonUtil.toBean(modelJson, SpreadVo.class);
		if(spreadVo != null){
			//以ID来判断是新增或者修改操作
			if(spreadVo.getId() != null && !spreadVo.getId().isEmpty() && !spreadVo.getId().equals("")){
                spreadManagerService.updateSpread(spreadVo);;
				result.put("success", true);
				result.put("msg", "编辑成功");
			} else{
				try {
					spreadVo.setId(IdGenerator.getDefault().nextId() + "");
					spreadManagerService.addSpread(spreadVo);
					result.put("success", true);
					result.put("msg", "新增成功");
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "新增失败");
					logger.error("新增推广失败-controller",e.getMessage());
				}
			}
		} else{
			result.put("success", false);
			result.put("msg", "推广为空，编辑失败");
		}
		
		return result;
	}
	
	
	 /**
     * 批量删除推广
     * @param idStr 推广分类的id数组
     * @return
     */
	@RequestMapping("/deleteSpread")
	@ResponseBody
    public Map<String, Object> deleteSpread(String IdStr) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(IdStr != null){
			try {
				spreadManagerService.deleteSpread(IdStr);;
				result.put("success", true);
			} 
			 catch (Exception e) {
				result.put("success", false);
				result.put("msg", "删除推广失败");
			}
		} else{
			result.put("success", false);
			result.put("msg", "要删除的推广ID不能为空");
		}
			
		return result;
	}
	
	
	@RequestMapping("/getSpreadList")
	@ResponseBody
	public List<SpreadVo> getSpreadList(String conditionStr,Integer start,Integer limit, HttpServletResponse response) {
		logger.debug(" spreadManagerController getSpreadList");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<SpreadVo> list = null;
		try {
			Map<String, Object> map = StringUtils.isNotEmpty(conditionStr)?JsonUtil.toMap(conditionStr):new HashMap<>();
			if(!map.isEmpty()){
				if(map.containsKey("classifyId")){
					map.put("classifyId", StringUtils.isEmpty(map.get("classifyId").toString())?"":(String) map.get("classifyId"));
				}if(map.containsKey("updateTime")){
					map.put("updateTime", StringUtils.isEmpty(map.get("updateTime").toString())?"":(String) map.get("updateTime"));
				}if(map.containsKey("readNum")){
					map.put("readNum", StringUtils.isEmpty(map.get("readNum").toString())?"":(String) map.get("readNum"));
				}
			}
			limit = limit == null?6:limit;
			start = start != null&&limit != null?start*limit:0;
			map.put("limit", limit);
			map.put("start", start);
			list = spreadManagerService.querySpreadList(map);
		} catch (Exception e) {
			logger.error("getSpreadList  error:", e);
		}
		return list;
	}
	
}
