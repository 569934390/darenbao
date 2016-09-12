package com.compses.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compses.model.DopChart;
import com.compses.model.DopNetworkNode;
import com.compses.model.DopNodeChart;
import com.compses.service.common.ChartService;
import com.compses.util.JsonUtils;

@Controller
@RequestMapping("chartController")
public class ChartController {
	@Autowired
    private ChartService chartService;

	@RequestMapping("loadChartData")
	@ResponseBody
	public List<DopNetworkNode> loadChartData(){
		List<DopNetworkNode> nodes = chartService.loadChartData();
		return nodes;
	}
	
	@RequestMapping("loadChartElementData")
	@ResponseBody
	public Map<String,Object> loadChartElementData(DopChart chart) throws InterruptedException{
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("result", chartService.loadChartElementData(chart));
		}catch(Exception e){
			result.put("success", false);
			result.put("result", "报错了"+e.getMessage()+e.toString());
		}
		return  result;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(String dopChartStr) {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			DopChart chart = JsonUtils.toBean(dopChartStr, DopChart.class);
			result.put("success", true);
			result.put("result", chartService.saveOrUpdate(chart));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("result", "报错了"+e.getMessage()+e.toString());
		}
		return  result;
	}
	
	@RequestMapping("delete.do")
	@ResponseBody
	public Result delTask(String idStr) {
		Result result = new Result();
		try{
			String[] ids = idStr.split(",");
			chartService.delete(ids);
		}
		catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage()+"["+e.toString()+"]");
		}
		return result;
	 }
	
	@RequestMapping("load.do")
	@ResponseBody
	public DopChart load(DopChart chart) {
		return chartService.load(chart);
	}
	
	@RequestMapping("addToNode.do")
	@ResponseBody
	public Result addToNode(DopNodeChart nodeChart,DopChart chart) {
		Result result = new Result();
		try{
			chartService.addToNode(nodeChart,chart);
		}
		catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage()+"["+e.toString()+"]");
		}
		return result;
	 }
}
