package com.compses.dao;

import java.util.List;
import java.util.Map;

public interface IDropStatisticsDAO {
	public List<Map<String, Object>> selectWrongMessageNum(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectDelayTimeNum(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectSecondHandleNum(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectBusinessHandleSucess(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectMessageLostNum(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectSRSystemSendSucess(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectBusinessChangeNum(Map<String,Object> paramMap);
	public List<Map<String, Object>> selectJobParams(Map<String,Object> paramMap);
}
