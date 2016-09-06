package com.club.web.common.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class WfDbColumnsArg {
    private String pk_name = "table_name";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<WfDbColumnsCriteria> oredCriteria;

    public WfDbColumnsArg() {
        oredCriteria = new ArrayList<WfDbColumnsCriteria>();
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

    public List<WfDbColumnsCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfDbColumnsCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfDbColumnsCriteria or() {
    	WfDbColumnsCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfDbColumnsCriteria createCriteria() {
    	WfDbColumnsCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfDbColumnsCriteria createCriteriaInternal() {
    	WfDbColumnsCriteria criteria = new WfDbColumnsCriteria();
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
        protected List<WfDbColumnsCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfDbColumnsCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfDbColumnsCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfDbColumnsCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfDbColumnsCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbColumnsCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbColumnsCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDbColumnsCriterion(condition, value1, value2));
        }

        public WfDbColumnsCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameLike(String value) {
            addCriterion("table_name like ", value, "table_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameLeftLike(String value) {
            addCriterion("table_name like ", value, "table_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotLeftLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameRightLike(String value) {
            addCriterion("table_name like ", value, "table_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotRightLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameIn(List<String> values) {
            addCriterion("table_name  in ", values, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in ", values, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between ", value1, value2, "table_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between ", value1, value2, "table_name");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andColumnNameIsNull() {
            addCriterion("column_name is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameIsNotNull() {
            addCriterion("column_name is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameEqualTo(String value) {
            addCriterion("column_name =", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotEqualTo(String value) {
            addCriterion("column_name <>", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameGreaterThan(String value) {
            addCriterion("column_name >", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameGreaterThanOrEqualTo(String value) {
            addCriterion("column_name >=", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameLessThan(String value) {
            addCriterion("column_name <", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameLessThanOrEqualTo(String value) {
            addCriterion("column_name <=", value, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameLike(String value) {
            addCriterion("column_name like ", value, "column_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameLeftLike(String value) {
            addCriterion("column_name like ", value, "column_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotLeftLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameRightLike(String value) {
            addCriterion("column_name like ", value, "column_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotRightLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameIn(List<String> values) {
            addCriterion("column_name  in ", values, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotIn(List<String> values) {
            addCriterion("column_name not in ", values, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameBetween(String value1, String value2) {
            addCriterion("column_name between ", value1, value2, "column_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andColumnNameNotBetween(String value1, String value2) {
            addCriterion("column_name not between ", value1, value2, "column_name");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andDisplayNameIsNull() {
            addCriterion("display_name is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameIsNotNull() {
            addCriterion("display_name is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameEqualTo(String value) {
            addCriterion("display_name =", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotEqualTo(String value) {
            addCriterion("display_name <>", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameGreaterThan(String value) {
            addCriterion("display_name >", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameGreaterThanOrEqualTo(String value) {
            addCriterion("display_name >=", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameLessThan(String value) {
            addCriterion("display_name <", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameLessThanOrEqualTo(String value) {
            addCriterion("display_name <=", value, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameLike(String value) {
            addCriterion("display_name like ", value, "display_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameLeftLike(String value) {
            addCriterion("display_name like ", value, "display_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotLeftLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameRightLike(String value) {
            addCriterion("display_name like ", value, "display_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotRightLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameIn(List<String> values) {
            addCriterion("display_name  in ", values, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotIn(List<String> values) {
            addCriterion("display_name not in ", values, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameBetween(String value1, String value2) {
            addCriterion("display_name between ", value1, value2, "display_name");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDisplayNameNotBetween(String value1, String value2) {
            addCriterion("display_name not between ", value1, value2, "display_name");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andDbTypeIsNull() {
            addCriterion("db_type is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeIsNotNull() {
            addCriterion("db_type is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeEqualTo(String value) {
            addCriterion("db_type =", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotEqualTo(String value) {
            addCriterion("db_type <>", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeGreaterThan(String value) {
            addCriterion("db_type >", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeGreaterThanOrEqualTo(String value) {
            addCriterion("db_type >=", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeLessThan(String value) {
            addCriterion("db_type <", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeLessThanOrEqualTo(String value) {
            addCriterion("db_type <=", value, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeLike(String value) {
            addCriterion("db_type like ", value, "db_type", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotLike(String value) {
            addCriterion("db_type  not like ", value, "db_type", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeLeftLike(String value) {
            addCriterion("db_type like ", value, "db_type", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotLeftLike(String value) {
            addCriterion("db_type  not like ", value, "db_type", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeRightLike(String value) {
            addCriterion("db_type like ", value, "db_type", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotRightLike(String value) {
            addCriterion("db_type  not like ", value, "db_type", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeIn(List<String> values) {
            addCriterion("db_type  in ", values, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotIn(List<String> values) {
            addCriterion("db_type not in ", values, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeBetween(String value1, String value2) {
            addCriterion("db_type between ", value1, value2, "db_type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDbTypeNotBetween(String value1, String value2) {
            addCriterion("db_type not between ", value1, value2, "db_type");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andTypeIsNull() {
            addCriterion("type is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeLike(String value) {
            addCriterion("type like ", value, "type", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotLike(String value) {
            addCriterion("type  not like ", value, "type", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeLeftLike(String value) {
            addCriterion("type like ", value, "type", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotLeftLike(String value) {
            addCriterion("type  not like ", value, "type", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeRightLike(String value) {
            addCriterion("type like ", value, "type", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotRightLike(String value) {
            addCriterion("type  not like ", value, "type", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeIn(List<String> values) {
            addCriterion("type  in ", values, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotIn(List<String> values) {
            addCriterion("type not in ", values, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeBetween(String value1, String value2) {
            addCriterion("type between ", value1, value2, "type");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between ", value1, value2, "type");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andIsNullIsNull() {
            addCriterion("is_null is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullIsNotNull() {
            addCriterion("is_null is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullEqualTo(String value) {
            addCriterion("is_null =", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotEqualTo(String value) {
            addCriterion("is_null <>", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullGreaterThan(String value) {
            addCriterion("is_null >", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullGreaterThanOrEqualTo(String value) {
            addCriterion("is_null >=", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullLessThan(String value) {
            addCriterion("is_null <", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullLessThanOrEqualTo(String value) {
            addCriterion("is_null <=", value, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullLike(String value) {
            addCriterion("is_null like ", value, "is_null", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotLike(String value) {
            addCriterion("is_null  not like ", value, "is_null", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullLeftLike(String value) {
            addCriterion("is_null like ", value, "is_null", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotLeftLike(String value) {
            addCriterion("is_null  not like ", value, "is_null", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullRightLike(String value) {
            addCriterion("is_null like ", value, "is_null", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotRightLike(String value) {
            addCriterion("is_null  not like ", value, "is_null", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullIn(List<String> values) {
            addCriterion("is_null  in ", values, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotIn(List<String> values) {
            addCriterion("is_null not in ", values, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullBetween(String value1, String value2) {
            addCriterion("is_null between ", value1, value2, "is_null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andIsNullNotBetween(String value1, String value2) {
            addCriterion("is_null not between ", value1, value2, "is_null");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andDefaultValueIsNull() {
            addCriterion("default_value is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueIsNotNull() {
            addCriterion("default_value is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueEqualTo(String value) {
            addCriterion("default_value =", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotEqualTo(String value) {
            addCriterion("default_value <>", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueGreaterThan(String value) {
            addCriterion("default_value >", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueGreaterThanOrEqualTo(String value) {
            addCriterion("default_value >=", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueLessThan(String value) {
            addCriterion("default_value <", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueLessThanOrEqualTo(String value) {
            addCriterion("default_value <=", value, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueLike(String value) {
            addCriterion("default_value like ", value, "default_value", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotLike(String value) {
            addCriterion("default_value  not like ", value, "default_value", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueLeftLike(String value) {
            addCriterion("default_value like ", value, "default_value", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotLeftLike(String value) {
            addCriterion("default_value  not like ", value, "default_value", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueRightLike(String value) {
            addCriterion("default_value like ", value, "default_value", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotRightLike(String value) {
            addCriterion("default_value  not like ", value, "default_value", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueIn(List<String> values) {
            addCriterion("default_value  in ", values, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotIn(List<String> values) {
            addCriterion("default_value not in ", values, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueBetween(String value1, String value2) {
            addCriterion("default_value between ", value1, value2, "default_value");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andDefaultValueNotBetween(String value1, String value2) {
            addCriterion("default_value not between ", value1, value2, "default_value");
            return (WfDbColumnsCriteria) this;
        }
        public WfDbColumnsCriteria andLengthIsNull() {
            addCriterion("length is null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthIsNotNull() {
            addCriterion("length is not null");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthEqualTo(Integer value) {
            addCriterion("length =", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotEqualTo(Integer value) {
            addCriterion("length <>", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthGreaterThan(Integer value) {
            addCriterion("length >", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("length >=", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthLessThan(Integer value) {
            addCriterion("length <", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthLessThanOrEqualTo(Integer value) {
            addCriterion("length <=", value, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthLike(Integer value) {
            addCriterion("length like ", value, "length", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotLike(Integer value) {
            addCriterion("length  not like ", value, "length", 1);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthLeftLike(Integer value) {
            addCriterion("length like ", value, "length", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotLeftLike(Integer value) {
            addCriterion("length  not like ", value, "length", 0);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthRightLike(Integer value) {
            addCriterion("length like ", value, "length", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotRightLike(Integer value) {
            addCriterion("length  not like ", value, "length", 2);
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthIn(List<Integer> values) {
            addCriterion("length  in ", values, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotIn(List<Integer> values) {
            addCriterion("length not in ", values, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthBetween(Integer value1, Integer value2) {
            addCriterion("length between ", value1, value2, "length");
            return (WfDbColumnsCriteria) this;
        }

        public WfDbColumnsCriteria andLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("length not between ", value1, value2, "length");
            return (WfDbColumnsCriteria) this;
        }

    }

    public static class WfDbColumnsCriteria extends GeneratedCriteria {

        protected WfDbColumnsCriteria() {
            super();
        }
    }

    public static class WfDbColumnsCriterion {
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

        protected WfDbColumnsCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfDbColumnsCriterion(String condition, Object value, int likeType) {
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

        protected WfDbColumnsCriterion(String condition, Object value,
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

        protected WfDbColumnsCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfDbColumnsCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfDbColumnsCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}