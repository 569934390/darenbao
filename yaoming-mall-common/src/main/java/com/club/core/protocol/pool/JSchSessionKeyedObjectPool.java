/**
 * 
 */
package com.club.core.protocol.pool;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.jcraft.jsch.Session;

/**
 * <Description>JSch Session连接池持有者 <br>
 * 目前本连接池以（ServerConfig）为KEY，（ServerConfig）代表不同的主机（以host,user,port为维度判定），每个KEY对应一个连接池组，通过Config控制池的大小、超时等配置<br>
 * 注意：对于不同的key，将使用一样的配置，并且第一次实例化JSchSessionKeyedObjectPool时就生效，后面的key都沿用这个配置
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月19日 <br>
 * @since V1.0<br>
 * @see com.club.core.pool <br>
 */

public class JSchSessionKeyedObjectPool {
    private KeyedObjectPool<ServerConfig, Session> pool;

    private org.apache.commons.pool.impl.GenericKeyedObjectPool.Config config;

    private static class SingletonHolder {
        public static JSchSessionKeyedObjectPool INSTANCE;
    }

    /**
     * 获取默认配置的连接池持有者实例对象，如果实例对象已经被实例化过，将直接返回
     * 
     * @return
     */
    public synchronized static JSchSessionKeyedObjectPool getInstance() {
        if (null != SingletonHolder.INSTANCE)
            return SingletonHolder.INSTANCE;
        SingletonHolder.INSTANCE = new JSchSessionKeyedObjectPool();
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取指定配置的连接池持有者实例对象，如果实例对象已经被实例化过，将直接返回
     * 
     * @param config
     * @return
     */
    public synchronized static JSchSessionKeyedObjectPool getInstance(
            org.apache.commons.pool.impl.GenericKeyedObjectPool.Config config) {
        if (null != SingletonHolder.INSTANCE)
            return SingletonHolder.INSTANCE;
        SingletonHolder.INSTANCE = new JSchSessionKeyedObjectPool(config);
        return SingletonHolder.INSTANCE;
    }

    /**
     * 使用默认配置实例化连接池
     */
    private JSchSessionKeyedObjectPool() {
        org.apache.commons.pool.impl.GenericKeyedObjectPool.Config config = new org.apache.commons.pool.impl.GenericKeyedObjectPool.Config();
        config.maxActive = 8;// 最大的活动数
        config.whenExhaustedAction = GenericKeyedObjectPool.WHEN_EXHAUSTED_BLOCK;// 如果当前没有可用的，则等待
        config.testOnBorrow = true;// 设定在借出对象时是否进行有效性检查
        config.testOnReturn = true;// 设定在还回对象时是否进行有效性检查
        config.testWhileIdle = true;// 设定在进行后台对象清理时，是否还对没有过期的池内对象进行有效性检查。不能通过有效性检查的对象也将被回收
        config.timeBetweenEvictionRunsMillis = 5 * 60 * 1000;// 设定间隔每过多少毫秒进行一次后台对象清理的行动。如果这个值不是正数，则实际上不会进行后台对象清理。
        config.minEvictableIdleTimeMillis = 15 * 60 * 1000;// 设定在进行后台对象清理时，视休眠时间超过了多少毫秒的对象为过期。过期的对象将被回收。如果这个值不是正数，那么对休眠时间没有特别的约束
        this.config = config;
        pool = new GenericKeyedObjectPool<ServerConfig, Session>(
                new JSchSessionFactory(), config);
    }

    /**
     * 使用传递进行的配置，构造连接池
     * 
     * @param config
     */
    private JSchSessionKeyedObjectPool(
            org.apache.commons.pool.impl.GenericKeyedObjectPool.Config config) {
        this.config = config;
        pool = new GenericKeyedObjectPool<ServerConfig, Session>(
                new JSchSessionFactory(), config);
    }

    /**
     * 获取连接池
     * 
     * @return
     */
    public synchronized KeyedObjectPool<ServerConfig, Session> getPool() {
        return pool;
    }

    /**
     * 获取连接池当前的配置，因本方法的调用必须通过getInstance获取，故不会返回null
     * 
     * @return
     */
    public org.apache.commons.pool.impl.GenericKeyedObjectPool.Config getPoolConfig() {
        return this.config;
    }

}
