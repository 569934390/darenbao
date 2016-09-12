package com.compses.common;


public class BaseException extends Exception {
	public BaseException(){
		
	}
	public BaseException(String message) {  
		   super(message);  
    }  
	
	public BaseException(Throwable cause) {  
		super(cause);  
    } 
	
	public BaseException(String message,Throwable cause) {  
		   super(message,cause);  
	}
	
	private static final long serialVersionUID = 614632013073787799L;
	private String errorCode;
	private Object params;
	private String errorMessage;
	private boolean success=false;

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
