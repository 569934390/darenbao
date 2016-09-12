package com.compses.dao;

import java.util.List;
import java.util.Map;

public interface ITreeCommonDAO {

	List<?> loadTreeData(Map<String,Object> paramMap);
}
