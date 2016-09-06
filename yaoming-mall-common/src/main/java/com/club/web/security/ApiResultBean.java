package com.club.web.security;

import java.util.Map;


public class ApiResultBean {
	public final static int CODE_SUCCESS = 0;
	public final static int CODE_FAIL = 1;
	public final static int CODE_RELOGIN = 2;
	/**未完善资料*/
	public final static int CODE_COMPLETE = 3;
	
	public final static int CODE_UPLOAD_FAILED = 400;
	public final static int CODE_DELETE_FILE_FAILED = 401;
	
	private int code;
	private Map<String,Object> data;
	private String error;

	public ApiResultBean() {
	}

	public ApiResultBean(int code, Map<String,Object> data,String error) {
		this.code = code;
		this.data = data;
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}