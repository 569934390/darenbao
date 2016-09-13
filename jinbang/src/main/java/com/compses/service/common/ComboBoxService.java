package com.compses.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compses.dao.IComboBoxDAO;

@Service
@Transactional
public class ComboBoxService {
	@Autowired
	IComboBoxDAO comboBoxDAO;

	public List<Map<String, Object>> selectList(String sqlKey,
			Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> comboList = comboBoxDAO.selectList(sqlKey,
				paramMap);
		return comboList;
	}
}
