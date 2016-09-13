package com.compses.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.compses.dao.IChartDAO;
import com.compses.model.DopChart;
import com.compses.model.DopChartDetail;
import com.compses.model.DopNetworkNode;
import com.compses.model.DopNodeChart;
import com.compses.util.DBUtils;

public class ChartDAO implements IChartDAO {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<DopNetworkNode> loadNode() {
		String sql=DBUtils.getSql("DopChart","loadNode");
		Map<String,Object> paramMap=new HashMap<String, Object>();
		return DBUtils.query(sql,paramMap,namedParameterJdbcTemplate,DopNetworkNode.class);
	}

	@Override
	public List<DopChart> loadNodeChart(DopNetworkNode node) {
		String sql=DBUtils.getSql("DopChart","loadNodeChart");
		return DBUtils.query(sql,node,namedParameterJdbcTemplate,DopChart.class);
	}

	@Override
	public List<DopChartDetail> loadChartDetail(DopChart chart) {
		String sql=DBUtils.getSql("DopChart","loadChartDetail");
		return DBUtils.query(sql,chart,namedParameterJdbcTemplate,DopChartDetail.class);
	}
	
	@Override
	public List<Map<String,Object>> loadScriptDataBySql(String sql) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("nowDate", new Date());
		return DBUtils.query(sql,params,namedParameterJdbcTemplate);
	}

	@Override
	public 	int deleteByChartId(DopChart  record) {
		String sql=DBUtils.getSql("DopChartDetail","deleteByChartId");
		return DBUtils.update(sql, namedParameterJdbcTemplate, record);
	}
	
	@Override
	public 	int deleteByChartId(DopNodeChart  record) {
		String sql=DBUtils.getSql("DopNodeChart","deleteByChartId");
		return DBUtils.update(sql, namedParameterJdbcTemplate, record);
	}
	
	
}
