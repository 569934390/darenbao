/**
 * 
 */
package com.club.core.db.ds;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.club.framework.dto.AbstractDto;

/**
 * <Description>DBCP 配置信息 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月4日 <br>
 * @since V1.0<br>
 * @see com.club.core.db.ds <br>
 */

public class DbcpConfig extends AbstractDto {
    private DBType dbtype; // 数据库类型

    private boolean defaultAutoCommit = true; // 对于事务是否 autoCommit, 默认值为 true

    private boolean defaultReadOnly = false; // 对于数据库是否只能读取, 默认值为 false

    private String driverClassName; // 连接数据库所用的 JDBC Driver Class,

    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE; // 最大活动连接数， 可以从对象池中取出的对象最大个数，为0则表示没有限制，默认为8

    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE; // 最大空闲连接数，最大等待连接中的数量,设 0 为没有限制 （对象池中对象最大个数）

    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;; // 最小空闲连接数， 对象池中对象最小个数

    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT; // 最大等待秒数, 单位为 ms, 超过时间会丢出错误信息

    private int initialSize = 0;// 初始连接池大小

    private String password; // 登陆数据库所用的密码

    private String url; // 连接数据库的 URL

    private String username; // 登陆数据库所用的帐号

    private String validationQuery; // 验证连接是否成功, SQL SELECT 指令至少要返回一行

    private boolean removeAbandoned = false; // 是否自我中断, 默认是 false

    private int removeAbandonedTimeout = 300; // 几秒后会自我中断, removeAbandoned 必须为 true

    private boolean logAbandoned = false; // 是否记录中断事件, 默认为 false

    private long minEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS; // 大于0 ，进行连接空闲时间判断，或为0，对空闲的连接不进行验证；默认30分钟

    private long timeBetweenEvictionRunsMillis = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS; // 失效检查线程运行时间间隔，如果小于等于0，不会启动检查线程，默认-1

    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW; // 取得对象时是否进行验证，检查对象是否有效，默认为false

    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN; // 返回对象时是否进行验证，检查对象是否有效，默认为false

    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE; // 空闲时是否进行验证，检查对象是否有效，默认为false

    private int numTestsPerEvictionRun = GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;// 每次执行空间回收的回收个数

    public DbcpConfig(String typeName) {
        this.dbtype = DBType.getInstance(typeName);
        setDefaultProperties();
    }

    public DbcpConfig(DBType dbtype) {
        this.dbtype = dbtype;
        setDefaultProperties();
    }

    /**
     * 设置默认参数
     */
    private void setDefaultProperties() {
        if (null == this.dbtype)
            return;
        switch (this.dbtype) {
            case MYSQL:
                setValidationQuery("SELECT 1 ");
                setDriverClassName("com.mysql.jdbc.Driver");
                break;
            case ORACLE:
                setValidationQuery("SELECT 1 FROM DUAL ");
                setDriverClassName("oracle.jdbc.driver.OracleDriver");
                break;
            case INFORMIX:
                setValidationQuery("SELECT 1 FROM systables ");
                setDriverClassName("com.informix.jdbc.IfxDriver");
                break;
            case TIMESTEN:
                break;
            case MDB:
                break;
            case DB2:
                setValidationQuery("select 1 from sysibm.sysdummy1 ");
                setDriverClassName("com.ibm.db2.jcc.DB2Driver");
                break;
            case SQLSERVER2000:
                setValidationQuery("SELECT 1 ");
                setDriverClassName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                break;
            case SQLSERVER2005:
                setValidationQuery("SELECT 1 ");
                setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                break;

        }
    }

    /**
     * @return the dbtype
     */
    public DBType getDbtype() {
        return dbtype;
    }

    /**
     * @param dbtype the dbtype to set
     */
    public void setDbtype(DBType dbtype) {
        this.dbtype = dbtype;
    }

    /**
     * @return the defaultAutoCommit
     */
    public boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    /**
     * @param defaultAutoCommit the defaultAutoCommit to set
     */
    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    /**
     * @return the defaultReadOnly
     */
    public boolean isDefaultReadOnly() {
        return defaultReadOnly;
    }

    /**
     * @param defaultReadOnly the defaultReadOnly to set
     */
    public void setDefaultReadOnly(boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    /**
     * @return the driverClassName
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @param driverClassName the driverClassName to set
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * @return the maxActive
     */
    public int getMaxActive() {
        return maxActive;
    }

    /**
     * @param maxActive the maxActive to set
     */
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /**
     * @return the maxIdle
     */
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * @return the maxWait
     */
    public long getMaxWait() {
        return maxWait;
    }

    /**
     * @param maxWait the maxWait to set
     */
    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the validationQuery
     */
    public String getValidationQuery() {
        return validationQuery;
    }

    /**
     * @param validationQuery the validationQuery to set
     */
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    /**
     * @return the removeAbandoned
     */
    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    /**
     * @param removeAbandoned the removeAbandoned to set
     */
    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    /**
     * @return the removeAbandonedTimeout
     */
    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    /**
     * @param removeAbandonedTimeout the removeAbandonedTimeout to set
     */
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    /**
     * @return the logAbandoned
     */
    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    /**
     * @param logAbandoned the logAbandoned to set
     */
    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    /**
     * @return the minEvictableIdleTimeMillis
     */
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    /**
     * @param minEvictableIdleTimeMillis the minEvictableIdleTimeMillis to set
     */
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * @return the timeBetweenEvictionRunsMillis
     */
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     * @param timeBetweenEvictionRunsMillis the timeBetweenEvictionRunsMillis to set
     */
    public void setTimeBetweenEvictionRunsMillis(
            long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * @return the testOnBorrow
     */
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    /**
     * @param testOnBorrow the testOnBorrow to set
     */
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * @return the testOnReturn
     */
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    /**
     * @param testOnReturn the testOnReturn to set
     */
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /**
     * @return the testWhileIdle
     */
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /**
     * @param testWhileIdle the testWhileIdle to set
     */
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * @return the initialSize
     */
    public int getInitialSize() {
        return initialSize;
    }

    /**
     * @param initialSize the initialSize to set
     */
    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    /**
     * @return the numTestsPerEvictionRun
     */
    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    /**
     * @param numTestsPerEvictionRun the numTestsPerEvictionRun to set
     */
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

}
