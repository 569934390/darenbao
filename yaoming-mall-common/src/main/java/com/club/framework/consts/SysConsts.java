package com.club.framework.consts;
/**
 * 
 * @Description: 系统常量类
 * @author WUWY baocandy.wu@gmail.com 
 * @date 2014-8-6 下午3:04:39 
 *
 */
public interface SysConsts {
	/**
	 * 
	 * @Description: Action执行标志
	 * @author WUWY baocandy.wu@gmail.com 
	 * @date 2014-8-6 下午3:04:34 
	 *
	 */
	public interface Action{
		public static int FLAG_FAIL = 0;// 执行ACTION失败的标志
		public static int FLAG_SUCC = 1;// 执行ACTION成功的标志
	}
	
	/**
	 * 
	 * @Description: 缓存分类
	 * @author WUWY baocandy.wu@gmail.com 
	 * @date 2014-8-6 下午3:05:02 
	 *
	 */
	public interface Cache{
		/** SQL服务目录缓存 */
		public static String CATAGORY_SQL = "SQL";
		/** JAVA服务目录缓存 */
		public static String CATAGORY_SERVICE = "SERVICE";
		/** 系统配置缓存 */
		public static String CATAGORY_SYSCONFIG = "SYSCONFIG";
		
	}
	
	/**
	 * 
	 * @Description: 缓存KEY
	 * @author WUWY baocandy.wu@gmail.com 
	 * @date 2014-9-13 下午4:10:45 
	 *
	 */
	public interface CacheKey{
		/** 缓存页面SEO信息 */
		public static String CACHE_KEY_PAGESEO = "PAGESEO";
	}
	
	/**
	 * 
	 * @Description: 系统配置(KEY,VALUE),其中KEY做为常量在这边设置
	 * @author WUWY baocandy.wu@gmail.com 
	 * @date 2014-8-6 下午3:06:35 
	 *
	 */
	public interface SysConfig{
		public static String LIMIT_ACCESS_FILTER_FLAG = "LIMIT_ACCESS_FILTER_FLAG";// 是否需要访问过滤
		public static String LIMIT_ACCESS_ERROR_URL = "LIMIT_ACCESS_ERROR_URL";// 错误重定向页面URL
		public static String UNLIMIT_ACCESS_URL = "UNLIMIT_ACCESS_URL";// 不需要过滤的访问路径
		public static String LIMIT_ACCESS_PARAMS = "LIMIT_ACCESS_PARAMS";// 设置访问限制参数：URL后缀:有限时间:限制次数(防暴力攻击URL初始参数【url后缀：有限时间(分为单位)：限制次数】)
	}
	
	/**
	 * 
	 * @Description: session的key值定义
	 * @author WUWY baocandy.wu@gmail.com 
	 * @date 2014-8-6 下午3:33:38 
	 *
	 */
	public interface Session{
		/** 用户登陆应答包存session的key */
		public static String LOGIN_ASK_STRUCT = "LOGIN_ASK_STRUCT";
		/** 登陆用户 */
		public static String LOGIN_USER = "LOGIN_USER";
		/** 当前登录用户角色 */
		public static String LOGIN_ROLES = "LOGIN_ROLES";
		/** 当前登录用户权限 */
		public static String LOGIN_PRIVILEGES = "LOGIN_PRIVILEGES";
		/** 当前登录用户的菜单 */
		public static String LOGIN_MENUS = "LOGIN_MENUS";
		/** 当前登录用户的菜单功能 */
		public static String LOGIN_NENU_FUNCS = "LOGIN_MENU_FUNCS";
	}
	
	public interface Log{
		public static String PRESTR = "[SONGOTECH]::";
	}
}
