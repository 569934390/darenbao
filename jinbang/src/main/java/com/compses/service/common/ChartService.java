package com.compses.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.IChartDAO;
import com.compses.model.DopChart;
import com.compses.model.DopChartDetail;
import com.compses.model.DopNetworkNode;
import com.compses.model.DopNodeChart;

@Service
@Transactional
public class ChartService {
	@Autowired
	IChartDAO chartDAO;
	@Autowired
	IBaseCommonDAO baseCommonDAO;
	
	public List<DopNetworkNode> loadChartData(){
		List<DopNetworkNode> nodes = chartDAO.loadNode();
		for(DopNetworkNode node : nodes){
			node.setChildren(chartDAO.loadNodeChart(node));
		}
		return nodes;
	}
	
	public List<Object> loadChartElementData(DopChart chart){
		List<Object> results = new ArrayList<Object>();
		List<DopChartDetail> elements = chartDAO.loadChartDetail(chart);
		if(elements.size()>0){
			for(DopChartDetail element : elements){
				if(element.getSqlData()==null||"".equals(element.getSqlData()))
					continue;
				List<Map<String,Object>> datas = chartDAO.loadScriptDataBySql(element.getSqlData());
				results.add(datas);
			}
		}
		return results;
	}
	
	
	public int saveOrUpdate(DopChart chart){
		if(chart.getChartId()==null){
			chart.setChartId(baseCommonDAO.save(chart).intValue());
		}
		else{
			baseCommonDAO.update(chart);
		}
		chartDAO.deleteByChartId(chart);
		for(DopChartDetail detail : chart.getChartDetails()){
			detail.setChartId(chart.getChartId());
			baseCommonDAO.save(detail);
		}
		chart.getXs().setChartId(chart.getChartId());
		baseCommonDAO.save(chart.getXs());
		return chart.getChartId();
	}

	public void delete(String[] ids){
		for(String id : ids){
			DopChart chart = new DopChart();
			chart.setChartId(Integer.parseInt(id));
			baseCommonDAO.delete(chart);
			chartDAO.deleteByChartId(chart);
		}
	}
	
	public DopChart load(DopChart chart){
		chart = baseCommonDAO.selectOne(chart);
		DopChartDetail detail = new DopChartDetail();
		detail.setChartId(chart.getChartId());
		List<DopChartDetail> details = baseCommonDAO.loadData(detail);
		chart.setChartDetails(new ArrayList<DopChartDetail>());
		for(DopChartDetail d : details){
			if(d.getType().equals("x")){
				chart.setXs(d);
			}
			else{
				chart.getChartDetails().add(d);
			}
		}
		return chart;
	}
	
	public Long addToNode(DopNodeChart nodeChart,DopChart chart) {
		baseCommonDAO.update(chart);
		chartDAO.deleteByChartId(nodeChart);
		return baseCommonDAO.save(nodeChart);
	}
}
