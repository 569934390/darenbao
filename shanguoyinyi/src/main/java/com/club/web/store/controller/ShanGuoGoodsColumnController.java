package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.store.service.ShanGuoGoodsColumnService;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.util.IdGenerator;

/**   
* @Title: ShanGuoGoodsColumnController.java
* @Package com.club.web.store.controller 
* @Description: 山国商品基础栏目接口类 
* @author hqLin 
* @date 2016/05/16
* @version V1.0   
*/

@Controller
@RequestMapping("/shanGuoGoodsColumnController")
public class ShanGuoGoodsColumnController {
	
	@Autowired
	private ShanGuoGoodsColumnService shanGuoGoodsColumnService;
	
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
				shanGuoGoodsColumnService.editGoodsColumn(goodsColumnVo);
			} else{
				result.put("success", true);
				result.put("msg", "新增成功");
				goodsColumnVo.setId(IdGenerator.getDefault().nextId() + "");
				shanGuoGoodsColumnService.addGoodsColumn(goodsColumnVo, request);				
			}
		} else{
			result.put("success", false);
			result.put("msg", "新增失败");
		}
				
		return result;
	}
}