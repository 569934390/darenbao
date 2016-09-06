package com.club.web.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.store.service.GoodsBaseLabelService;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.util.IdGenerator;

/**   
* @Title: GoodsBaseLabelController.java
* @Package com.club.web.store.controller 
* @Description: 商品基础标签接口类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
@Controller
@RequestMapping("/goodsBaseLabelController")
public class GoodsBaseLabelController {
	
	@Autowired
	private GoodsBaseLabelService goodsBaseLabelService;
	
	/**
     * 新增/修改标签
     * @param modelJson 规格对象值字符串
     */
	@RequestMapping("/addOrUpdGoodsBaseLabel")
	@ResponseBody
	public Map<String, Object> addGoodsBaseLabel(String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		GoodsBaseLabelVo goodsBaseLabelVo = JsonUtil.toBean(modelJson, GoodsBaseLabelVo.class);
		if(goodsBaseLabelVo != null){
			//以ID来判断是新增或者修改操作
			if(goodsBaseLabelVo.getId() != null && !goodsBaseLabelVo.getId().isEmpty()){
				try {
					result.putAll(goodsBaseLabelService.editGoodsBaseLabel(goodsBaseLabelVo));					
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", e.getMessage());
				}
			} else{
				try {
					result.put("success", true);
					result.put("msg", "新增商品标签成功");
					goodsBaseLabelVo.setId(IdGenerator.getDefault().nextId() + "");
					goodsBaseLabelVo.setStatus(1);
					goodsBaseLabelService.addGoodsBaseLabel(goodsBaseLabelVo, request);					
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", e.getMessage());
				}
			}
		} else{
			result.put("success", false);
			result.put("msg", "新增失败");
		}
		
		return result;
	}
	
	/**
	 * 根据标签名称查询标签信息
	 * @param page 分页信息
	 * @param labelName 标签名称
	 * @return
	 */
	@RequestMapping("/selectGoodsBaseLabel")
	@ResponseBody
	public Page<Map<String, Object>> selectGoodsBaseLabelByLabelName(Page<Map<String, Object>> page, 
			String labelName, String shopFlag, HttpServletRequest request) {
		page = goodsBaseLabelService.selectGoodsBaseLabelByLabelName(page, labelName, shopFlag, request);	
		
		return page;
	}
	    
    /**
     * 删除标签
     * @param idStr 标签ID集合
     * @return
     */
	@RequestMapping("/deleteGoodsBaseLabel")
	@ResponseBody
    public Map<String, Object> deleteGoodsBaseLabel(String idStr, String shopFlag) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			try {
				result.put("success", true);
				result.putAll(goodsBaseLabelService.deleteGoodsBaseLabel(idStr, shopFlag));
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				result.put("success", false);
				result.put("msg", "标签被引用，无法删除");
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
	@RequestMapping("/changeStatus")
	@ResponseBody
    public Map<String, Object> changeStatus(String idStr, String status) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			try {				
				result.put("success", true);
				result.put("msg", "修改状态成功");
				goodsBaseLabelService.changeStatus(idStr, status);
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

	@RequestMapping("/selectGoodsBaseLabelListByLabelName")
	@ResponseBody
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> page, 
			String labelName, String shopFlag, HttpServletRequest request) {
		
		return goodsBaseLabelService.selectGoodsBaseLabelListByLabelName(page, labelName, shopFlag, request);
	}
}