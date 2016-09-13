package com.compses.util;

import org.springframework.context.ApplicationContext;

public class ApplicationUtil {
     private static ApplicationContext context;
     public static void setContext(ApplicationContext context) {
    	 if(ApplicationUtil.context==null){
    		 ApplicationUtil.context = context;
    	 }
	}


	public static Object getBean(String name){
		if(name==null){
			return null;
		}
	 
          return context.getBean(name);
      }
	
	public static <T> T getBean(Class<T> clazz){
		if(clazz==null){
			return null;
		}
		return context.getBean(clazz);
	}
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}
}