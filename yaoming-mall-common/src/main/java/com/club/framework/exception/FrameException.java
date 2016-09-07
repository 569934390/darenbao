package com.club.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 框架异常
 * @author WUWY
 *
 */
public class FrameException extends Exception {
	private static final long serialVersionUID = 2597166840393065221L;
	
	String m_ErrorCode = "0";
	String m_ErrorMsg = "";
	String m_SysErrorMsg = "";
	private static Logger m_logger = null;
	
	public FrameException(){}
	
	public FrameException(String m_ErrorCode,String m_ErrorMsg,String m_SysErrorMsg){
		this.m_ErrorCode = m_ErrorCode;
		this.m_ErrorMsg = m_ErrorMsg;
		this.m_SysErrorMsg = m_SysErrorMsg;
		
		getLogger().debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg
				+ "][系统错误:" + m_SysErrorMsg + "]");
		getLogger().debug(getStackInfo());
	}
	
	/**
	 * @param ErrorMsg
	 * @since 2014
	 */
	public FrameException(String ErrorMsg) {
		m_ErrorMsg = ErrorMsg;
		getLogger().debug("[错误信息:" + m_ErrorMsg + "]");
		getLogger().debug(getStackInfo());
	}
	
	/**
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @since 2002
	 */
	public FrameException(String ErrorCode, String ErrorMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		getLogger().debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg + "]");
		getLogger().debug(getStackInfo());
	}
	
	public void printStackTrace() {
		System.out.println(" 错误代码 :" + m_ErrorCode);
		System.out.println(" 错误信息 :" + m_ErrorMsg);
		if (m_SysErrorMsg != null && !"".equals(m_SysErrorMsg))
			System.out.println(" 系统错误 :" + m_SysErrorMsg);
		super.printStackTrace();
	}
	
	public void printStackTrace(PrintWriter s) {
		s.println(" 错误代码 :" + m_ErrorCode);
		s.println(" 错误信息 :" + m_ErrorMsg);
		if (m_SysErrorMsg != null && !"".equals(m_SysErrorMsg))
			System.out.println(" 系统错误 :" + m_SysErrorMsg);
		super.printStackTrace();
	}
	
	public FrameException(String ErrorCode, String ErrorMsg,Throwable cause) {
        super.setStackTrace(cause.getStackTrace());
        getLogger();
        m_ErrorCode = ErrorCode;
        m_ErrorMsg = ErrorMsg;
        getLogger().debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg + "]");
        getLogger().debug(getStackInfo());
    }
	
	/**
	 * 获取异常信息
	 * @return
	 */
	public String getErrorMessage(){
		return m_ErrorMsg;
	}
	
	public String getErrorCode() {
		return m_ErrorCode;
	}

	public String getErrorMsg() {
		return m_ErrorMsg;
	}

	public String getSysErrorMsg() {
		return m_SysErrorMsg;
	}
	
	private Logger getLogger(){
		if(m_logger == null){
			m_logger = LoggerFactory.getLogger(FrameException.class);
		}
		return m_logger;
	}
	
	
	private String getStackInfo(){
		StringWriter sw = null;
		PrintWriter pw = null;
		String strMsg = "";

		sw = new StringWriter();
		pw = new PrintWriter(sw);
		try {
			this.printStackTrace(pw);
			sw.flush();
			strMsg = sw.toString();
			sw.close();
			pw.close();
		} catch (java.io.IOException le) {
			strMsg = "待定处理";
		}
		return strMsg;
	}
	
	
}
