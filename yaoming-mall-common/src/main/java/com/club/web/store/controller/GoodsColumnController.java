package com.club.web.store.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.stock.service.CargoService;
import com.club.web.stock.vo.CargoSkuSimpleVo;
import com.club.web.store.service.GoodsBaseLabelService;
import com.club.web.store.service.GoodsColumnService;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.RuleSourceVo;
import com.club.web.util.IdGenerator;

/**   
* @Title: GoodsColumnController.java
* @Package com.club.web.store.controller 
* @Description: 商品基础栏目接口类 
* @author hqLin 
* @date 2016/03/30
* @version V1.0   
*/

@Controller
@RequestMapping("/goodsColumnController")
public class GoodsColumnController {
	
	@Autowired
	private GoodsColumnService goodsColumnService;
	
	@Autowired
	private GoodsBaseLabelService goodsBaseLabelServiceImpl;
	
	@Autowired 
	private CargoService cargoService;
	
	/**
     * 新增/修改标签
     * @param modelJson 规格对象值字符串
     */
	@RequestMapping("/addOrUpdGoodsColumn")
	@ResponseBody
	public Map<String, Object> addGoodsColumn(String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		GoodsColumnVo goodsColumnVo = JsonUtil.toBean(modelJson, GoodsColumnVo.class);
		if(goodsColumnVo != null){
			//以ID来判断是新增或者修改操作
			if(goodsColumnVo.getId() != null && !goodsColumnVo.getId().isEmpty()){
				result.put("success", true);
				result.put("msg", "编辑成功");
				goodsColumnService.editGoodsColumn(goodsColumnVo);
			} else{
				result.put("success", true);
				result.put("msg", "新增成功");
				goodsColumnVo.setId(IdGenerator.getDefault().nextId() + "");
				goodsColumnService.addGoodsColumn(goodsColumnVo, request);				
			}
		} else{
			result.put("success", false);
			result.put("msg", "新增失败");
		}
				
		return result;
	}
	
	/**
	 * 根据栏目名称查询栏目信息
	 * @param page 分页信息
	 * @param columnName 栏目名称
	 * @param shopFlag 总店标识
	 * @return
	 */
	@RequestMapping("/selectGoodsColumn")
	@ResponseBody
	public Page<Map<String, Object>> selectGoodsColumnByColumnName(Page<Map<String, Object>> page, 
			String columnName, String shopFlag, HttpServletRequest request) {
		page = goodsColumnService.selectGoodsColumnByColumnName(page, columnName, shopFlag, request);	
		
		return page;
	}
	    
    /**
     * 删除栏目
     * @param idStr 标签ID集合
     * @return
     */
	@RequestMapping("/deleteGoodsColumn")
	@ResponseBody
    public Map<String, Object> deleteGoodsColumn(String idStr) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			try {
				result.put("success", true);
				result.putAll(goodsColumnService.deleteGoodsColumn(idStr));				
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				result.put("success", false);
				result.put("msg", "栏目被引用，无法删除");
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		} else{
			result.put("success", false);
			result.put("msg", "标签ID不能为空");
		}
			
		return result;
	}
	
	/**
	 * 修改启动/禁用状态
	 * @param idStr
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatusForGoodsColumnById")
	@ResponseBody
    public Map<String, Object> changeStatus(String idStr, String status) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			result.put("success", true);
			result.putAll(goodsColumnService.updateStatusForGoodsColumnById(idStr, status));
		} else{
			result.put("success", false);
			result.put("msg", "标签ID不能为空");
		}
			
		return result;
	}
	
	@RequestMapping("/selectGoodsColumnAndLabelListByShopId")
	@ResponseBody
	public Map<String,Object> selectGoodsColumnListByShopId(HttpServletRequest request,String cargoId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<GoodsColumnVo> columnList= goodsColumnService.selectGoodsColumnListByShopId(request);
		List<GoodsBaseLabelVo> labelList=goodsBaseLabelServiceImpl.selectGoodsBaseLabelListByLabelName(null, 
				"", null, request);
		List<CargoSkuSimpleVo> cargoSkuList=cargoService.getSkuList(Long.parseLong(cargoId));
        map.put("columns", columnList);
        map.put("labels", labelList);
        map.put("cargoSkuList", cargoSkuList);
		return map;
	}
	
	@RequestMapping("/selectRuleSourceList")
	@ResponseBody
	public List<RuleSourceVo> selectRuleSourceList(int ruleType,HttpServletRequest request) {
		
		return goodsColumnService.selectRuleSourceList(ruleType);
	}
	
	@RequestMapping("/mobile/getGoodsAndCloumn")
	@ResponseBody
	public List<Map<String, Object>> getGoodsAndCloumn(Long shopId, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = goodsColumnService.getGoodsAndCloumn(shopId);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/mobile/selectGoodsByColumnId")
	@ResponseBody
	public Map<String, Object> selectGoodsByColumnId(Long columnId,int start,
			@RequestParam(name="limit",defaultValue="6")int limit, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = goodsColumnService.selectGoodsByColumnId(columnId,start,limit);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/mobile/selectColumnsGood")
	@ResponseBody
	public List<Map<String, Object>> selectColumnsGood(HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = goodsColumnService.selectColumnsGood();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}