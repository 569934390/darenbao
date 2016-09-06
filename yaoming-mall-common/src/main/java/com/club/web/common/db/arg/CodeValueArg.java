package com.club.web.common.db.arg;

import java.util.*;
import org.apache.commons.lang.*;

public class CodeValueArg {
    private String pk_name = "code_name";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<CodeValueCriteria> oredCriteria;

    public CodeValueArg() {
        oredCriteria = new ArrayList<CodeValueCriteria>();
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

    public List<CodeValueCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(CodeValueCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public CodeValueCriteria or() {
    	CodeValueCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public CodeValueCriteria createCriteria() {
    	CodeValueCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected CodeValueCriteria createCriteriaInternal() {
    	CodeValueCriteria criteria = new CodeValueCriteria();
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
        protected List<CodeValueCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<CodeValueCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<CodeValueCriterion> getAllCriteria() {
            return criteria;
        }

        public List<CodeValueCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new CodeValueCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new CodeValueCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new CodeValueCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new CodeValueCriterion(condition, value1, value2));
        }

        public CodeValueCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (CodeValueCriteria) this;
        }
        public CodeValueCriteria andCodeTypeIsNull() {
            addCriterion("code_type is null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeIsNotNull() {
            addCriterion("code_type is not null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeEqualTo(String value) {
            addCriterion("code_type =", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotEqualTo(String value) {
            addCriterion("code_type <>", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeGreaterThan(String value) {
            addCriterion("code_type >", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("code_type >=", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeLessThan(String value) {
            addCriterion("code_type <", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeLessThanOrEqualTo(String value) {
            addCriterion("code_type <=", value, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeLike(String value) {
            addCriterion("code_type like ", value, "code_type", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotLike(String value) {
            addCriterion("code_type  not like ", value, "code_type", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeLeftLike(String value) {
            addCriterion("code_type like ", value, "code_type", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotLeftLike(String value) {
            addCriterion("code_type  not like ", value, "code_type", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeRightLike(String value) {
            addCriterion("code_type like ", value, "code_type", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotRightLike(String value) {
            addCriterion("code_type  not like ", value, "code_type", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeIn(List<String> values) {
            addCriterion("code_type  in ", values, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotIn(List<String> values) {
            addCriterion("code_type not in ", values, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeBetween(String value1, String value2) {
            addCriterion("code_type between ", value1, value2, "code_type");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeTypeNotBetween(String value1, String value2) {
            addCriterion("code_type not between ", value1, value2, "code_type");
            return (CodeValueCriteria) this;
        }
        public CodeValueCriteria andCodeNameIsNull() {
            addCriterion("code_name is null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameIsNotNull() {
            addCriterion("code_name is not null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameEqualTo(String value) {
            addCriterion("code_name =", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotEqualTo(String value) {
            addCriterion("code_name <>", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameGreaterThan(String value) {
            addCriterion("code_name >", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("code_name >=", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameLessThan(String value) {
            addCriterion("code_name <", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameLessThanOrEqualTo(String value) {
            addCriterion("code_name <=", value, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameLike(String value) {
            addCriterion("code_name like ", value, "code_name", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotLike(String value) {
            addCriterion("code_name  not like ", value, "code_name", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameLeftLike(String value) {
            addCriterion("code_name like ", value, "code_name", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotLeftLike(String value) {
            addCriterion("code_name  not like ", value, "code_name", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameRightLike(String value) {
            addCriterion("code_name like ", value, "code_name", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotRightLike(String value) {
            addCriterion("code_name  not like ", value, "code_name", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameIn(List<String> values) {
            addCriterion("code_name  in ", values, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotIn(List<String> values) {
            addCriterion("code_name not in ", values, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameBetween(String value1, String value2) {
            addCriterion("code_name between ", value1, value2, "code_name");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeNameNotBetween(String value1, String value2) {
            addCriterion("code_name not between ", value1, value2, "code_name");
            return (CodeValueCriteria) this;
        }
        public CodeValueCriteria andCodeValueIsNull() {
            addCriterion("code_value is null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueIsNotNull() {
            addCriterion("code_value is not null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueEqualTo(String value) {
            addCriterion("code_value =", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotEqualTo(String value) {
            addCriterion("code_value <>", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueGreaterThan(String value) {
            addCriterion("code_value >", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueGreaterThanOrEqualTo(String value) {
            addCriterion("code_value >=", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueLessThan(String value) {
            addCriterion("code_value <", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueLessThanOrEqualTo(String value) {
            addCriterion("code_value <=", value, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueLike(String value) {
            addCriterion("code_value like ", value, "code_value", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotLike(String value) {
            addCriterion("code_value  not like ", value, "code_value", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueLeftLike(String value) {
            addCriterion("code_value like ", value, "code_value", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotLeftLike(String value) {
            addCriterion("code_value  not like ", value, "code_value", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueRightLike(String value) {
            addCriterion("code_value like ", value, "code_value", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotRightLike(String value) {
            addCriterion("code_value  not like ", value, "code_value", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueIn(List<String> values) {
            addCriterion("code_value  in ", values, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotIn(List<String> values) {
            addCriterion("code_value not in ", values, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueBetween(String value1, String value2) {
            addCriterion("code_value between ", value1, value2, "code_value");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andCodeValueNotBetween(String value1, String value2) {
            addCriterion("code_value not between ", value1, value2, "code_value");
            return (CodeValueCriteria) this;
        }
        public CodeValueCriteria andStateIsNull() {
            addCriterion("state is null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateLike(String value) {
            addCriterion("state like ", value, "state", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotLike(String value) {
            addCriterion("state  not like ", value, "state", 1);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateLeftLike(String value) {
            addCriterion("state like ", value, "state", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotLeftLike(String value) {
            addCriterion("state  not like ", value, "state", 0);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateRightLike(String value) {
            addCriterion("state like ", value, "state", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotRightLike(String value) {
            addCriterion("state  not like ", value, "state", 2);
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateIn(List<String> values) {
            addCriterion("state  in ", values, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotIn(List<String> values) {
            addCriterion("state not in ", values, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateBetween(String value1, String value2) {
            addCriterion("state between ", value1, value2, "state");
            return (CodeValueCriteria) this;
        }

        public CodeValueCriteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between ", value1, value2, "state");
            return (CodeValueCriteria) this;
        }

    }

    public static class CodeValueCriteria extends GeneratedCriteria {

        protected CodeValueCriteria() {
            super();
        }
    }

    public static class CodeValueCriterion {
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

        protected CodeValueCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected CodeValueCriterion(String condition, Object value, int likeType) {
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

        protected CodeValueCriterion(String condition, Object value,
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

        protected CodeValueCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected CodeValueCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected CodeValueCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}