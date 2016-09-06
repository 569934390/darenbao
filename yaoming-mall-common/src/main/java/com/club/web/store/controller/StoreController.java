package com.club.web.store.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.web.store.service.StoreService;
import com.club.web.store.vo.ClassifyVO;
import com.club.web.store.vo.DiscountGoodsVO;
import com.club.web.store.vo.GoodsCommentVO;
import com.club.web.store.vo.GoodsDetailVO;
import com.club.web.store.vo.GoodsSimpleVO;
import com.club.web.store.vo.OrderSimpleVO;

/**
 * @author yunpeng.xiong
 */

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired private StoreService storeService;

    @RequestMapping("index")
    public String index(Model model) {
        return "/store/index";
    }
    
    @RequestMapping("order")
    public String order(Model model) {
        return "/store/order";
    }
    
    @RequestMapping("product")
    public String product(Model model) {
        return "/store/product";
    }
    
    @RequestMapping("sureOrder")
    public String sureOrder(Model model) {
        return "/store/sure_order";
    }
    
    @RequestMapping("/getClassify")
    @ResponseBody
    public List<ClassifyVO> getClassify() {
        return storeService.getClassifyList();
    }

    @RequestMapping("/getDiscountGoods")
    @ResponseBody
    public DiscountGoodsVO getDiscountGoods(){
    	return storeService.getDiscountGoods();
    }

    @RequestMapping("/getRecommendGoods")
    @ResponseBody
    public List<GoodsSimpleVO> getRecommendGoods(HttpServletResponse response) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getRecommendGoods();
    }

    @RequestMapping("/getProvateCustomLeather")
    @ResponseBody
    public List<GoodsSimpleVO> getProvateCustomLeather(HttpServletResponse response) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getGoodsList(0x000000001000000AL, 1);
    }

    @RequestMapping("/getHotGoods")
    @ResponseBody
    public List<GoodsSimpleVO> getHotGoods(HttpServletResponse response) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getHotGoods();
    }

    @RequestMapping("/getGoodsList")
    @ResponseBody
    public List<GoodsSimpleVO> getGoodsList(HttpServletResponse response,
    		@RequestParam(value="classifyId", defaultValue="0") long classifyId, 
    		@RequestParam(value="page") int page) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getGoodsList(classifyId, page);
    }

    @RequestMapping("/getGoodsInfo")
    @ResponseBody
    public GoodsDetailVO getGoodsInfo(@RequestParam("goodsId") long goodsId,HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getGoodsInfo(goodsId);
    }
    
    @RequestMapping("/getComment")
    @ResponseBody
    public GoodsCommentVO getComment(@RequestParam("goodsId") long goodsId,HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getComment(goodsId);
    }

    @RequestMapping("/getOrders")
    @ResponseBody
    public List<OrderSimpleVO> getOrders(
    		@RequestParam(value="orderStatus", defaultValue="0") int orderStatus, 
    		@RequestParam(value="page") int page,
    		HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	return storeService.getOrders(orderStatus, page);
    }

    @RequestMapping("/getQrCode")
    @ResponseBody
    public Map<String, Object> getQrCode(@RequestParam(value="userId") String userId,HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("qrcode", storeService.getQrCode(userId));
    	return map;
    }
    
}