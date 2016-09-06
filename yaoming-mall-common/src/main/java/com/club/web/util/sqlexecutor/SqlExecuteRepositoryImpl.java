package com.club.web.util.sqlexecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.util.sqlexecutor.dao.SqlExecuteMapper;

/**
 * 
 * @author yunpeng.xiong
 *
 */
@Repository
public class SqlExecuteRepositoryImpl implements SqlExecuteRepository {

	@Autowired private SqlExecuteMapper sqlExecuteDao;

	/**
	 * 禁用MySql外键校验，<br>
	 * 在try代码块中调用此方法，<br>
	 * 并且在finally代码块调用enableForeignKeyChecks()
	 */
	@Override
	public void disableForeignKeyChecks() {
		sqlExecuteDao.executeUpdate("SET FOREIGN_KEY_CHECKS=0;");
	}

	/**
	 * 启用MySql外键校验，<br>
	 * 在try代码块中调用disableForeignKeyChecks()<br>
	 * 并且在finally代码块调用此方法
	 */
	@Override
	public void enableForeignKeyChecks() {
		sqlExecuteDao.executeUpdate("SET FOREIGN_KEY_CHECKS=1;");
	}
	
}
