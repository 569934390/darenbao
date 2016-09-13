package com.compses.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用于检查用户是否登录了系统的过滤器<br>
 * @author 李飞
 */
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig cfg) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
	}

	@Override
	public void destroy() {
	}
}