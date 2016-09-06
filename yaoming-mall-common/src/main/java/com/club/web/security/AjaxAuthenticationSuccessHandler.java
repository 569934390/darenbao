package com.club.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.club.web.common.Constants;
import com.club.web.common.Constants.Status;
import com.club.web.common.service.IBaseService;

@Configurable
public class AjaxAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(AjaxAuthenticationSuccessHandler.class);
	
    @Autowired private IBaseService baseService;
	
	protected JsonResponseWriter jsonResponseWriter=new JsonResponseWriter();
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("url:" + request.getRequestURI());
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>(2);
		conditions.put("loginName", request.getParameter("username"));
		conditions.put("staffState", Status.PUBLISH.toString());
		List<Map<String, Object>> list = baseService.selectList("staff_t", conditions);
    	Map<String, Object>resultmap = new HashMap<String, Object>();
		if(list!=null && list.size()>0) {
			request.getSession().setAttribute(Constants.STAFF, list.get(0));
			resultmap.put("code", ApiResultBean.CODE_SUCCESS);
			resultmap.put("msg", "登录成功!");
		} else {
			resultmap.put("code", ApiResultBean.CODE_FAIL);
			resultmap.put("msg", "登录失败!");
		}
		response.setHeader("Set-Cookie", "JSESSIONID="+request.getSession().getId()+"; Path="+request.getContextPath()+"/; HttpOnly" );
     	jsonResponseWriter.writeAsUTF_8(response,resultmap);
    }
    
    
}
