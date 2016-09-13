package com.compses.dao;

import java.util.List;
import java.util.Map;

import com.compses.model.DopChart;
import com.compses.model.DopChartDetail;
import com.compses.model.DopNetworkNode;
import com.compses.model.DopNodeChart;

public interface IChartDAO {
	List<DopNetworkNode> loadNode();
	List<DopChart> loadNodeChart(DopNetworkNode node);
	List<DopChartDetail> loadChartDetail(DopChart chart);
	List<Map<String,Object>> loadScriptDataBySql(String sql);
	int deleteByChartId(DopChart  record);
	int deleteByChartId(DopNodeChart  record);
}
