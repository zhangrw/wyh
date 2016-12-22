package com.banshion.portal.web.index.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TTransInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TTransInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSerialNumberIsNull() {
            addCriterion("serial_number is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(String value) {
            addCriterion("serial_number =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(String value) {
            addCriterion("serial_number <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(String value) {
            addCriterion("serial_number >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("serial_number >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(String value) {
            addCriterion("serial_number <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(String value) {
            addCriterion("serial_number <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLike(String value) {
            addCriterion("serial_number like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotLike(String value) {
            addCriterion("serial_number not like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<String> values) {
            addCriterion("serial_number in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<String> values) {
            addCriterion("serial_number not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(String value1, String value2) {
            addCriterion("serial_number between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(String value1, String value2) {
            addCriterion("serial_number not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andTransValueIsNull() {
            addCriterion("trans_value is null");
            return (Criteria) this;
        }

        public Criteria andTransValueIsNotNull() {
            addCriterion("trans_value is not null");
            return (Criteria) this;
        }

        public Criteria andTransValueEqualTo(String value) {
            addCriterion("trans_value =", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueNotEqualTo(String value) {
            addCriterion("trans_value <>", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueGreaterThan(String value) {
            addCriterion("trans_value >", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueGreaterThanOrEqualTo(String value) {
            addCriterion("trans_value >=", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueLessThan(String value) {
            addCriterion("trans_value <", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueLessThanOrEqualTo(String value) {
            addCriterion("trans_value <=", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueLike(String value) {
            addCriterion("trans_value like", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueNotLike(String value) {
            addCriterion("trans_value not like", value, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueIn(List<String> values) {
            addCriterion("trans_value in", values, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueNotIn(List<String> values) {
            addCriterion("trans_value not in", values, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueBetween(String value1, String value2) {
            addCriterion("trans_value between", value1, value2, "transValue");
            return (Criteria) this;
        }

        public Criteria andTransValueNotBetween(String value1, String value2) {
            addCriterion("trans_value not between", value1, value2, "transValue");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberIsNull() {
            addCriterion("srcbank_number is null");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberIsNotNull() {
            addCriterion("srcbank_number is not null");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberEqualTo(String value) {
            addCriterion("srcbank_number =", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberNotEqualTo(String value) {
            addCriterion("srcbank_number <>", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberGreaterThan(String value) {
            addCriterion("srcbank_number >", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberGreaterThanOrEqualTo(String value) {
            addCriterion("srcbank_number >=", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberLessThan(String value) {
            addCriterion("srcbank_number <", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberLessThanOrEqualTo(String value) {
            addCriterion("srcbank_number <=", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberLike(String value) {
            addCriterion("srcbank_number like", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberNotLike(String value) {
            addCriterion("srcbank_number not like", value, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberIn(List<String> values) {
            addCriterion("srcbank_number in", values, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberNotIn(List<String> values) {
            addCriterion("srcbank_number not in", values, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberBetween(String value1, String value2) {
            addCriterion("srcbank_number between", value1, value2, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNumberNotBetween(String value1, String value2) {
            addCriterion("srcbank_number not between", value1, value2, "srcbankNumber");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameIsNull() {
            addCriterion("srcbank_name is null");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameIsNotNull() {
            addCriterion("srcbank_name is not null");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameEqualTo(String value) {
            addCriterion("srcbank_name =", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameNotEqualTo(String value) {
            addCriterion("srcbank_name <>", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameGreaterThan(String value) {
            addCriterion("srcbank_name >", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameGreaterThanOrEqualTo(String value) {
            addCriterion("srcbank_name >=", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameLessThan(String value) {
            addCriterion("srcbank_name <", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameLessThanOrEqualTo(String value) {
            addCriterion("srcbank_name <=", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameLike(String value) {
            addCriterion("srcbank_name like", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameNotLike(String value) {
            addCriterion("srcbank_name not like", value, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameIn(List<String> values) {
            addCriterion("srcbank_name in", values, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameNotIn(List<String> values) {
            addCriterion("srcbank_name not in", values, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameBetween(String value1, String value2) {
            addCriterion("srcbank_name between", value1, value2, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andSrcbankNameNotBetween(String value1, String value2) {
            addCriterion("srcbank_name not between", value1, value2, "srcbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameIsNull() {
            addCriterion("targetbank_name is null");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameIsNotNull() {
            addCriterion("targetbank_name is not null");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameEqualTo(String value) {
            addCriterion("targetbank_name =", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameNotEqualTo(String value) {
            addCriterion("targetbank_name <>", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameGreaterThan(String value) {
            addCriterion("targetbank_name >", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameGreaterThanOrEqualTo(String value) {
            addCriterion("targetbank_name >=", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameLessThan(String value) {
            addCriterion("targetbank_name <", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameLessThanOrEqualTo(String value) {
            addCriterion("targetbank_name <=", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameLike(String value) {
            addCriterion("targetbank_name like", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameNotLike(String value) {
            addCriterion("targetbank_name not like", value, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameIn(List<String> values) {
            addCriterion("targetbank_name in", values, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameNotIn(List<String> values) {
            addCriterion("targetbank_name not in", values, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameBetween(String value1, String value2) {
            addCriterion("targetbank_name between", value1, value2, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNameNotBetween(String value1, String value2) {
            addCriterion("targetbank_name not between", value1, value2, "targetbankName");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberIsNull() {
            addCriterion("targetbank_number is null");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberIsNotNull() {
            addCriterion("targetbank_number is not null");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberEqualTo(String value) {
            addCriterion("targetbank_number =", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberNotEqualTo(String value) {
            addCriterion("targetbank_number <>", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberGreaterThan(String value) {
            addCriterion("targetbank_number >", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberGreaterThanOrEqualTo(String value) {
            addCriterion("targetbank_number >=", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberLessThan(String value) {
            addCriterion("targetbank_number <", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberLessThanOrEqualTo(String value) {
            addCriterion("targetbank_number <=", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberLike(String value) {
            addCriterion("targetbank_number like", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberNotLike(String value) {
            addCriterion("targetbank_number not like", value, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberIn(List<String> values) {
            addCriterion("targetbank_number in", values, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberNotIn(List<String> values) {
            addCriterion("targetbank_number not in", values, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberBetween(String value1, String value2) {
            addCriterion("targetbank_number between", value1, value2, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andTargetbankNumberNotBetween(String value1, String value2) {
            addCriterion("targetbank_number not between", value1, value2, "targetbankNumber");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(int value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(int value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(int value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(int value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(int value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(int value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(int value1, int value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(int value1, int value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("bz is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("bz is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("bz =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("bz <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("bz >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("bz >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("bz <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("bz <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("bz like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("bz not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("bz in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("bz not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("bz between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("bz not between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}