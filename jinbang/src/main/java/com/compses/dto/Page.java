package com.compses.dto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 * 
 * User lyhcn
 */
public class Page<T> extends RowBounds{
	/**
	 * 公共变量 
	 */
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/**
	 * 分页参数
	 */
	protected int page = 1;
	protected int limit = 10;
	protected int start = 0;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;
	protected String sqlKey = "";
	protected String type = "properties";
	
	/**
	 * 返回结果
	 */
	protected List<T> result = new ArrayList<T>();
	/**
	 * 查询参数
	 */
	protected Map<String,Object> conditions = new HashMap<String, Object>();
	protected long totalCount = -1;

	public Page() {
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	@JsonIgnore
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPage(final int page) {
		this.page = page;

		if (page < 1) {
			this.page = 1;
		}
	}

	public Page<T> page(final int thePage) {
		setPage(thePage);
		return this;
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	/**
	 * 获得排序方向.
	 */
	@JsonIgnore
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	@JsonIgnore
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	@JsonIgnore
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@JsonIgnore
	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	@JsonIgnore
	public int getOffset() {//覆盖掉父类的offset，不把多余 的参数输出到前台
	    return super.getOffset();
	 }
	
	@JsonIgnore
	public String getSqlKey() {
		return sqlKey;
	}

	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}

	@JsonIgnore
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
