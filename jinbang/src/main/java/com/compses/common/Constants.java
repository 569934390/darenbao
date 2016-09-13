package com.compses.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.compses.util.StringUtils;

public class Constants {
	public static Logger logger = Logger.getLogger(Constants.class);
	
	public static final String sqlmapPrefix="com.compses.dao.sqlmap.";
	
	public static final String STAFF="staff";
		
	public static final String SECURITY_ROLE="securityRole";
	
	public static final String ROLE_LIST="roleList";
	
	public static final String STAFF_PRIVILEGE_LIST="staffPrivilegeList";
	
	public static final String RESOURCE_PRIVILEGE_LIST="resourcePrivilegeList";

	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

	/** int类型的NULL表示. */
	public static final int NULL_INT = -2147483648;

	/** float类型的NULL表示. */
	public static final float NULL_FLOAT = 1.4E-45F;

	/** double类型的NULL表示. */
	public static final double NULL_DOUBLE = 4.9E-324D;

	/** long类型的NULL表示. */
	public static final long NULL_LONG = -9999999999999998L; // -9223372036854775808L;
																// 前台JAVASCRIPT没有这么大的值

	/** String类型的NULL标识 */
	public static final String NULL_STRING = "";

	// 编码集设置

	/** GBK编码集. */
	public static final String GBK_ENCODING = "GBK";

	/** GB2312编码集. */
	public static final String GB2312_ENCODING = "GB2312";

	/** ISO-8859-1编码集. */
	public static final String ISO_8859_1_ENCODING = "ISO-8859-1";

	/** UTF-8编码集. */
	public static final String UTF_8_ENCODING = "UTF-8";

	// easyui中树形节点的展开状态
	public static final String NODE_ICON_ACUTAL = "icon-actual";

	public static final String NODE_ICON_SYSTEM = "icon-system";

	public static final String NODE_ICON_VIRTUAL = "icon-virtual";

	public static String ctx = "";

	/**
	 * 告警常量
	 */
	// 告警升級
	public static final String ALR_UPGRADE = "ALR_UPGRADE";
	// 告警清除
	public static final String ALR_CLR = "ALR_CLR";
	// 告警刪除
	public static final String ALR_DEL = "ALR_DEL";
	// 告警確認
	public static final String ALR_ACK = "ALR_ACK";
	// 告警掛起
	public static final String ALR_SPD = "ALR_SPD";
	//system_ori_data_code告警类型对应的ori_category_id
	public static final int ALARM_TYPE = 1;
	//system_ori_data_code告警来源对应的ori_category_id
	public static final int ALARM_SOURCE = 2;
	//system_ori_data_code告警级别对应的ori_category_id
	public static final int ALARM_LEVEL = 3;
	//system_ori_data_code告警操作状态对应的ori_category_id
	public static final long OPRT_STATE = 4;
	//system_ori_data_code告警状态对应的ori_category_id
	public static final int ALARM_STATE = 5;
	//system_ori_data_code告警派单类型对应的ori_category_id
	public static final int ALARM_DISPATCH_STATUS = 6;
	//
	public static final String ALARM_RULE_UPGRADE = "ALARM_RULE_UPGRADE";
	/**
	 * Agent nodeTypeid
	 */
	public static final String AGENT_NODE_TYPE_ID = "13010001";
	
	static {
		loadProperties();
		ctx = getContextProperty("ctx").toString();
	}

	public static void loadProperties() {

		Properties prop = new Properties();
		synchronized (ctxPropertiesMap) {
			// String fileName = resources + File.separator + propertiesName+
			// ".properties";
			String fileName = "appconfig.properties";
			InputStream inputStream = Constants.class.getClassLoader()
					.getResourceAsStream(fileName);
			if (inputStream == null) {
				logger.error("don't find  file " + fileName);
			}
			try {
				prop.load(inputStream);
				Iterator<Object> propIt = prop.keySet().iterator();
				while (propIt.hasNext()) {
					String key = (String) propIt.next();
					ctxPropertiesMap.put(key, prop.get(key));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
	
	public static Object getContextProperty(String name,Object defaultValue) {
		if(StringUtils.isEmpty(ctxPropertiesMap.get(name))){
			return defaultValue;
		}
		else{
			return ctxPropertiesMap.get(name);
		}
	}

	public static Map<String, Object> getCtxPropertiesMap() {
		return ctxPropertiesMap;
	}

	public static void reloadAppConfig() {
		loadProperties();
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		Constants.loadProperties();
	}

}
