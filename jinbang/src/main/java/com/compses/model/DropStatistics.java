package com.compses.model;

import java.util.Date;

public class DropStatistics {
	private Integer dropStatisticsId;

	private String dropKpiId;

	private Date dropStatisticsTime;

	private String dropStatisticsType;

	private String dropStatisticsResult;

	public Integer getDropStatisticsId() {
		return dropStatisticsId;
	}

	public void setDropStatisticsId(Integer dropStatisticsId) {
		this.dropStatisticsId = dropStatisticsId;
	}

	public String getDropKpiId() {
		return dropKpiId;
	}

	public void setDropKpiId(String dropKpiId) {
		this.dropKpiId = dropKpiId;
	}

	public Date getDropStatisticsTime() {
		return dropStatisticsTime;
	}

	public void setDropStatisticsTime(Date dropStatisticsTime) {
		this.dropStatisticsTime = dropStatisticsTime;
	}

	public String getDropStatisticsType() {
		return dropStatisticsType;
	}

	public void setDropStatisticsType(String dropStatisticsType) {
		this.dropStatisticsType = dropStatisticsType == null ? null
				: dropStatisticsType.trim();
	}

	public String getDropStatisticsResult() {
		return dropStatisticsResult;
	}

	public void setDropStatisticsResult(String dropStatisticsResult) {
		this.dropStatisticsResult = dropStatisticsResult;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		DropStatistics other = (DropStatistics) that;
		return (this.getDropStatisticsId() == null ? other
				.getDropStatisticsId() == null : this.getDropStatisticsId()
				.equals(other.getDropStatisticsId()))
				&& (this.getDropKpiId() == null ? other.getDropKpiId() == null
						: this.getDropKpiId().equals(other.getDropKpiId()))
				&& (this.getDropStatisticsTime() == null ? other
						.getDropStatisticsTime() == null : this
						.getDropStatisticsTime().equals(
								other.getDropStatisticsTime()))
				&& (this.getDropStatisticsType() == null ? other
						.getDropStatisticsType() == null : this
						.getDropStatisticsType().equals(
								other.getDropStatisticsType()))
				&& (this.getDropStatisticsResult() == null ? other
						.getDropStatisticsResult() == null : this
						.getDropStatisticsResult().equals(
								other.getDropStatisticsResult()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getDropStatisticsId() == null) ? 0 : getDropStatisticsId()
						.hashCode());
		result = prime * result
				+ ((getDropKpiId() == null) ? 0 : getDropKpiId().hashCode());
		result = prime
				* result
				+ ((getDropStatisticsTime() == null) ? 0
						: getDropStatisticsTime().hashCode());
		result = prime
				* result
				+ ((getDropStatisticsType() == null) ? 0
						: getDropStatisticsType().hashCode());
		result = prime
				* result
				+ ((getDropStatisticsResult() == null) ? 0
						: getDropStatisticsResult().hashCode());
		return result;
	}
}