package com.club.web.datamodel.db.arg;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WfDbTableArg {
    private String pk_name = "table_name";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<WfDbTableCriteria> oredCriteria;

    public WfDbTableArg() {
        oredCriteria = new ArrayList<WfDbTableCriteria>();
    }

    public void setPk_name(String pk_name) {
        this.pk_name = StringEscapeUtils.escapeSql(pk_name);
    }

    public String getPk_name() {
        return pk_name;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = StringEscapeUtils.escapeSql(orderByClause);
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = StringEscapeUtils.escapeSql(groupByClause);
    }

    public String geGroupByClause() {
        return groupByClause;
    }

    public void setColumns(String columns) {
        this.columns = StringEscapeUtils.escapeSql(columns);
    }

    public String geColumns() {
        return columns;
    }

    public void setCountsql1(String countsql1) {
        this.countsql1 = StringEscapeUtils.escapeSql(countsql1);
    }

    public String geCountsql1() {
        return countsql1;
    }

    public void setCountsql2(String countsql2) {
        this.countsql2 = StringEscapeUtils.escapeSql(countsql2);
    }

    public String geCountsql2() {
        return countsql2;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public void setRowEnd(int rowEnd) {
        this.rowEnd = rowEnd;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public List<WfDbTableCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfDbTableCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfDbTableCriteria or() {
    	WfDbTableCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfDbTableCriteria createCriteria() {
    	WfDbTableCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfDbTableCriteria createCriteriaInternal() {
    	WfDbTableCriteria criteria = new WfDbTableCriteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        groupByClause = null;
        columns = null;
        countsql1 = null;
        countsql2 = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<WfDbTableCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfDbTableCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfDbTableCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfDbTableCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfDbTableCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbTableCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbTableCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbTableCriterion(condition, value1, value2));
        }

        public WfDbTableCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfDbTableCriteria) this;
        }
        public WfDbTableCriteria andDbNameIsNull() {
            addCriterion("db_name is null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameIsNotNull() {
            addCriterion("db_name is not null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameEqualTo(String value) {
            addCriterion("db_name =", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotEqualTo(String value) {
            addCriterion("db_name <>", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameGreaterThan(String value) {
            addCriterion("db_name >", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameGreaterThanOrEqualTo(String value) {
            addCriterion("db_name >=", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameLessThan(String value) {
            addCriterion("db_name <", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameLessThanOrEqualTo(String value) {
            addCriterion("db_name <=", value, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameLike(String value) {
            addCriterion("db_name like ", value, "db_name", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameLeftLike(String value) {
            addCriterion("db_name like ", value, "db_name", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotLeftLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameRightLike(String value) {
            addCriterion("db_name like ", value, "db_name", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotRightLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameIn(List<String> values) {
            addCriterion("db_name  in ", values, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotIn(List<String> values) {
            addCriterion("db_name not in ", values, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameBetween(String value1, String value2) {
            addCriterion("db_name between ", value1, value2, "db_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andDbNameNotBetween(String value1, String value2) {
            addCriterion("db_name not between ", value1, value2, "db_name");
            return (WfDbTableCriteria) this;
        }
        public WfDbTableCriteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameLike(String value) {
            addCriterion("table_name like ", value, "table_name", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameLeftLike(String value) {
            addCriterion("table_name like ", value, "table_name", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotLeftLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameRightLike(String value) {
            addCriterion("table_name like ", value, "table_name", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotRightLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameIn(List<String> values) {
            addCriterion("table_name  in ", values, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in ", values, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between ", value1, value2, "table_name");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between ", value1, value2, "table_name");
            return (WfDbTableCriteria) this;
        }
        public WfDbTableCriteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksLike(String value) {
            addCriterion("remarks like ", value, "remarks", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotLike(String value) {
            addCriterion("remarks  not like ", value, "remarks", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksLeftLike(String value) {
            addCriterion("remarks like ", value, "remarks", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotLeftLike(String value) {
            addCriterion("remarks  not like ", value, "remarks", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksRightLike(String value) {
            addCriterion("remarks like ", value, "remarks", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotRightLike(String value) {
            addCriterion("remarks  not like ", value, "remarks", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksIn(List<String> values) {
            addCriterion("remarks  in ", values, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in ", values, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between ", value1, value2, "remarks");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between ", value1, value2, "remarks");
            return (WfDbTableCriteria) this;
        }
        public WfDbTableCriteria andSourceIsNull() {
            addCriterion("source is null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceLike(String value) {
            addCriterion("source like ", value, "source", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotLike(String value) {
            addCriterion("source  not like ", value, "source", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceLeftLike(String value) {
            addCriterion("source like ", value, "source", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotLeftLike(String value) {
            addCriterion("source  not like ", value, "source", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceRightLike(String value) {
            addCriterion("source like ", value, "source", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotRightLike(String value) {
            addCriterion("source  not like ", value, "source", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceIn(List<String> values) {
            addCriterion("source  in ", values, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotIn(List<String> values) {
            addCriterion("source not in ", values, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceBetween(String value1, String value2) {
            addCriterion("source between ", value1, value2, "source");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between ", value1, value2, "source");
            return (WfDbTableCriteria) this;
        }
        public WfDbTableCriteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeLike(Date value) {
            addCriterion("modify_time like ", value, "modify_time", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotLike(Date value) {
            addCriterion("modify_time  not like ", value, "modify_time", 1);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeLeftLike(Date value) {
            addCriterion("modify_time like ", value, "modify_time", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotLeftLike(Date value) {
            addCriterion("modify_time  not like ", value, "modify_time", 0);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeRightLike(Date value) {
            addCriterion("modify_time like ", value, "modify_time", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotRightLike(Date value) {
            addCriterion("modify_time  not like ", value, "modify_time", 2);
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time  in ", values, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in ", values, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between ", value1, value2, "modify_time");
            return (WfDbTableCriteria) this;
        }

        public WfDbTableCriteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between ", value1, value2, "modify_time");
            return (WfDbTableCriteria) this;
        }

    }

    public static class WfDbTableCriteria extends GeneratedCriteria {

        protected WfDbTableCriteria() {
            super();
        }
    }

    public static class WfDbTableCriterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected WfDbTableCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfDbTableCriterion(String condition, Object value, int likeType) {
            this.condition = condition;
            if (likeType == 0) {
                this.value = "%" + value;
            }
            else if (likeType == 1) {
                this.value = "%" + value + "%";
            }
            else {
                this.value = value + "%";
            }
            this.typeHandler = null;
            this.singleValue = true;

        }

        protected WfDbTableCriterion(String condition, Object value,
                String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            }
            else {
                this.singleValue = true;
            }
        }

        protected WfDbTableCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfDbTableCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfDbTableCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}