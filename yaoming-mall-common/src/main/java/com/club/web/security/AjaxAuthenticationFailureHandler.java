package com.club.web.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected JsonResponseWriter jsonResponseWriter=new JsonResponseWriter();
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("url:" + request.getRequestURI());
		}
    	Map<String, Object>resultmap = new HashMap<String, Object>();
		resultmap.put("code", ApiResultBean.CODE_FAIL);
		resultmap.put("msg", "登录失败!");
    	jsonResponseWriter.writeAsUTF_8(response,resultmap);
    }
}