package com.club.web.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.club.framework.log.ClubLogManager;
import com.club.web.common.Configuration;
import com.club.web.common.Constants;


/**
 * 用于检查用户是否登录了系统的过滤器<br>
 * @author 李飞
 */
public class SessionFilter implements Filter {

    private static final ClubLogManager logger = ClubLogManager.getLogger(SessionFilter.class);
	/** 检查不通过时，转发的URL */
	private String redirectUrl;
	private String webIP;
	private Map<String,String> loginExecutePageMap=new HashMap<String, String>();
	private Map<String,String> executePageMap=new HashMap<String, String>();

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		
		redirectUrl = cfg.getInitParameter("redirectUrl");
		webIP = Configuration.getString("webIP");
		String loginExecutePageStr=cfg.getInitParameter("loginExecutePage");
		List<Object> executePage=Configuration.getList("executePage");
		String[] loginExecutePageArr=loginExecutePageStr.split(",");
		for (String string : loginExecutePageArr) {
			loginExecutePageMap.put(string, "");
		}
		for (Object page : executePage) {
			executePageMap.put(page.toString(), "");
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		// * 请求 http://127.0.0.1:8080/webApp/home.jsp?&a=1&b=2 时
		// * request.getRequestURL()： http://127.0.0.1:8080/webApp/home.jsp
		// * request.getContextPath()： /webApp
		// * request.getServletPath()：/home.jsp
		// * request.getRequestURI()： /webApp/home.jsp
		// * request.getQueryString()：a=1&b=2
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String servletPath = request.getServletPath();
		String contextPath =request.getContextPath();
		String queryString =request.getQueryString();
		logger.debug("请求访问地址："+servletPath);
		// 如果请求的路径与redirectUrl相同，或请求的路径是排除的URL时，则直接放行
		if (executePageMap.containsKey(servletPath)||servletPath.equals(redirectUrl)||servletPath.contains("/noPrivilege")||servletPath.contains("/login")||servletPath.contains("/logout")) {
			logger.debug("访问地址为无需登录就能访问的地址，直接放行！");
			chain.doFilter(req, res);
			return;
		}
		if (queryString==null||!queryString.contains("token")) {
			Object sessionObj = request.getSession().getAttribute(Constants.STAFF);
			// 如果Session为空，则跳转到指定页面
			if (sessionObj == null) {
				logger.info("用户未登录，即将跳转到登录页面！");
				String redirect = contextPath + redirectUrl;
				/*
				 * login.jsp 的 <form> 表单中新增一个隐藏表单域： <input type="hidden"
				 * name="redirect" value="${param.redirect }">
				 *
				 * LoginServlet.java 的 service 的方法中新增如下代码： String redirect =
				 * request.getParamter("redirect"); if(loginSuccess){ if(redirect ==
				 * null || redirect.length() == 0){ // 跳转到项目主页（home.jsp） }else{ //
				 * 跳转到登录前访问的页面（java.net.URLDecoder.decode(s, "UTF-8")） } }
				 */
				if(servletPath.endsWith(".do")){
					response.setHeader("sessionstatus","timeout");
				}else{
					response.sendRedirect(redirect);
				}
			}
		}
		chain.doFilter(req, res);
	}
	@Override
	public void destroy() {
	}
}