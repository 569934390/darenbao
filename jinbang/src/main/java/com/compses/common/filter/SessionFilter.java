package com.compses.common.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.compses.common.Configuration;
import com.compses.common.Constants;
import com.compses.model.DopPrivilegeResource;
import com.compses.model.DopPrivilegeUser;
import com.compses.util.PrivilegeUtils;
import com.compses.util.StringUtils;

/**
 * 用于检查用户是否登录了系统的过滤器<br>
 * @author 李飞
 */
public class SessionFilter implements Filter {

	/** 要检查的 session 的名称 */
	private String sessionKey;

	/** 检查不通过时，转发的URL */
	private String redirectUrl;
	
	/** 检查不通过时，没有权限URL */
	private String noPrivilegeUrl;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		sessionKey = cfg.getInitParameter("sessionKey");
//		Properties properties=System.getProperties();
//		Set<?> set=properties.keySet();
//		Iterator it=set.iterator();
//		Map<String,Object> keyValueMap=new HashMap<String,Object>();
//		while(it.hasNext()){
//			String key=it.next().toString();
//			System.out.println(key+"==="+properties.get(key));
//		}

		redirectUrl = cfg.getInitParameter("redirectUrl");
		noPrivilegeUrl = cfg.getInitParameter("noPrivilegeUrl");
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// 如果 sessionKey 为空，则直接放行
		if (StringUtils.isEmpty(sessionKey)) {
			chain.doFilter(req, res);
			return;
		}
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
		// 如果请求的路径与redirectUrl相同，或请求的路径是排除的URL时，则直接放行
		if (servletPath.equals(redirectUrl)) {
			chain.doFilter(req, res);
			return;
		}
		if (servletPath.startsWith("/login")||servletPath.contains("/noPrivilege")) {//与登录相关的请求过滤掉
			chain.doFilter(req, res);
			return;
		}
		if(!Configuration.getBoolean("loginAuth")){
			DopPrivilegeUser user=new DopPrivilegeUser();
			user.setId(1);
			user.setName("admin");
			user.setRealName("管理员");
			request.getSession().setAttribute(Constants.STAFF, user);
//				request.getSession().setAttribute(Constants.STAFF_PRIVILEGE_LIST, null);
//				request.getSession().setAttribute(Constants.RESOURCE_PRIVILEGE_LIST, null);
			chain.doFilter(req, res);
			return;
		}
		
		Object sessionObj = request.getSession().getAttribute(sessionKey);
		// 如果Session为空，则跳转到指定页面
		if (sessionObj == null) {
//			String contextPath = request.getContextPath();
			String redirect = contextPath+redirectUrl;
			/*
			 * login.jsp 的 <form> 表单中新增一个隐藏表单域： <input type="hidden"
			 * name="redirect" value="${param.redirect }">
			 * 
			 * LoginServlet.java 的 service 的方法中新增如下代码： String redirect =
			 * request.getParamter("redirect"); if(loginSuccess){ if(redirect ==
			 * null || redirect.length() == 0){ // 跳转到项目主页（home.jsp） }else{ //
			 * 跳转到登录前访问的页面（java.net.URLDecoder.decode(s, "UTF-8")） } }
			 */
//			response.sendRedirect(redirect);
			if(servletPath.endsWith(".do")){
				response.setHeader("sessionstatus","timeout");
//				response.setContentType( "text/html; charset=utf-8" );
//				res.getWriter().write("window.top.location.href='"+redirect+"'");
				res.getWriter().flush();
			}
			else{
				response.sendRedirect(redirect);
//				response.setContentType( "text/html; charset=utf-8" );
//				res.getWriter().write("<script>window.top.location.href='"+redirect+"'</script>");
//				res.getWriter().flush();
			}
			
			return;
		} else {
			DopPrivilegeUser staff=(DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF);
			@SuppressWarnings("unchecked")
			List<DopPrivilegeResource> prs=(List<DopPrivilegeResource>) request.getSession().getAttribute(Constants.RESOURCE_PRIVILEGE_LIST);
			if(staff!=null&&!staff.getName().equals("admin")&&!PrivilegeUtils.doUrlFilter(prs, servletPath)){
				response.sendRedirect(contextPath+noPrivilegeUrl);
			}
			else{
				chain.doFilter(req, res);
			}
		}
	}

	@Override
	public void destroy() {
	}
}