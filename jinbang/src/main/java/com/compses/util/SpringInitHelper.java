package com.compses.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * DESC:Spring初始化帮助类。用于获取Spring的上下文。
 *	@author PanGuofeng
 */
public class SpringInitHelper {

	private static ApplicationContext appContext;
	@Autowired
	private ApplicationContext cxt;

	protected void init(){
		if(appContext != null){
		}
		else{
			appContext = cxt;
		}
	}
	
	/**
	 * DESC:获取Spring上下文
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return appContext;
	}

	/**
	 * DESC:根据bean的名称获取bean对象。
	 *
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return appContext.getBean(beanName);
	}
	
	/**
	 * DESC:根据bean名称获取bean对象。
	 *
	 * @param beanName
	 * @param objects
	 * @return
	 */
	public static Object getBean(String beanName, Object...objects ){
		return appContext.getBean(beanName, objects);
	}
	
	/**
	 * DESC:根据bean的类获取bean对象。
	 *
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz){
		return appContext.getBean(clazz);
	}
	
	/**
	 * DESC:根据bean的名称和类获取bean对象。
	 *
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> clazz){
		return appContext.getBean(beanName, clazz);
	}
}
