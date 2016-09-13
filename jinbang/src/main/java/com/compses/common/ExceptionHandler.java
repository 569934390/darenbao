package com.compses.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.compses.util.JsonUtils;

public class ExceptionHandler implements HandlerExceptionResolver{
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		if(exception instanceof BaseException){
			BaseException baseException=(BaseException)exception;
			logger.info("处理异常{}",baseException.getErrorCode());
			Map<String,Object> model=new HashMap<String,Object>();
			model.put("message", baseException.getMessage());
			model.put("success", baseException.isSuccess());
//			response.setHeader("requeststatus","error");
			JsonUtils.writeBaseException(request,response,baseException);
		}
		return null;
	}

}
