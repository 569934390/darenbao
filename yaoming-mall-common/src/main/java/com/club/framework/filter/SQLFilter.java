package com.club.framework.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.club.framework.consts.SysConsts;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.SecurityUtil;

public class SQLFilter implements Filter {
	private static final ClubLogManager logger = ClubLogManager.getLogger(SQLFilter.class);
	public static String badStr = null;
	FilterConfig config = null;

	public void destroy() {
		this.config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		Enumeration<String> paraNames = req.getParameterNames();
		String paraValue = "";
		while(paraNames.hasMoreElements()){
			String name = paraNames.nextElement().toString();
			String[] values = req.getParameterValues(name);
			if(null != values && values.length > 0){
				for(String val : values){
					paraValue += val;
				}
			}
		}
		
		badStr = null;
		// 判断用户参数是否包含了SQL的非法字符
		if(SecurityUtil.isContainedSQL(paraValue)){
			logger.error(SysConsts.Log.PRESTR+"用户发送请求参数中含有非法字符："+paraValue);
			
//			rep.setContentType("text/html;charset=utf-8");
//			rep.getWriter().print("请不要尝试注入<br><a href='#' onclick='history.go(-1)'>返回</a>");
//			throw new IllegalArgumentException("SQLFilter-输入非法字符");
			String responseMsg = "{\"msg\":\"" + "请勿在表单中输入敏感词汇:" + badStr + "！" + "\",\"success\":false,\"errCode\":\""
					+ "SQLFilter" + "\"}";
			// JSON格式返回
			try {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter writer = response.getWriter();
				writer.write(responseMsg);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.error(e);
			}
			return;
		}else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
