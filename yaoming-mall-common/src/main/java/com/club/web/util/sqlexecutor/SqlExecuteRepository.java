package com.club.web.util.sqlexecutor;

/**
 * 
 * @author yunpeng.xiong
 *
 */
public interface SqlExecuteRepository {

	/**
	 * 禁用MySql外键校验，<br>
	 * 在try代码块中调用此方法，<br>
	 * 并且在finally代码块调用enableForeignKeyChecks()
	 */
	public void disableForeignKeyChecks();
	
	/**
	 * 启用MySql外键校验，<br>
	 * 在try代码块中调用disableForeignKeyChecks()<br>
	 * 并且在finally代码块调用此方法
	 */
	public void enableForeignKeyChecks();
}
