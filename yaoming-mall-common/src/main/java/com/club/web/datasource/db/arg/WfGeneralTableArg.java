package com.club.web.datasource.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class WfGeneralTableArg {
    private String pk_name = "table_id";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private List<WfGeneralTableCriteria> oredCriteria;

    public WfGeneralTableArg() {
        oredCriteria = new ArrayList<WfGeneralTableCriteria>();
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

    public List<WfGeneralTableCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfGeneralTableCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfGeneralTableCriteria or() {
    	WfGeneralTableCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfGeneralTableCriteria createCriteria() {
    	WfGeneralTableCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfGeneralTableCriteria createCriteriaInternal() {
    	WfGeneralTableCriteria criteria = new WfGeneralTableCriteria();
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
        protected List<WfGeneralTableCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfGeneralTableCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfGeneralTableCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfGeneralTableCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfGeneralTableCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfGeneralTableCriterion(condition, value1, value2));
        }

        public WfGeneralTableCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andTableIdIsNull() {
            addCriterion("table_id is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdIsNotNull() {
            addCriterion("table_id is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdEqualTo(Integer value) {
            addCriterion("table_id =", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotEqualTo(Integer value) {
            addCriterion("table_id <>", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdGreaterThan(Integer value) {
            addCriterion("table_id >", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("table_id >=", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdLessThan(Integer value) {
            addCriterion("table_id <", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdLessThanOrEqualTo(Integer value) {
            addCriterion("table_id <=", value, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdLeftLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotLeftLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdRightLike(Integer value) {
            addCriterion("table_id like ", value, "table_id", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotRightLike(Integer value) {
            addCriterion("table_id  not like ", value, "table_id", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdIn(List<Integer> values) {
            addCriterion("table_id  in ", values, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotIn(List<Integer> values) {
            addCriterion("table_id not in ", values, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdBetween(Integer value1, Integer value2) {
            addCriterion("table_id between ", value1, value2, "table_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("table_id not between ", value1, value2, "table_id");
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameLike(String value) {
            addCriterion("table_name like ", value, "table_name", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameLeftLike(String value) {
            addCriterion("table_name like ", value, "table_name", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotLeftLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameRightLike(String value) {
            addCriterion("table_name like ", value, "table_name", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotRightLike(String value) {
            addCriterion("table_name  not like ", value, "table_name", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameIn(List<String> values) {
            addCriterion("table_name  in ", values, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in ", values, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between ", value1, value2, "table_name");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between ", value1, value2, "table_name");
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andIsPageIsNull() {
            addCriterion("is_page is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageIsNotNull() {
            addCriterion("is_page is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageEqualTo(String value) {
            addCriterion("is_page =", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotEqualTo(String value) {
            addCriterion("is_page <>", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageGreaterThan(String value) {
            addCriterion("is_page >", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageGreaterThanOrEqualTo(String value) {
            addCriterion("is_page >=", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageLessThan(String value) {
            addCriterion("is_page <", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageLessThanOrEqualTo(String value) {
            addCriterion("is_page <=", value, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageLike(String value) {
            addCriterion("is_page like ", value, "is_page", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotLike(String value) {
            addCriterion("is_page  not like ", value, "is_page", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageLeftLike(String value) {
            addCriterion("is_page like ", value, "is_page", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotLeftLike(String value) {
            addCriterion("is_page  not like ", value, "is_page", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageRightLike(String value) {
            addCriterion("is_page like ", value, "is_page", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotRightLike(String value) {
            addCriterion("is_page  not like ", value, "is_page", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageIn(List<String> values) {
            addCriterion("is_page  in ", values, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotIn(List<String> values) {
            addCriterion("is_page not in ", values, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageBetween(String value1, String value2) {
            addCriterion("is_page between ", value1, value2, "is_page");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsPageNotBetween(String value1, String value2) {
            addCriterion("is_page not between ", value1, value2, "is_page");
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andIsCheckboxIsNull() {
            addCriterion("is_checkbox is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxIsNotNull() {
            addCriterion("is_checkbox is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxEqualTo(String value) {
            addCriterion("is_checkbox =", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotEqualTo(String value) {
            addCriterion("is_checkbox <>", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxGreaterThan(String value) {
            addCriterion("is_checkbox >", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxGreaterThanOrEqualTo(String value) {
            addCriterion("is_checkbox >=", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxLessThan(String value) {
            addCriterion("is_checkbox <", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxLessThanOrEqualTo(String value) {
            addCriterion("is_checkbox <=", value, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxLike(String value) {
            addCriterion("is_checkbox like ", value, "is_checkbox", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotLike(String value) {
            addCriterion("is_checkbox  not like ", value, "is_checkbox", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxLeftLike(String value) {
            addCriterion("is_checkbox like ", value, "is_checkbox", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotLeftLike(String value) {
            addCriterion("is_checkbox  not like ", value, "is_checkbox", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxRightLike(String value) {
            addCriterion("is_checkbox like ", value, "is_checkbox", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotRightLike(String value) {
            addCriterion("is_checkbox  not like ", value, "is_checkbox", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxIn(List<String> values) {
            addCriterion("is_checkbox  in ", values, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotIn(List<String> values) {
            addCriterion("is_checkbox not in ", values, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxBetween(String value1, String value2) {
            addCriterion("is_checkbox between ", value1, value2, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andIsCheckboxNotBetween(String value1, String value2) {
            addCriterion("is_checkbox not between ", value1, value2, "is_checkbox");
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andExtJsonIsNull() {
            addCriterion("ext_json is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonIsNotNull() {
            addCriterion("ext_json is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonEqualTo(String value) {
            addCriterion("ext_json =", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotEqualTo(String value) {
            addCriterion("ext_json <>", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonGreaterThan(String value) {
            addCriterion("ext_json >", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonGreaterThanOrEqualTo(String value) {
            addCriterion("ext_json >=", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonLessThan(String value) {
            addCriterion("ext_json <", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonLessThanOrEqualTo(String value) {
            addCriterion("ext_json <=", value, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonLike(String value) {
            addCriterion("ext_json like ", value, "ext_json", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotLike(String value) {
            addCriterion("ext_json  not like ", value, "ext_json", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonLeftLike(String value) {
            addCriterion("ext_json like ", value, "ext_json", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotLeftLike(String value) {
            addCriterion("ext_json  not like ", value, "ext_json", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonRightLike(String value) {
            addCriterion("ext_json like ", value, "ext_json", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotRightLike(String value) {
            addCriterion("ext_json  not like ", value, "ext_json", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonIn(List<String> values) {
            addCriterion("ext_json  in ", values, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotIn(List<String> values) {
            addCriterion("ext_json not in ", values, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonBetween(String value1, String value2) {
            addCriterion("ext_json between ", value1, value2, "ext_json");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andExtJsonNotBetween(String value1, String value2) {
            addCriterion("ext_json not between ", value1, value2, "ext_json");
            return (WfGeneralTableCriteria) this;
        }
        public WfGeneralTableCriteria andDataSetIdIsNull() {
            addCriterion("data_set_id is null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdIsNotNull() {
            addCriterion("data_set_id is not null");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdEqualTo(Integer value) {
            addCriterion("data_set_id =", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotEqualTo(Integer value) {
            addCriterion("data_set_id <>", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdGreaterThan(Integer value) {
            addCriterion("data_set_id >", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_set_id >=", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdLessThan(Integer value) {
            addCriterion("data_set_id <", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdLessThanOrEqualTo(Integer value) {
            addCriterion("data_set_id <=", value, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 1);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdLeftLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotLeftLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 0);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdRightLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotRightLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 2);
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdIn(List<Integer> values) {
            addCriterion("data_set_id  in ", values, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotIn(List<Integer> values) {
            addCriterion("data_set_id not in ", values, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id between ", value1, value2, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

        public WfGeneralTableCriteria andDataSetIdNotBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id not between ", value1, value2, "data_set_id");
            return (WfGeneralTableCriteria) this;
        }

    }

    public static class WfGeneralTableCriteria extends GeneratedCriteria {

        protected WfGeneralTableCriteria() {
            super();
        }
    }

    public static class WfGeneralTableCriterion {
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

        protected WfGeneralTableCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfGeneralTableCriterion(String condition, Object value, int likeType) {
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

        protected WfGeneralTableCriterion(String condition, Object value,
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

        protected WfGeneralTableCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfGeneralTableCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfGeneralTableCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}