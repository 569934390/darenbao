package com.club.web.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.club.web.common.vo.StaffTVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.exception.BaseAppException;
import com.club.web.common.Constants;
import com.club.web.common.service.IResetPassWordService;

@Controller
@RequestMapping("/resetPassWord")
public class ResetPassWordController {

	@Autowired
	private IResetPassWordService resetPassWordService;

	@RequestMapping("getStaff")
	@ResponseBody
	public StaffTVO getStaff(HttpServletRequest request) throws BaseAppException {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {
			long staffId = Long.parseLong(loginMap.get("staffId").toString());
			return resetPassWordService.getStaff(staffId);
		} else {
			return null;
		}

	}

	@RequestMapping("updatePassWord")
	@ResponseBody
	public Map<String, Object> updatePassWord(String modelJson,HttpServletRequest request) throws BaseAppException {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (loginMap != null && loginMap.get("staffId") != null) {
				long staffId = Long.parseLong(loginMap.get("staffId").toString());
				result = resetPassWordService.updateStaff(modelJson,staffId);
			}else{
				result.put("msg", "更新失败");
				result.put("success", false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "更新失败");
			result.put("success", false);
		}
		return result;
	}

}
