package com.club.web.security;
/**
 * @author 王立
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AjaxLoginUrlAuthenticationEntryPoint.class);
	private JsonResponseWriter jsonResponseWriter = new JsonResponseWriter();

	public AjaxLoginUrlAuthenticationEntryPoint(String loginUrl) {
		super(loginUrl);
	}
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("url:" + request.getRequestURI());
		}
    	Map<String, Object>resultmap = new HashMap<String, Object>();
		resultmap.put("code", ApiResultBean.CODE_RELOGIN);
		resultmap.put("msg", "需要重新登录!");
		jsonResponseWriter.writeAsUTF_8(response,resultmap);
	}
}
