package com.club.web.common.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.club.framework.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.framework.util.Utils;
import com.club.web.common.Constants;
import com.club.web.common.Constants.Status;
import com.club.web.common.service.IBaseService;

/**
 * <Description>自动生成代码 <br>
 * 
 * @author lifei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年12月11日 <br>
 * @since V1.0<br>
 */

@Controller
@RequestMapping("/index")
public class IndexController {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(IndexController.class);

    private static final String ERROR_TIMES = "ERROR_TIMES";

	public static Map<String,String> phoneCodeCache = new HashMap<String, String>();
    
    @Autowired
    private IBaseService baseService;

	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> login(HttpSession session,HttpServletRequest request,HttpServletResponse response,String loginName,String password,String randCode) throws BaseAppException {
		logger.debug("user login ");
		Map<String,Object> result = new HashMap<String, Object>();
		if(loginName!=null){
			 loginName=loginName.trim();
			 if(!Pattern.matches("(^[a-zA-Z0-9]$)|(^[a-zA-Z0-9]+[a-zA-Z0-9_]*[a-zA-Z0-9]+$)", loginName)){
				result.put("success", false);
            	result.put("type", 1);
            	result.put("msg", "用户名只能数字、字母、下划线（禁止首尾）");
            	return result;
			 }
		}
		if(password!=null){
			  password=password.trim();
			  if(!Pattern.matches("^[a-zA-Z0-9`\\-\\=\\\\\\[\\];',.\\/\\~\\!@#$%\\^&\\*()_\\+|\\?><\":\\{\\}]*", password)){
				  result.put("success", false);
	              result.put("type", 1);
	              result.put("msg", "密码：包含字母（A-Z）大小写敏感；数字（0-9）；特殊字符（`、-、=、\\、[、]、;、'、,、.、/、~、!、@、#、$、%、^、&、*、(、)、_、+、|、?、>、<、\"、:、{、}）");
	              return result;
			  }
		}
		Map<String, Object> conditions = new HashMap<String, Object>(2);
		conditions.put("loginName", loginName);
		conditions.put("staffState", Status.PUBLISH.toString());
		conditions.put("password", password);
		List<Map<String, Object>> list = baseService.selectList("staff_t", conditions);
		if (list.size() == 0) {
			result.put("success", false);
			result.put("msg", "账号或密码错误");//上海poc测试修改
			result.put("type", 2);
		}
		else {
			session.setAttribute(Constants.STAFF, list.get(0));
			result.put("success", true);
			result.put("msg", "登录成功");
		}

        return result;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session,HttpServletRequest request,HttpServletResponse response){
		logger.debug("user logout ");
		Map<String,String> loginMap=(Map<String, String>) request.getSession(false).getAttribute("loginMap");
		
    	session.invalidate();
        return new ModelAndView(new RedirectView("../login.html"));
	}

    @RequestMapping("sendRedirect")
	public void sendRedirect(String strURL,HttpServletResponse response){
    	logger.debug("open url:{0}",strURL);
		try {
			if(strURL.lastIndexOf("?")!=-1){
				strURL+="&privilegeCode="+UUID.randomUUID();
			}
			else{
				strURL+="?privilegeCode="+UUID.randomUUID();
			}
			response.sendRedirect(strURL);
		} catch (IOException e) {
			logger.error(e);
		}
		return;
	}
    
    @RequestMapping("check")
	public ModelAndView check(String code,HttpServletResponse response){
    	if(StringUtils.isEmpty(code)){
    		return new ModelAndView(new RedirectView("/privilege/noPrivilege.js"));
    	}
    	else{
    		return new ModelAndView(new RedirectView("/privilege/yesPrivilege.js"));
    	}
	}

	private String verifyCode(String phoneCode){
		String verifyCode = StringUtils.random(4);
		phoneCodeCache.put(phoneCode,verifyCode);
		phoneCodeCache.put(phoneCode+"_time",new Date().getTime()+"");
		return verifyCode;
	}

	@RequestMapping("verifyPhone")
	@ResponseBody
	public Map<String,Object> verifyPhone(String phoneCode) throws UnsupportedEncodingException {
		Map<String,Object> result = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(phoneCode)){
			String userId = "212558791940096000";
			String token = "3J0dwS8FDs6aD1HLKMhHWkYLr3Zarbky";
			String verifyCode = "";
			if (phoneCodeCache.containsKey(phoneCode)){
				long delay = Long.parseLong(phoneCodeCache.get(phoneCode + "_time"));
				if (new Date().getTime()-delay>3600*1000){
					verifyCode = this.verifyCode(phoneCode);
				}
				else{
					verifyCode = phoneCodeCache.get(phoneCode);
				}
			}
			else{
				verifyCode = this.verifyCode(phoneCode);
			}
			logger.info(verifyCode);
			verifyCode = "凯握商城会员管理验证码"+verifyCode+"请注意查收。【凯握商城】";
			String message = URLEncoder.encode(verifyCode, "utf-8");
			String query = "/msgservice/sms/sendmsg?userId="+userId+"&mobile="+phoneCode+"&content="+verifyCode;
			String sign = StringUtils.md5(query+token);
			String url = "http://115.159.25.170:8080"+ query + "&sign="+sign;
			String msg = HttpUtils.sendGet("http://115.159.25.170:8080/msgservice/sms/sendmsg", "userId="+userId+"&mobile=" + phoneCode + "&content=" + message + "&sign=" + sign);
			result.put("success", true);
			result.put("resultData",msg);
			logger.info(url);
			System.out.println(url);
			System.out.println(verifyCode+msg);
			result.put("msg","验证码已发送至手机");
			result.put("timeout",60000);
		}
		else {
			result.put("success",false);
			result.put("msg","电话号码无效！");
		}
		return result;
	}
	
	public static void main(String[] args) {
		String query = "/msgservice/sms/sendmsg?userId=212558791940096000&mobile=18559696600&content=凯渥商城会员管理验证码6532请注意查收";
		String token = "3J0dwS8FDs6aD1HLKMhHWkYLr3Zarbky";
//		199E11CFF7703CD1F39D3133AA6873D0
//		DB2352CB0119B08250ABCEDED49C98A6
				String sign = StringUtils.md5(query+token);
				System.out.println(sign);
	}
}
