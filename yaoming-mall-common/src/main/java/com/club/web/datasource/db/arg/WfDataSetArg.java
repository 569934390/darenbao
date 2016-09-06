package com.club.web.datasource.db.arg;

import java.util.*;
import java.math.*;
import org.apache.commons.lang.*;

public class WfDataSetArg {
    private String pk_name = "data_set_id";

    private String orderByClause;

    private String groupByClause;

    private String columns;

    private String countsql1;

    private String countsql2;

    private boolean distinct;

    private int rowStart = 0;

    private int rowEnd = 10;

    private String conditionFlag1 ;

    public String getConditionFlag2() {
        return conditionFlag2;
    }

    public void setConditionFlag2(String conditionFlag2) {
        this.conditionFlag2 = conditionFlag2;
    }

    private String conditionFlag2 ;
    private String paramName;
    private String paramDisplayName;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDisplayName() {
        return paramDisplayName;
    }

    public void setParamDisplayName(String paramDisplayName) {
        this.paramDisplayName = paramDisplayName;
    }

    public String getConditionFlag1() {
        return conditionFlag1;
    }

    public void setConditionFlag1(String conditionFlag1) {
        this.conditionFlag1 = conditionFlag1;
    }

    private List<WfDataSetCriteria> oredCriteria;

    public WfDataSetArg() {
        oredCriteria = new ArrayList<WfDataSetCriteria>();
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

    public List<WfDataSetCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(WfDataSetCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public WfDataSetCriteria or() {
    	WfDataSetCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public WfDataSetCriteria createCriteria() {
    	WfDataSetCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected WfDataSetCriteria createCriteriaInternal() {
    	WfDataSetCriteria criteria = new WfDataSetCriteria();
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
        protected List<WfDataSetCriterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<WfDataSetCriterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<WfDataSetCriterion> getAllCriteria() {
            return criteria;
        }

        public List<WfDataSetCriterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new WfDataSetCriterion(condition));
        }

        protected void addCriterion(String condition, Object value,
                String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDataSetCriterion(condition, value));
        }

        protected void addCriterion(String condition, Object value,
                String property, int likeType) {
            if (value == null) {
                throw new RuntimeException("Value for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDataSetCriterion(condition, value, likeType));
        }

        protected void addCriterion(String condition, Object value1,
                Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property
                        + " cannot be null");
            }
            criteria.add(new WfDataSetCriterion(condition, value1, value2));
        }

        public WfDataSetCriteria andCriterionEqualTo(String criterion) {
            if (StringUtils.isBlank(criterion)) {
                criterion = "1=1";
            }
            addCriterion(criterion);
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andDataSetIdIsNull() {
            addCriterion("data_set_id is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdIsNotNull() {
            addCriterion("data_set_id is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdEqualTo(Integer value) {
            addCriterion("data_set_id =", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotEqualTo(Integer value) {
            addCriterion("data_set_id <>", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdGreaterThan(Integer value) {
            addCriterion("data_set_id >", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_set_id >=", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdLessThan(Integer value) {
            addCriterion("data_set_id <", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdLessThanOrEqualTo(Integer value) {
            addCriterion("data_set_id <=", value, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdLeftLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotLeftLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdRightLike(Integer value) {
            addCriterion("data_set_id like ", value, "data_set_id", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotRightLike(Integer value) {
            addCriterion("data_set_id  not like ", value, "data_set_id", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdIn(List<Integer> values) {
            addCriterion("data_set_id  in ", values, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotIn(List<Integer> values) {
            addCriterion("data_set_id not in ", values, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id between ", value1, value2, "data_set_id");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetIdNotBetween(Integer value1, Integer value2) {
            addCriterion("data_set_id not between ", value1, value2, "data_set_id");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andDataSetTypeIsNull() {
            addCriterion("data_set_type is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeIsNotNull() {
            addCriterion("data_set_type is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeEqualTo(String value) {
            addCriterion("data_set_type =", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotEqualTo(String value) {
            addCriterion("data_set_type <>", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeGreaterThan(String value) {
            addCriterion("data_set_type >", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_set_type >=", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeLessThan(String value) {
            addCriterion("data_set_type <", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeLessThanOrEqualTo(String value) {
            addCriterion("data_set_type <=", value, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeLike(String value) {
            addCriterion("data_set_type like ", value, "data_set_type", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotLike(String value) {
            addCriterion("data_set_type  not like ", value, "data_set_type", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeLeftLike(String value) {
            addCriterion("data_set_type like ", value, "data_set_type", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotLeftLike(String value) {
            addCriterion("data_set_type  not like ", value, "data_set_type", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeRightLike(String value) {
            addCriterion("data_set_type like ", value, "data_set_type", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotRightLike(String value) {
            addCriterion("data_set_type  not like ", value, "data_set_type", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeIn(List<String> values) {
            addCriterion("data_set_type  in ", values, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotIn(List<String> values) {
            addCriterion("data_set_type not in ", values, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeBetween(String value1, String value2) {
            addCriterion("data_set_type between ", value1, value2, "data_set_type");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataSetTypeNotBetween(String value1, String value2) {
            addCriterion("data_set_type not between ", value1, value2, "data_set_type");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andDataUrlIsNull() {
            addCriterion("data_url is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlIsNotNull() {
            addCriterion("data_url is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlEqualTo(String value) {
            addCriterion("data_url =", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotEqualTo(String value) {
            addCriterion("data_url <>", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlGreaterThan(String value) {
            addCriterion("data_url >", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlGreaterThanOrEqualTo(String value) {
            addCriterion("data_url >=", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlLessThan(String value) {
            addCriterion("data_url <", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlLessThanOrEqualTo(String value) {
            addCriterion("data_url <=", value, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlLike(String value) {
            addCriterion("data_url like ", value, "data_url", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotLike(String value) {
            addCriterion("data_url  not like ", value, "data_url", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlLeftLike(String value) {
            addCriterion("data_url like ", value, "data_url", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotLeftLike(String value) {
            addCriterion("data_url  not like ", value, "data_url", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlRightLike(String value) {
            addCriterion("data_url like ", value, "data_url", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotRightLike(String value) {
            addCriterion("data_url  not like ", value, "data_url", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlIn(List<String> values) {
            addCriterion("data_url  in ", values, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotIn(List<String> values) {
            addCriterion("data_url not in ", values, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlBetween(String value1, String value2) {
            addCriterion("data_url between ", value1, value2, "data_url");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDataUrlNotBetween(String value1, String value2) {
            addCriterion("data_url not between ", value1, value2, "data_url");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andNameIsNull() {
            addCriterion("name is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameLike(String value) {
            addCriterion("name like ", value, "name", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotLike(String value) {
            addCriterion("name  not like ", value, "name", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameLeftLike(String value) {
            addCriterion("name like ", value, "name", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotLeftLike(String value) {
            addCriterion("name  not like ", value, "name", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameRightLike(String value) {
            addCriterion("name like ", value, "name", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotRightLike(String value) {
            addCriterion("name  not like ", value, "name", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameIn(List<String> values) {
            addCriterion("name  in ", values, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotIn(List<String> values) {
            addCriterion("name not in ", values, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameBetween(String value1, String value2) {
            addCriterion("name between ", value1, value2, "name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between ", value1, value2, "name");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andDisplayNameIsNull() {
            addCriterion("display_name is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameIsNotNull() {
            addCriterion("display_name is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameEqualTo(String value) {
            addCriterion("display_name =", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotEqualTo(String value) {
            addCriterion("display_name <>", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameGreaterThan(String value) {
            addCriterion("display_name >", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameGreaterThanOrEqualTo(String value) {
            addCriterion("display_name >=", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameLessThan(String value) {
            addCriterion("display_name <", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameLessThanOrEqualTo(String value) {
            addCriterion("display_name <=", value, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameLike(String value) {
            addCriterion("display_name like ", value, "display_name", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameLeftLike(String value) {
            addCriterion("display_name like ", value, "display_name", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotLeftLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameRightLike(String value) {
            addCriterion("display_name like ", value, "display_name", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotRightLike(String value) {
            addCriterion("display_name  not like ", value, "display_name", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameIn(List<String> values) {
            addCriterion("display_name  in ", values, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotIn(List<String> values) {
            addCriterion("display_name not in ", values, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameBetween(String value1, String value2) {
            addCriterion("display_name between ", value1, value2, "display_name");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayNameNotBetween(String value1, String value2) {
            addCriterion("display_name not between ", value1, value2, "display_name");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andTableGroupIsNull() {
            addCriterion("table_group is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupIsNotNull() {
            addCriterion("table_group is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupEqualTo(String value) {
            addCriterion("table_group =", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotEqualTo(String value) {
            addCriterion("table_group <>", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupGreaterThan(String value) {
            addCriterion("table_group >", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupGreaterThanOrEqualTo(String value) {
            addCriterion("table_group >=", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupLessThan(String value) {
            addCriterion("table_group <", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupLessThanOrEqualTo(String value) {
            addCriterion("table_group <=", value, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupLike(String value) {
            addCriterion("table_group like ", value, "table_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotLike(String value) {
            addCriterion("table_group  not like ", value, "table_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupLeftLike(String value) {
            addCriterion("table_group like ", value, "table_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotLeftLike(String value) {
            addCriterion("table_group  not like ", value, "table_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupRightLike(String value) {
            addCriterion("table_group like ", value, "table_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotRightLike(String value) {
            addCriterion("table_group  not like ", value, "table_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupIn(List<String> values) {
            addCriterion("table_group  in ", values, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotIn(List<String> values) {
            addCriterion("table_group not in ", values, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupBetween(String value1, String value2) {
            addCriterion("table_group between ", value1, value2, "table_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andTableGroupNotBetween(String value1, String value2) {
            addCriterion("table_group not between ", value1, value2, "table_group");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andJoinGroupIsNull() {
            addCriterion("join_group is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupIsNotNull() {
            addCriterion("join_group is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupEqualTo(String value) {
            addCriterion("join_group =", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotEqualTo(String value) {
            addCriterion("join_group <>", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupGreaterThan(String value) {
            addCriterion("join_group >", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupGreaterThanOrEqualTo(String value) {
            addCriterion("join_group >=", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupLessThan(String value) {
            addCriterion("join_group <", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupLessThanOrEqualTo(String value) {
            addCriterion("join_group <=", value, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupLike(String value) {
            addCriterion("join_group like ", value, "join_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotLike(String value) {
            addCriterion("join_group  not like ", value, "join_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupLeftLike(String value) {
            addCriterion("join_group like ", value, "join_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotLeftLike(String value) {
            addCriterion("join_group  not like ", value, "join_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupRightLike(String value) {
            addCriterion("join_group like ", value, "join_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotRightLike(String value) {
            addCriterion("join_group  not like ", value, "join_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupIn(List<String> values) {
            addCriterion("join_group  in ", values, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotIn(List<String> values) {
            addCriterion("join_group not in ", values, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupBetween(String value1, String value2) {
            addCriterion("join_group between ", value1, value2, "join_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andJoinGroupNotBetween(String value1, String value2) {
            addCriterion("join_group not between ", value1, value2, "join_group");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andOnGroupIsNull() {
            addCriterion("on_group is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupIsNotNull() {
            addCriterion("on_group is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupEqualTo(String value) {
            addCriterion("on_group =", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotEqualTo(String value) {
            addCriterion("on_group <>", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupGreaterThan(String value) {
            addCriterion("on_group >", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupGreaterThanOrEqualTo(String value) {
            addCriterion("on_group >=", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupLessThan(String value) {
            addCriterion("on_group <", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupLessThanOrEqualTo(String value) {
            addCriterion("on_group <=", value, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupLike(String value) {
            addCriterion("on_group like ", value, "on_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotLike(String value) {
            addCriterion("on_group  not like ", value, "on_group", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupLeftLike(String value) {
            addCriterion("on_group like ", value, "on_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotLeftLike(String value) {
            addCriterion("on_group  not like ", value, "on_group", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupRightLike(String value) {
            addCriterion("on_group like ", value, "on_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotRightLike(String value) {
            addCriterion("on_group  not like ", value, "on_group", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupIn(List<String> values) {
            addCriterion("on_group  in ", values, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotIn(List<String> values) {
            addCriterion("on_group not in ", values, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupBetween(String value1, String value2) {
            addCriterion("on_group between ", value1, value2, "on_group");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andOnGroupNotBetween(String value1, String value2) {
            addCriterion("on_group not between ", value1, value2, "on_group");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andWhereParamsIsNull() {
            addCriterion("where_params is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsIsNotNull() {
            addCriterion("where_params is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsEqualTo(String value) {
            addCriterion("where_params =", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotEqualTo(String value) {
            addCriterion("where_params <>", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsGreaterThan(String value) {
            addCriterion("where_params >", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsGreaterThanOrEqualTo(String value) {
            addCriterion("where_params >=", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsLessThan(String value) {
            addCriterion("where_params <", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsLessThanOrEqualTo(String value) {
            addCriterion("where_params <=", value, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsLike(String value) {
            addCriterion("where_params like ", value, "where_params", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotLike(String value) {
            addCriterion("where_params  not like ", value, "where_params", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsLeftLike(String value) {
            addCriterion("where_params like ", value, "where_params", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotLeftLike(String value) {
            addCriterion("where_params  not like ", value, "where_params", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsRightLike(String value) {
            addCriterion("where_params like ", value, "where_params", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotRightLike(String value) {
            addCriterion("where_params  not like ", value, "where_params", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsIn(List<String> values) {
            addCriterion("where_params  in ", values, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotIn(List<String> values) {
            addCriterion("where_params not in ", values, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsBetween(String value1, String value2) {
            addCriterion("where_params between ", value1, value2, "where_params");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andWhereParamsNotBetween(String value1, String value2) {
            addCriterion("where_params not between ", value1, value2, "where_params");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andColumnsIsNull() {
            addCriterion("columns is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsIsNotNull() {
            addCriterion("columns is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsEqualTo(String value) {
            addCriterion("columns =", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotEqualTo(String value) {
            addCriterion("columns <>", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsGreaterThan(String value) {
            addCriterion("columns >", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsGreaterThanOrEqualTo(String value) {
            addCriterion("columns >=", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsLessThan(String value) {
            addCriterion("columns <", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsLessThanOrEqualTo(String value) {
            addCriterion("columns <=", value, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsLike(String value) {
            addCriterion("columns like ", value, "columns", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotLike(String value) {
            addCriterion("columns  not like ", value, "columns", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsLeftLike(String value) {
            addCriterion("columns like ", value, "columns", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotLeftLike(String value) {
            addCriterion("columns  not like ", value, "columns", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsRightLike(String value) {
            addCriterion("columns like ", value, "columns", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotRightLike(String value) {
            addCriterion("columns  not like ", value, "columns", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsIn(List<String> values) {
            addCriterion("columns  in ", values, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotIn(List<String> values) {
            addCriterion("columns not in ", values, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsBetween(String value1, String value2) {
            addCriterion("columns between ", value1, value2, "columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andColumnsNotBetween(String value1, String value2) {
            addCriterion("columns not between ", value1, value2, "columns");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andDisplayColumnsIsNull() {
            addCriterion("display_columns is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsIsNotNull() {
            addCriterion("display_columns is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsEqualTo(String value) {
            addCriterion("display_columns =", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotEqualTo(String value) {
            addCriterion("display_columns <>", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsGreaterThan(String value) {
            addCriterion("display_columns >", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsGreaterThanOrEqualTo(String value) {
            addCriterion("display_columns >=", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsLessThan(String value) {
            addCriterion("display_columns <", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsLessThanOrEqualTo(String value) {
            addCriterion("display_columns <=", value, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsLike(String value) {
            addCriterion("display_columns like ", value, "display_columns", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotLike(String value) {
            addCriterion("display_columns  not like ", value, "display_columns", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsLeftLike(String value) {
            addCriterion("display_columns like ", value, "display_columns", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotLeftLike(String value) {
            addCriterion("display_columns  not like ", value, "display_columns", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsRightLike(String value) {
            addCriterion("display_columns like ", value, "display_columns", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotRightLike(String value) {
            addCriterion("display_columns  not like ", value, "display_columns", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsIn(List<String> values) {
            addCriterion("display_columns  in ", values, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotIn(List<String> values) {
            addCriterion("display_columns not in ", values, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsBetween(String value1, String value2) {
            addCriterion("display_columns between ", value1, value2, "display_columns");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andDisplayColumnsNotBetween(String value1, String value2) {
            addCriterion("display_columns not between ", value1, value2, "display_columns");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateLike(Date value) {
            addCriterion("create_date like ", value, "create_date", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotLike(Date value) {
            addCriterion("create_date  not like ", value, "create_date", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateLeftLike(Date value) {
            addCriterion("create_date like ", value, "create_date", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotLeftLike(Date value) {
            addCriterion("create_date  not like ", value, "create_date", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateRightLike(Date value) {
            addCriterion("create_date like ", value, "create_date", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotRightLike(Date value) {
            addCriterion("create_date  not like ", value, "create_date", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date  in ", values, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in ", values, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between ", value1, value2, "create_date");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between ", value1, value2, "create_date");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andStatusIsNull() {
            addCriterion("status is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusLike(String value) {
            addCriterion("status like ", value, "status", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotLike(String value) {
            addCriterion("status  not like ", value, "status", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusLeftLike(String value) {
            addCriterion("status like ", value, "status", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotLeftLike(String value) {
            addCriterion("status  not like ", value, "status", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusRightLike(String value) {
            addCriterion("status like ", value, "status", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotRightLike(String value) {
            addCriterion("status  not like ", value, "status", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusIn(List<String> values) {
            addCriterion("status  in ", values, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotIn(List<String> values) {
            addCriterion("status not in ", values, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusBetween(String value1, String value2) {
            addCriterion("status between ", value1, value2, "status");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between ", value1, value2, "status");
            return (WfDataSetCriteria) this;
        }
        public WfDataSetCriteria andModifyUserIsNull() {
            addCriterion("modify_user is null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserIsNotNull() {
            addCriterion("modify_user is not null");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserEqualTo(Integer value) {
            addCriterion("modify_user =", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotEqualTo(Integer value) {
            addCriterion("modify_user <>", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserGreaterThan(Integer value) {
            addCriterion("modify_user >", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("modify_user >=", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserLessThan(Integer value) {
            addCriterion("modify_user <", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserLessThanOrEqualTo(Integer value) {
            addCriterion("modify_user <=", value, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserLike(Integer value) {
            addCriterion("modify_user like ", value, "modify_user", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotLike(Integer value) {
            addCriterion("modify_user  not like ", value, "modify_user", 1);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserLeftLike(Integer value) {
            addCriterion("modify_user like ", value, "modify_user", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotLeftLike(Integer value) {
            addCriterion("modify_user  not like ", value, "modify_user", 0);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserRightLike(Integer value) {
            addCriterion("modify_user like ", value, "modify_user", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotRightLike(Integer value) {
            addCriterion("modify_user  not like ", value, "modify_user", 2);
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserIn(List<Integer> values) {
            addCriterion("modify_user  in ", values, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotIn(List<Integer> values) {
            addCriterion("modify_user not in ", values, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserBetween(Integer value1, Integer value2) {
            addCriterion("modify_user between ", value1, value2, "modify_user");
            return (WfDataSetCriteria) this;
        }

        public WfDataSetCriteria andModifyUserNotBetween(Integer value1, Integer value2) {
            addCriterion("modify_user not between ", value1, value2, "modify_user");
            return (WfDataSetCriteria) this;
        }

    }

    public static class WfDataSetCriteria extends GeneratedCriteria {

        protected WfDataSetCriteria() {
            super();
        }
    }

    public static class WfDataSetCriterion {
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

        protected WfDataSetCriterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected WfDataSetCriterion(String condition, Object value, int likeType) {
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

        protected WfDataSetCriterion(String condition, Object value,
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

        protected WfDataSetCriterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected WfDataSetCriterion(String condition, Object value,
                Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected WfDataSetCriterion(String condition, Object value,
                Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}