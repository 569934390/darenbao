/**
 * 
 */
package com.club.core.db.ds;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.club.framework.exception.ExceptionHandler;
import com.club.framework.exception.SystemErrorCode;
import com.club.framework.log.ClubLogManager;

/**
 * <Description>动态数据源管理类，可根据配置动态创建DHCP数据源连接池，也可以关闭连接池 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月4日 <br>
 * @since V1.0<br>
 * @see com.club.core.db.ds <br>
 */

@Service("dynamicDataSourceManager")
public class DynamicDataSourceManager {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(DynamicDataSourceManager.class);

    private static final ConcurrentMap<String, org.apache.commons.dbcp.BasicDataSource> dataSourcePoolMap = new ConcurrentHashMap<String, BasicDataSource>();

    private ConcurrentMap<String, org.apache.commons.dbcp.BasicDataSource> getPoolMap() {
        return this.dataSourcePoolMap;
    }

    private void validate(DbcpConfig dbcpConfig) {
        Assert.notNull(dbcpConfig, "dbcpConfig must not be null.");
        Assert.notNull(dbcpConfig.getDbtype(), "dbtype must not be null.");
        Assert.notNull(dbcpConfig.getDriverClassName(),
                "driverClassName must not be null.");
        Assert.notNull(dbcpConfig.getUrl(), "url must not be null.");
        Assert.notNull(dbcpConfig.getUsername(), "username must not be null.");
        Assert.notNull(dbcpConfig.getPassword(), "password must not be null.");
    }

    /**
     * 创建数据源连接池
     * 
     * @param poolId 连接池唯一标识，poolId
     * @param dbcpConfig 连接池的配置信息类
     */
    public synchronized void createDataSourcePool(String poolId,
            DbcpConfig dbcpConfig) {
        validate(dbcpConfig);
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName(dbcpConfig.getDriverClassName());
        basicDataSource.setUrl(dbcpConfig.getUrl());
        basicDataSource.setUsername(dbcpConfig.getUsername());
        basicDataSource.setPassword(dbcpConfig.getPassword());

        basicDataSource.setDefaultAutoCommit(dbcpConfig.isDefaultAutoCommit());
        basicDataSource.setDefaultReadOnly(dbcpConfig.isDefaultReadOnly());

        //
        basicDataSource.setMaxActive(dbcpConfig.getMaxActive());
        basicDataSource.setMaxIdle(dbcpConfig.getMaxIdle());
        basicDataSource.setMinIdle(dbcpConfig.getMinIdle());
        basicDataSource.setMaxWait(dbcpConfig.getMaxWait());

        basicDataSource.setInitialSize(dbcpConfig.getInitialSize());
        basicDataSource.setValidationQuery(dbcpConfig.getValidationQuery());

        basicDataSource.setRemoveAbandoned(dbcpConfig.isRemoveAbandoned());
        basicDataSource.setRemoveAbandonedTimeout(dbcpConfig
                .getRemoveAbandonedTimeout());
        basicDataSource.setLogAbandoned(dbcpConfig.isLogAbandoned());

        basicDataSource.setMinEvictableIdleTimeMillis(dbcpConfig
                .getMinEvictableIdleTimeMillis());
        basicDataSource.setTimeBetweenEvictionRunsMillis(dbcpConfig
                .getTimeBetweenEvictionRunsMillis());

        basicDataSource.setTestOnBorrow(dbcpConfig.isTestOnBorrow());
        basicDataSource.setTestOnReturn(dbcpConfig.isTestOnReturn());
        basicDataSource.setTestWhileIdle(dbcpConfig.isTestWhileIdle());

        basicDataSource.setNumTestsPerEvictionRun(dbcpConfig
                .getNumTestsPerEvictionRun());

        getPoolMap().put(poolId, basicDataSource);
    }

    /**
     * 获取数据源，如果不存在，则根据dbcpConfig配置创建
     * 
     * @param poolId
     * @param dbcpConfig
     * @return
     * @throws Exception
     */
    public synchronized BasicDataSource getDataSourcePoolByPoolId(
            String poolId, DbcpConfig dbcpConfig) throws Exception {
        // 先获取
        BasicDataSource basicDataSource = getPoolMap().get(poolId);
        if (basicDataSource == null) {
            logger.info(String
                    .format("Could not found datasource pool by this poolId[poolId=%s],created it now... ",
                            poolId));
            // 未获取到相应的数据源，则创建新的数据源连接池
            // add
            createDataSourcePool(poolId, dbcpConfig);
            // get
            basicDataSource = getPoolMap().get(poolId);

        }
        else {
            logger.info(String.format(
                    "Found datasource pool by this poolId[poolId=%s]!", poolId));
        }
        return basicDataSource;
    }

    /**
     * @param poolId
     * @return
     * @throws Exception
     */
    public synchronized BasicDataSource getDataSourcePoolByPoolId(String poolId)
            throws Exception {
        // 先获取
        BasicDataSource basicDataSource = getPoolMap().get(poolId);
        if (basicDataSource == null) {
            logger.error(String
                    .format("Could not found datasource pool by this poolId[poolId=%s]!Throw Exception...",
                            poolId));
            throw ExceptionHandler
                    .publish(
                            SystemErrorCode.LOOKUP_DATASOURCE_ERROR,
                            String.format(
                                    "Could not found this datasource pool by this poolId[poolId=%s]!Please created it first!",
                                    poolId));

        }
        logger.info(String.format(
                "Found datasource pool by this poolId[poolId=%s]!", poolId));
        return basicDataSource;
    }

    public synchronized void close(String poolId) throws SQLException {
        logger.debug(String.format(
                "Close datasource pool by poolId[poolId=%s]!", poolId));
        BasicDataSource basicDataSource = getPoolMap().get(poolId);
        if (basicDataSource == null) {
            logger.error(String
                    .format("Close pool,Could not found datasource pool by this poolId[poolId=%s],skip close it...",
                            poolId));
            return;
        }
        basicDataSource.close();
        dataSourcePoolMap.remove(poolId);
    }

    /**
     * 关闭所有数据源连接池
     */
    public synchronized void closeAll() {
        logger.debug(String.format("Close all datasource pool!"));
        Set<String> key = dataSourcePoolMap.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String poolId = it.next();
            BasicDataSource basicDataSource = dataSourcePoolMap.get(poolId);
            try {
                basicDataSource.close();
                dataSourcePoolMap.remove(poolId);
            }
            catch (Exception e) {
                logger.error(String.format(
                        "CloseAll Pool cast Exception [poolId=%s]",
                        ToStringBuilder.reflectionToString(basicDataSource)));
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否存在某个连接池
     * 
     * @param poolId
     * @return
     */
    public synchronized boolean isPoolExist(String poolId) {
        BasicDataSource basicDataSource = getPoolMap().get(poolId);
        if (basicDataSource == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取连接池的个数
     * 
     * @return
     */
    public synchronized int getPoolSize() {
        return getPoolMap().size();
    }
}
