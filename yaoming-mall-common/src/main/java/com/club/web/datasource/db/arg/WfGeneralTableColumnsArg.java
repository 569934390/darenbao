package com.club.web.datasource.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class WfGeneralTableColumnsArg {
    private String pk_name = "column_id";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<WfGeneralTableColumnsCriteria> oredCriteria;

    public WfGeneralTableColumnsArg() {
        oredCriteria = new ArrayList<WfGeneralTableColumnsCriteria>();
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

    public List<WfGeneralTableColumnsCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfGeneralTableColumnsCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfGeneralTableColumnsCriteria or() {
    	WfGeneralTableColumnsCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfGeneralTableColumnsCriteria createCriteria() {
    	WfGeneralTableColumnsCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfGeneralTableColumnsCriteria createCriteriaInternal() {
    	WfGeneralTableColumnsCriteria criteria = new WfGeneralTableColumnsCriteria();
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
        protected List<WfGeneralTableColumnsCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfGeneralTableColumnsCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfGeneralTableColumnsCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfGeneralTableColumnsCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfGeneralTableColumnsCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableColumnsCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableColumnsCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableColumnsCriterion(condition, value1, value2));
        }

        public WfGeneralTableColumnsCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andTableIdIsNull() {
            addCriterion("table_id is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdIsNotNull() {
            addCriterion("table_id is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdEqualTo(Integer value) {
            addCriterion("table_id =", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotEqualTo(Integer value) {
            addCriterion("table_id <>", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdGreaterThan(Integer value) {
            addCriterion("table_id >", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("table_id >=", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdLessThan(Integer value) {
            addCriterion("table_id <", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdLessThanOrEqualTo(Integer value) {
            addCriterion("table_id <=", value, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdLeftLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotLeftLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdRightLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotRightLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdIn(List<Integer> values) {
            addCriterion("table_id  in ", values, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotIn(List<Integer> values) {
            addCriterion("table_id not in ", values, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdBetween(Integer value1, Integer value2) {
            addCriterion("table_id between ", value1, value2, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andTableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("table_id not between ", value1, value2, "table_id");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andColumnIdIsNull() {
            addCriterion("column_id is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdIsNotNull() {
            addCriterion("column_id is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdEqualTo(Integer value) {
            addCriterion("column_id =", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotEqualTo(Integer value) {
            addCriterion("column_id <>", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdGreaterThan(Integer value) {
            addCriterion("column_id >", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("column_id >=", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdLessThan(Integer value) {
            addCriterion("column_id <", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdLessThanOrEqualTo(Integer value) {
            addCriterion("column_id <=", value, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdLike(Integer value) {
            addCriterion("column_id like ", value, "column_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotLike(Integer value) {
            addCriterion("column_id  not like ", value, "column_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdLeftLike(Integer value) {
            addCriterion("column_id like ", value, "column_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotLeftLike(Integer value) {
            addCriterion("column_id  not like ", value, "column_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdRightLike(Integer value) {
            addCriterion("column_id like ", value, "column_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotRightLike(Integer value) {
            addCriterion("column_id  not like ", value, "column_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdIn(List<Integer> values) {
            addCriterion("column_id  in ", values, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotIn(List<Integer> values) {
            addCriterion("column_id not in ", values, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdBetween(Integer value1, Integer value2) {
            addCriterion("column_id between ", value1, value2, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIdNotBetween(Integer value1, Integer value2) {
            addCriterion("column_id not between ", value1, value2, "column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andGenColumnIdIsNull() {
            addCriterion("gen_column_id is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdIsNotNull() {
            addCriterion("gen_column_id is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdEqualTo(Integer value) {
            addCriterion("gen_column_id =", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotEqualTo(Integer value) {
            addCriterion("gen_column_id <>", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdGreaterThan(Integer value) {
            addCriterion("gen_column_id >", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("gen_column_id >=", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdLessThan(Integer value) {
            addCriterion("gen_column_id <", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdLessThanOrEqualTo(Integer value) {
            addCriterion("gen_column_id <=", value, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdLike(Integer value) {
            addCriterion("gen_column_id like ", value, "gen_column_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotLike(Integer value) {
            addCriterion("gen_column_id  not like ", value, "gen_column_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdLeftLike(Integer value) {
            addCriterion("gen_column_id like ", value, "gen_column_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotLeftLike(Integer value) {
            addCriterion("gen_column_id  not like ", value, "gen_column_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdRightLike(Integer value) {
            addCriterion("gen_column_id like ", value, "gen_column_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotRightLike(Integer value) {
            addCriterion("gen_column_id  not like ", value, "gen_column_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdIn(List<Integer> values) {
            addCriterion("gen_column_id  in ", values, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotIn(List<Integer> values) {
            addCriterion("gen_column_id not in ", values, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdBetween(Integer value1, Integer value2) {
            addCriterion("gen_column_id between ", value1, value2, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andGenColumnIdNotBetween(Integer value1, Integer value2) {
            addCriterion("gen_column_id not between ", value1, value2, "gen_column_id");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andDataSetIdIsNull() {
            addCriterion("data_set_id is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdIsNotNull() {
            addCriterion("data_set_id is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdEqualTo(Integer value) {
            addCriterion("data_set_id =", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotEqualTo(Integer value) {
            addCriterion("data_set_id <>", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdGreaterThan(Integer value) {
            addCriterion("data_set_id >", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_set_id >=", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdLessThan(Integer value) {
            addCriterion("data_set_id <", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdLessThanOrEqualTo(Integer value) {
            addCriterion("data_set_id <=", value, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdLeftLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotLeftLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdRightLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotRightLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdIn(List<Integer> values) {
            addCriterion("data_set_id  in ", values, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotIn(List<Integer> values) {
            addCriterion("data_set_id not in ", values, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id between ", value1, value2, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andDataSetIdNotBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id not between ", value1, value2, "data_set_id");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andColumnIndexIsNull() {
            addCriterion("column_index is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexIsNotNull() {
            addCriterion("column_index is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexEqualTo(String value) {
            addCriterion("column_index =", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotEqualTo(String value) {
            addCriterion("column_index <>", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexGreaterThan(String value) {
            addCriterion("column_index >", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexGreaterThanOrEqualTo(String value) {
            addCriterion("column_index >=", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexLessThan(String value) {
            addCriterion("column_index <", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexLessThanOrEqualTo(String value) {
            addCriterion("column_index <=", value, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexLike(String value) {
            addCriterion("column_index like ", value, "column_index", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotLike(String value) {
            addCriterion("column_index  not like ", value, "column_index", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexLeftLike(String value) {
            addCriterion("column_index like ", value, "column_index", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotLeftLike(String value) {
            addCriterion("column_index  not like ", value, "column_index", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexRightLike(String value) {
            addCriterion("column_index like ", value, "column_index", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotRightLike(String value) {
            addCriterion("column_index  not like ", value, "column_index", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexIn(List<String> values) {
            addCriterion("column_index  in ", values, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotIn(List<String> values) {
            addCriterion("column_index not in ", values, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexBetween(String value1, String value2) {
            addCriterion("column_index between ", value1, value2, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnIndexNotBetween(String value1, String value2) {
            addCriterion("column_index not between ", value1, value2, "column_index");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andColumnNameIsNull() {
            addCriterion("column_name is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameIsNotNull() {
            addCriterion("column_name is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameEqualTo(String value) {
            addCriterion("column_name =", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotEqualTo(String value) {
            addCriterion("column_name <>", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameGreaterThan(String value) {
            addCriterion("column_name >", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameGreaterThanOrEqualTo(String value) {
            addCriterion("column_name >=", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameLessThan(String value) {
            addCriterion("column_name <", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameLessThanOrEqualTo(String value) {
            addCriterion("column_name <=", value, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameLike(String value) {
            addCriterion("column_name like ", value, "column_name", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameLeftLike(String value) {
            addCriterion("column_name like ", value, "column_name", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotLeftLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameRightLike(String value) {
            addCriterion("column_name like ", value, "column_name", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotRightLike(String value) {
            addCriterion("column_name  not like ", value, "column_name", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameIn(List<String> values) {
            addCriterion("column_name  in ", values, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotIn(List<String> values) {
            addCriterion("column_name not in ", values, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameBetween(String value1, String value2) {
            addCriterion("column_name between ", value1, value2, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnNameNotBetween(String value1, String value2) {
            addCriterion("column_name not between ", value1, value2, "column_name");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andHiddenIsNull() {
            addCriterion("hidden is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenIsNotNull() {
            addCriterion("hidden is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenEqualTo(String value) {
            addCriterion("hidden =", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotEqualTo(String value) {
            addCriterion("hidden <>", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenGreaterThan(String value) {
            addCriterion("hidden >", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenGreaterThanOrEqualTo(String value) {
            addCriterion("hidden >=", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenLessThan(String value) {
            addCriterion("hidden <", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenLessThanOrEqualTo(String value) {
            addCriterion("hidden <=", value, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenLike(String value) {
            addCriterion("hidden like ", value, "hidden", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotLike(String value) {
            addCriterion("hidden  not like ", value, "hidden", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenLeftLike(String value) {
            addCriterion("hidden like ", value, "hidden", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotLeftLike(String value) {
            addCriterion("hidden  not like ", value, "hidden", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenRightLike(String value) {
            addCriterion("hidden like ", value, "hidden", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotRightLike(String value) {
            addCriterion("hidden  not like ", value, "hidden", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenIn(List<String> values) {
            addCriterion("hidden  in ", values, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotIn(List<String> values) {
            addCriterion("hidden not in ", values, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenBetween(String value1, String value2) {
            addCriterion("hidden between ", value1, value2, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andHiddenNotBetween(String value1, String value2) {
            addCriterion("hidden not between ", value1, value2, "hidden");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andWidthIsNull() {
            addCriterion("width is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthIsNotNull() {
            addCriterion("width is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthEqualTo(String value) {
            addCriterion("width =", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotEqualTo(String value) {
            addCriterion("width <>", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthGreaterThan(String value) {
            addCriterion("width >", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthGreaterThanOrEqualTo(String value) {
            addCriterion("width >=", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthLessThan(String value) {
            addCriterion("width <", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthLessThanOrEqualTo(String value) {
            addCriterion("width <=", value, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthLike(String value) {
            addCriterion("width like ", value, "width", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotLike(String value) {
            addCriterion("width  not like ", value, "width", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthLeftLike(String value) {
            addCriterion("width like ", value, "width", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotLeftLike(String value) {
            addCriterion("width  not like ", value, "width", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthRightLike(String value) {
            addCriterion("width like ", value, "width", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotRightLike(String value) {
            addCriterion("width  not like ", value, "width", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthIn(List<String> values) {
            addCriterion("width  in ", values, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotIn(List<String> values) {
            addCriterion("width not in ", values, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthBetween(String value1, String value2) {
            addCriterion("width between ", value1, value2, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andWidthNotBetween(String value1, String value2) {
            addCriterion("width not between ", value1, value2, "width");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andRenderIsNull() {
            addCriterion("render is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderIsNotNull() {
            addCriterion("render is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderEqualTo(String value) {
            addCriterion("render =", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotEqualTo(String value) {
            addCriterion("render <>", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderGreaterThan(String value) {
            addCriterion("render >", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderGreaterThanOrEqualTo(String value) {
            addCriterion("render >=", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderLessThan(String value) {
            addCriterion("render <", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderLessThanOrEqualTo(String value) {
            addCriterion("render <=", value, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderLike(String value) {
            addCriterion("render like ", value, "render", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotLike(String value) {
            addCriterion("render  not like ", value, "render", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderLeftLike(String value) {
            addCriterion("render like ", value, "render", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotLeftLike(String value) {
            addCriterion("render  not like ", value, "render", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderRightLike(String value) {
            addCriterion("render like ", value, "render", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotRightLike(String value) {
            addCriterion("render  not like ", value, "render", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderIn(List<String> values) {
            addCriterion("render  in ", values, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotIn(List<String> values) {
            addCriterion("render not in ", values, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderBetween(String value1, String value2) {
            addCriterion("render between ", value1, value2, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andRenderNotBetween(String value1, String value2) {
            addCriterion("render not between ", value1, value2, "render");
            return (WfGeneralTableColumnsCriteria) this;
        }
        public WfGeneralTableColumnsCriteria andColumnOrderIsNull() {
            addCriterion("column_order is null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderIsNotNull() {
            addCriterion("column_order is not null");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderEqualTo(Integer value) {
            addCriterion("column_order =", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotEqualTo(Integer value) {
            addCriterion("column_order <>", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderGreaterThan(Integer value) {
            addCriterion("column_order >", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("column_order >=", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderLessThan(Integer value) {
            addCriterion("column_order <", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderLessThanOrEqualTo(Integer value) {
            addCriterion("column_order <=", value, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderLike(Integer value) {
            addCriterion("column_order like ", value, "column_order", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotLike(Integer value) {
            addCriterion("column_order  not like ", value, "column_order", 1);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderLeftLike(Integer value) {
            addCriterion("column_order like ", value, "column_order", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotLeftLike(Integer value) {
            addCriterion("column_order  not like ", value, "column_order", 0);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderRightLike(Integer value) {
            addCriterion("column_order like ", value, "column_order", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotRightLike(Integer value) {
            addCriterion("column_order  not like ", value, "column_order", 2);
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderIn(List<Integer> values) {
            addCriterion("column_order  in ", values, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotIn(List<Integer> values) {
            addCriterion("column_order not in ", values, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderBetween(Integer value1, Integer value2) {
            addCriterion("column_order between ", value1, value2, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

        public WfGeneralTableColumnsCriteria andColumnOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("column_order not between ", value1, value2, "column_order");
            return (WfGeneralTableColumnsCriteria) this;
        }

    }

    public static class WfGeneralTableColumnsCriteria extends GeneratedCriteria {

        protected WfGeneralTableColumnsCriteria() {
            super();
        }
    }

    public static class WfGeneralTableColumnsCriterion {
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

        protected WfGeneralTableColumnsCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfGeneralTableColumnsCriterion(String condition, Object value, int likeType) {
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

        protected WfGeneralTableColumnsCriterion(String condition, Object value,
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

        protected WfGeneralTableColumnsCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfGeneralTableColumnsCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfGeneralTableColumnsCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}