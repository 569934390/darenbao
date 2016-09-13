package com.compses.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisFactoryUtil {

	private static final Logger logger= LoggerFactory.getLogger(RedisFactoryUtil.class);

	/**
	 * 存放普通的缓存数据
	 */
	private static final Integer D_0 = 0;
	/**
	 * 存放用户地理位置信息-实时上报位置
	 */
	private static final Integer D_2 = 2;
	/**
	 * 存放用户服务信息
	 */
	private static final Integer D_3 = 3;
	/**
	 * 存放统计信息
	 */
	private static final Integer D_4 = 4;
	/**
	 * 存放系统自动增涨相关信息 （清除缓存必须保留）
	 */
	private static final Integer D_5 = 5;
	/**
	 * 存放抢单信息，让先得到的接单成功。
	 */
	private static final Integer D_6 = 6;
	/**
	 * 存放用户的登录的信息
	 */
	private static final Integer D_7 = 7;
	/**
	 * 存放用户验证号码的数据
	 */
	private static final Integer D_8 = 8;
	/**
	 * 存放单用户登录，一个用户只能在一个手机登录
	 */
	private static final Integer D_9 = 9;
	/**
	 * 存放普通的缓存数据
	 */
	private static final Integer D_10 = 10;

	/**
	 * 轮询缓存信息
	 */
	private static final Integer D_11 = 11;


	/**
	 * 系统配置参数缓存信息
	 */
	private static final Integer D_12 = 12;

	/**
	 * 后台系统登录session缓存信息
	 */
	private static final Integer D_13 = 13;

	/**
	 * 管理一个动态参数，变化比较少的缓存
	 */
	private static final Integer D_14 = 14;

	/**
	 * 用户邀请码
	 */
	private static final Integer D_15 = 15;

	private static JedisPool pool = null;


	public static synchronized Jedis getRedis() {
		if (pool == null) {
			getPool();
		}
		Jedis jedis = pool.getResource();
		jedis.select(D_0);
		return jedis;
	}

	public static synchronized Jedis getERedis() {
		if (pool == null) {
			getPool();
		}
		Jedis jedis = pool.getResource();
		return jedis;
	}

	private static JedisPool getPool(){
		if (pool == null) {
			pool = init();
		}
		return pool;
	}


	private synchronized static JedisPool init() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxActive(RedisConfig.getMaxactive());
			config.setMaxIdle(RedisConfig.getMaxidle());
//			config.setMaxWait(RedisConfig.getMaxwait());
//			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			try {
				logger.debug("get redis pool from {}:{}",RedisConfig.getIp(),RedisConfig.getPort());
				pool = new JedisPool(config, RedisConfig.getIp(),
						RedisConfig.getPort(), RedisConfig.getTimeout(),RedisConfig.getPwd());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pool;
	}

	protected static Jedis getSingleSignOnRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_9);
		return jedis;
	}

	protected static Jedis getVerificationCodeRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_8);
		return jedis;
	}

	protected static Jedis getSessionRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_7);
		return jedis;
	}



	protected static Jedis getGrabRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_6);
		return jedis;
	}



	public static Jedis getAutoCodeRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_5);
		return jedis;
	}

	public static Jedis getSumRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_4);
		return jedis;
	}


	public static Jedis getLimtMobileRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_3);
		return jedis;
	}


	public static Jedis getSumStaffUserRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_2);
		return jedis;
	}


	public static Jedis getAcceptOrderRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_10);
		return jedis;
	}
	/**
	 * 配置轮询信息
	 * @return
	 */
	public static Jedis getPollingRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_11);
		return jedis;
	}

	/**
	 * 配置系统参数
	 * @return
	 */
	public static Jedis getParamRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_12);
		return jedis;
	}

	/**
	 * 配置App参数
	 * @return
	 */
	public static Jedis getAppParamRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_14);
		return jedis;
	}

	/**
	 * 后台系统登录session缓存信息
	 * @return
	 */
	public static Jedis getManagerSessionRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_13);
		return jedis;
	}

	/**
	 * 用户邀请码信息
	 * @return
	 */
	public static Jedis getInvitationCodeRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_15);
		return jedis;
	}

	/**
	 * 存放用户服务信息
	 * @return
	 */
	public static Jedis getUserServiceRedis() {
		Jedis jedis = getERedis();
		jedis.select(D_3);
		return jedis;
	}


	/**
	 * 将jedis返回到连接池中，并设置为null，避免重复return，而造成错误。 本处实现是为了避免开发上的错误，破坏了不修改参数的原则。
	 *
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis) {
		if (jedis == null) {
			return;
		}

		if (pool != null) {
			try {
				pool.returnResource(jedis);
			} catch (JedisException t) {
				logger.error("释放redis出错", t);;
				try {
					pool.returnBrokenResource(jedis);
				} catch (JedisException t1) {
					logger.error("释放异常redis出错", t1);
				}
			}
			jedis = null;
		}
	}

	/**
	 * 将jedis返回到连接池中，并设置为null，避免重复return，而造成错误。 本处实现是为了避免开发上的错误，破坏了不修改参数的原则。
	 *
	 * @param jedis
	 */
	public static void returnBrokenJedis(Jedis jedis) {
		if (jedis == null) {
			return;
		}
		if (pool != null) {
			logger.debug("return broken jedis to pool for key:{}");
			try {
				pool.returnBrokenResource(jedis);
			} catch (JedisException t) {
				logger.error("释放异常redis出错", t);;
			}
			jedis = null;
		}
	}





}
