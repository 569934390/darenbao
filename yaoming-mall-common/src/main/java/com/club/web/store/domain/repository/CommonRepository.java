package com.club.web.store.domain.repository;


/**
 * 轮播图仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-12
 */
public interface CommonRepository {

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	<T> void save(T t) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	<T> void update(T t) throws Exception;

}
