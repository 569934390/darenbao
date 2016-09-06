package com.club.web.common.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class WfDbMetaArg {
    private String pk_name = "db_name";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<WfDbMetaCriteria> oredCriteria;

    public WfDbMetaArg() {
        oredCriteria = new ArrayList<WfDbMetaCriteria>();
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

    public List<WfDbMetaCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfDbMetaCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfDbMetaCriteria or() {
    	WfDbMetaCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfDbMetaCriteria createCriteria() {
    	WfDbMetaCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfDbMetaCriteria createCriteriaInternal() {
    	WfDbMetaCriteria criteria = new WfDbMetaCriteria();
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
        protected List<WfDbMetaCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfDbMetaCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfDbMetaCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfDbMetaCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfDbMetaCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbMetaCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbMetaCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbMetaCriterion(condition, value1, value2));
        }

        public WfDbMetaCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfDbMetaCriteria) this;
        }
        public WfDbMetaCriteria andDbNameIsNull() {
            addCriterion("db_name is null");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameIsNotNull() {
            addCriterion("db_name is not null");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameEqualTo(String value) {
            addCriterion("db_name =", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotEqualTo(String value) {
            addCriterion("db_name <>", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameGreaterThan(String value) {
            addCriterion("db_name >", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameGreaterThanOrEqualTo(String value) {
            addCriterion("db_name >=", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameLessThan(String value) {
            addCriterion("db_name <", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameLessThanOrEqualTo(String value) {
            addCriterion("db_name <=", value, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameLike(String value) {
            addCriterion("db_name like ", value, "db_name", 1);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 1);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameLeftLike(String value) {
            addCriterion("db_name like ", value, "db_name", 0);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotLeftLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 0);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameRightLike(String value) {
            addCriterion("db_name like ", value, "db_name", 2);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotRightLike(String value) {
            addCriterion("db_name  not like ", value, "db_name", 2);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameIn(List<String> values) {
            addCriterion("db_name  in ", values, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotIn(List<String> values) {
            addCriterion("db_name not in ", values, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameBetween(String value1, String value2) {
            addCriterion("db_name between ", value1, value2, "db_name");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andDbNameNotBetween(String value1, String value2) {
            addCriterion("db_name not between ", value1, value2, "db_name");
            return (WfDbMetaCriteria) this;
        }
        public WfDbMetaCriteria andTypeIsNull() {
            addCriterion("type is null");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeLike(String value) {
            addCriterion("type like ", value, "type", 1);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotLike(String value) {
            addCriterion("type  not like ", value, "type", 1);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeLeftLike(String value) {
            addCriterion("type like ", value, "type", 0);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotLeftLike(String value) {
            addCriterion("type  not like ", value, "type", 0);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeRightLike(String value) {
            addCriterion("type like ", value, "type", 2);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotRightLike(String value) {
            addCriterion("type  not like ", value, "type", 2);
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeIn(List<String> values) {
            addCriterion("type  in ", values, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotIn(List<String> values) {
            addCriterion("type not in ", values, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeBetween(String value1, String value2) {
            addCriterion("type between ", value1, value2, "type");
            return (WfDbMetaCriteria) this;
        }

        public WfDbMetaCriteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between ", value1, value2, "type");
            return (WfDbMetaCriteria) this;
        }

    }

    public static class WfDbMetaCriteria extends GeneratedCriteria {

        protected WfDbMetaCriteria() {
            super();
        }
    }

    public static class WfDbMetaCriterion {
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

        protected WfDbMetaCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfDbMetaCriterion(String condition, Object value, int likeType) {
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

        protected WfDbMetaCriterion(String condition, Object value,
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

        protected WfDbMetaCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfDbMetaCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfDbMetaCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}