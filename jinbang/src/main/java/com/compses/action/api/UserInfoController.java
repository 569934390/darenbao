package com.compses.action.api;

import com.compses.action.common.Result;
import com.compses.dto.Page;
import com.compses.dto.Quartz;
import com.compses.message.MessageCell;
import com.compses.message.MessageHeart;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.QuartzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("userinfo")
public class UserInfoController {
	
//	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseCommonService baseCommonService;
	

	@RequestMapping("getList.do")
	@ResponseBody
	public List<?> getList(HttpServletRequest request) {
		String sqlKey =request.getParameter("sqlKey");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("sqlKey",sqlKey);
		List<?> list = baseCommonService.loadData(paramMap);
		return list;
	}
}
