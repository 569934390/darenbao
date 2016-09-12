package com.compses.dto;

import com.compses.util.QuartzUtil;


public class Quartz {
	private String secondExp="0";
	private String minuteExp=QuartzUtil.normalPlaceHolder;
	private String hourExp=QuartzUtil.normalPlaceHolder;
	private String dayOfMonthExp=QuartzUtil.specilPlaceHolder;
	private String monthExp=QuartzUtil.normalPlaceHolder;
	private String dayOfWeekExp=QuartzUtil.specilPlaceHolder;
	private String yearExp=QuartzUtil.normalPlaceHolder;

	private String dayOfWeek;
	private String dayOfMonth;
	private String dayOf;
	private String second="0";
	private String minuteStart;
	private String minuteEnd;
	private String hourStart;
	private String hourEnd;
	private String yearStart;
	private String yearEnd;
	private String cycle;
	private String cycleType;
	//月份也没有开始和结束,直接以逗号做分割符的形式全部列出
	private String month;
	
	private String samplTimeType;
	
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	
	public String getDayOfMonthExp() {
		return dayOfMonthExp;
	}
	public void setDayOfMonthExp(String dayOfMonthExp) {
		this.dayOfMonthExp = dayOfMonthExp;
	}
	public String getDayOfWeekExp() {
		return dayOfWeekExp;
	}
	public void setDayOfWeekExp(String dayOfWeekExp) {
		this.dayOfWeekExp = dayOfWeekExp;
	}
	
	
	public String getYearExp() {
		return yearExp;
	}
	public void setYearExp(String yearExp) {
		this.yearExp = yearExp;
	}
	
	public String getSecondExp() {
		return secondExp;
	}
	public void setSecondExp(String secondExp) {
		this.secondExp = secondExp;
	}
	public String getMinuteExp() {
		return minuteExp;
	}
	public void setMinuteExp(String minuteExp) {
		this.minuteExp = minuteExp;
	}
	public String getHourExp() {
		return hourExp;
	}
	public void setHourExp(String hourExp) {
		this.hourExp = hourExp;
	}
	public String getMonthExp() {
		return monthExp;
	}
	public void setMonthExp(String monthExp) {
		this.monthExp = monthExp;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getDayOf() {
		return dayOf;
	}
	public void setDayOf(String dayOf) {
		this.dayOf = dayOf;
	}
	public String getMinuteStart() {
		return minuteStart;
	}
	public void setMinuteStart(String minuteStart) {
		this.minuteStart = minuteStart;
	}
	public String getMinuteEnd() {
		return minuteEnd;
	}
	public void setMinuteEnd(String minuteEnd) {
		this.minuteEnd = minuteEnd;
	}
	public String getHourStart() {
		return hourStart;
	}
	public void setHourStart(String hourStart) {
		this.hourStart = hourStart;
	}
	public String getHourEnd() {
		return hourEnd;
	}
	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}
	public String getYearStart() {
		return yearStart;
	}
	public void setYearStart(String yearStart) {
		this.yearStart = yearStart;
	}
	public String getYearEnd() {
		return yearEnd;
	}
	public void setYearEnd(String yearEnd) {
		this.yearEnd = yearEnd;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getSamplTimeType() {
		return samplTimeType;
	}
	public void setSamplTimeType(String samplTimeType) {
		this.samplTimeType = samplTimeType;
	}
	
}
