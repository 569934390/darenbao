package com.compses.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import com.compses.dto.Quartz;

public class QuartzUtil {
	public static String normalPlaceHolder = "*";
	public static String specilPlaceHolder = "?";
	public static String cyleSplit ="/";
	public static String startEndSplit = "-";
	public static String startEndField ="year,minute,hour";
	public static String[] cronExpressionArr=new String[]{"second","minute","hour","dayOfMonth","month","dayOfWeek","year"};  
	public static Quartz convertToQuartz(String cronExpression){
		Quartz quartz=new Quartz();
		int cycleInex=cronExpression.indexOf("/");
		if(cycleInex!=-1){//有周期的为自动采集,自动采集没有周期
			String cycle=cronExpression.substring(cycleInex+1).split(" ")[0];
			cronExpression=cronExpression.replace("/"+cycle,"/1");
			quartz.setCycleType(cronExpression);
			quartz.setCycle(cycle);
			return quartz;
		}else{
			String[] crons=cronExpression.split(" ");
			for (int i = 0; i < crons.length; i++) {
				try {
					BeanUtils.setProperty(quartz, cronExpressionArr[i]+"Exp", crons[i].equals("")?"*":crons[i]);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if(crons[i].equals("?")){
						if(3==i){
							quartz.setDayOf(cronExpressionArr[5]);
						}else{
							quartz.setDayOf(cronExpressionArr[3]);
						}
					}
					if(crons[i].indexOf("*")!=-1||crons[i].indexOf("?")!=-1){
						if(startEndField.contains(cronExpressionArr[i])){
							BeanUtils.setProperty(quartz, cronExpressionArr[i]+"Start", null);
							BeanUtils.setProperty(quartz, cronExpressionArr[i]+"End", null);
						}else{
							BeanUtils.setProperty(quartz, cronExpressionArr[i], null);
						}
						
					}else{
						String[] exp=crons[i].split("-");
						if(startEndField.contains(cronExpressionArr[i])){
							BeanUtils.setProperty(quartz, cronExpressionArr[i]+"Start", exp[0]);
							if(exp.length==1){
								BeanUtils.setProperty(quartz, cronExpressionArr[i]+"End", exp[0]);
							}else{
								BeanUtils.setProperty(quartz, cronExpressionArr[i]+"End", exp[1]);
							}
						}else{
							BeanUtils.setProperty(quartz, cronExpressionArr[i], exp);
						}
					}

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return quartz;
		}
		
	}
	public static String convertToCron(Quartz quartz,String collectType){
		
		String exp;
		StringBuilder  cronExpression=new StringBuilder();


		if(collectType.equals("2")){//自定义采集
			quartz.setMinuteExp(quartz.getMinuteStart()+startEndSplit+quartz.getMinuteEnd());
			quartz.setHourExp(quartz.getHourStart()+startEndSplit+quartz.getHourEnd());
			quartz.setYearExp(quartz.getYearStart()+startEndSplit+quartz.getYearEnd());
			quartz.setMonthExp(quartz.getMonth());
			try {
				exp=BeanUtils.getProperty(quartz, quartz.getDayOf());
				if(!StringUtils.isEmpty(exp)){
					BeanUtils.setProperty(quartz, quartz.getDayOf()+"Exp",exp);
				}else{
					BeanUtils.setProperty(quartz, quartz.getDayOf()+"Exp","*");
				}
				for (int i = 0; i < cronExpressionArr.length; i++) {
					exp=BeanUtils.getProperty(quartz, cronExpressionArr[i]+"Exp");
					String[] expArr=exp.split("-");//将0-0变为0
					if(expArr.length==2&&expArr[0].equals(expArr[1])){
						exp=expArr[0];
					}
					if(i==cronExpressionArr.length-1){
						cronExpression.append((exp.equals("")?"*":exp) +"");
					}else{
						cronExpression.append((exp.equals("")?"*":exp) +" ");
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}////设置dayOfWeek或者dayOfMonth的表达式
			return cronExpression.toString();
		}else{
			return quartz.getCycleType().replace("/1", "/"+quartz.getCycle());
		}
	}
	
	public static void main(String[] args) {
//		Quartz quartz=QuartzUtil.convertToQuartz("0 0-0 0-0 ? 1 1,2 2013-2013");
		Quartz quartz=QuartzUtil.convertToQuartz("0 0 0 ? 1 1,2 2013");
		String cron=QuartzUtil.convertToCron(quartz, "2");
		System.out.print(cron);

	}

}
