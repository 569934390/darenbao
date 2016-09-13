package com.compses.common.mybatis.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.compses.common.mybatis.Dialect;
import com.compses.dto.Page;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	// 日志对象
	protected static Logger logger = LoggerFactory
			.getLogger(PageInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private String dialect=null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
	 * .Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaStatementHandler = MetaObject
				.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		RowBounds rowBounds = (RowBounds) metaStatementHandler
				.getValue("delegate.rowBounds");
		Connection connection = (Connection) invocation.getArgs()[0];
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
//			DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler
//					.getValue("delegate.parameterHandler");
//			Object parameterObject = defaultParameterHandler.getParameterObject();
//			if(parameterObject instanceof Page){
//				Page<?> page=(Page<?>)parameterObject;
//				//设置查询总条数的sql
//				setTotalSql(boundSql,metaStatementHandler,page);
//		        // 重设分页参数里的总页数等
//		        setPageParameter(boundSql.getSql(), connection, mappedStatement, boundSql, page);
//				return invocation.proceed();
//			}else{
				return invocation.proceed();
//			}
		}else{
			Page<?> page=(Page<?>)rowBounds;
			
//			Object sidx = parameterMap.get("_sidx");
//			Object sord = parameterMap.get("_sord");

//			String originalSql = (String) metaStatementHandler
//					.getValue("delegate.boundSql.sql");

//			if (sidx != null && sord != null) {
//				originalSql = originalSql + " order by " + sidx + " " + sord;
//			}
			//设置查询总条数的sql
			setTotalSql(boundSql.getSql(), connection, mappedStatement, boundSql, page);
	        // 重设分页参数里的总页数等
			setPageParameter(boundSql,metaStatementHandler,page);
			return invocation.proceed();
		}
		
	}
	/**
	 * 设置分页查询语句
	 * @param boundSql
	 * @param metaStatementHandler
	 * @param page
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public void setPageParameter(BoundSql boundSql,MetaObject metaStatementHandler,Page<?> page) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Dialect dialect=(Dialect) Class.forName(this.dialect).newInstance();
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect
				.getLimitString(boundSql.getSql(), page.getStart(),
						page.getLimit()));
		metaStatementHandler.setValue("delegate.rowBounds.offset",
				RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit",
				RowBounds.NO_ROW_LIMIT);
		if (logger.isDebugEnabled()) {
			logger.debug("生成分页SQL : " + boundSql.getSql());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	/**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数
     * <code>PageParameter</code>获得相关信息。
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setTotalSql(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, Page<?> page) {
        // 记录总记录数
        String countSql = "select count(0) total from (" + sql + ")";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
//            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
//            page.setTotalPage(totalPage);

        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }

    }
    
    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	/** 
     * 设置注册拦截器时设定的属性 
     */  
    public void setProperties(Properties properties) {  
       this.dialect = properties.getProperty("dialect");  
    }  

}
