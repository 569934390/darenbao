package com.club.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class NonAjaxRequestMatcher implements RequestMatcher {
	@Override
	public boolean matches(HttpServletRequest request) {
		return !"XmlHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
}
