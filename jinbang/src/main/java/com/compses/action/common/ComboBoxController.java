package com.compses.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compses.common.converter.vo.Config;
import com.compses.common.converter.vo.Dcc;
import com.compses.service.common.ComboBoxService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;

@Controller
@RequestMapping("comboBox")
public class ComboBoxController {
	@Autowired
	ComboBoxService comboBoxService;

	@RequestMapping("selectList.do")
	@ResponseBody
	public List<Map<String, Object>> selectList(
			@RequestParam(value = "paramMap", required = false) String paramStr,
			@RequestParam(value = "sqlKey",required = false) String sqlKey) throws DocumentException {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap=JsonUtils.toMap(paramStr);
		List<Map<String, Object>> comboList = comboBoxService.selectList(
				sqlKey, paramMap);
//		if(paramMap.containsKey("dccFlag")){//dcc测试工具使用的数据
//			for (int i = 0; i < comboList.size(); i++) {
//				   Map<String,Object> valueMap=comboList.get(i);
//					Object content=valueMap.get("content");//dcc测试工具用的属性
//					if(!StringUtils.isEmptyForObject(content)){
//						Config config=XML2DccUtils.anaysisXML(content.toString());
//						for(Dcc dcc:config.getMsgs()){
//							valueMap.put("id", dcc.getId()+"&"+dcc.getKey()+"&"+dcc.getName());
//							valueMap.put("name", dcc.getName());
//						}
//				}
//			}
//		}
		return comboList;
	}

	public static void main(String[] arg){
		int a=5;
		System.out.println((a%2==1)?(a+1)/2:a/2) ;
	}
}
