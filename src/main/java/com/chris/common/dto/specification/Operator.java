package com.chris.common.dto.specification;

import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public enum Operator {
    //Relational Operators
    EQUAL("=") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<?> key = root.get(request.getKey());

            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.equal(key, value), predicate) : cb.or(cb.equal(key, value), predicate);
        }
    },

    NOT_EQUAL("!=") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<?> key = root.get(request.getKey());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.notEqual(key, value), predicate): cb.or(cb.notEqual(key, value), predicate);
        }
    },

    GREATER_THAN_EQUAL(">=") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<String> key = root.get(request.getKey());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.greaterThanOrEqualTo(key, value.toString()), predicate):  cb.or(cb.greaterThanOrEqualTo(key, value.toString()), predicate);
        }
    },
    GREATER_THAN(">") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<String> key = root.get(request.getKey());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.greaterThan(key, value.toString()), predicate):cb.or(cb.greaterThan(key, value.toString()), predicate);
        }
    },

    LESS_THAN_EQUAL("<=") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<String> key = root.get(request.getKey());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.lessThanOrEqualTo(key, value.toString()), predicate):cb.or(cb.lessThanOrEqualTo(key, value.toString()), predicate);
        }
    },
    LESS_THAN("<") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<String> key = root.get(request.getKey());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.lessThan(key, value.toString()), predicate):cb.or(cb.lessThan(key, value.toString()), predicate);
        }
    },


    LIKE("like") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
//            log.info(request.getFilterOperation().toString());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate)
                    :
                    cb.or(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);
//            return  cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);

        }
    },
    NOT_NULL("notNull") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
//            log.info(request.getFilterOperation().toString());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.isNotNull(cb.upper(key)), predicate)
                    :
                    cb.or(cb.isNotNull(cb.upper(key)), predicate);
//            return  cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);

        }
    },
    NULL("null") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
//            log.info(request.getFilterOperation().toString());
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(cb.isNull(cb.upper(key)), predicate)
                    :
                    cb.or(cb.isNull(cb.upper(key)), predicate);
//            return  cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);

        }
    },

    IN("in") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            List<Object> values = request.getValues();
            CriteriaBuilder.In<Object> inClause = cb.in(root.get(request.getKey()));
            for (Object value : values) {
                inClause.value(request.getFieldType().parse(value.toString()));
            }
            return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                    cb.and(inClause, predicate):cb.or(inClause, predicate) ;
        }
    },

    BETWEEN("between") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Object valueTo = request.getFieldType().parse(request.getValueTo().toString());
            if (request.getFieldType() == FieldType.DATE) {
                LocalDateTime startDate = (LocalDateTime) value;
                LocalDateTime endDate = (LocalDateTime) valueTo;
                Expression<LocalDateTime> key = root.get(request.getKey());
                return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                        cb.and(cb.and(cb.greaterThanOrEqualTo(key, startDate), cb.lessThanOrEqualTo(key, endDate)), predicate)
                        :
                        cb.or(cb.and(cb.greaterThanOrEqualTo(key, startDate), cb.lessThanOrEqualTo(key, endDate)), predicate);
            }

            if (request.getFieldType() != FieldType.CHAR && request.getFieldType() != FieldType.BOOLEAN) {
                Number start = (Number) value;
                Number end = (Number) valueTo;
                Expression<Number> key = root.get(request.getKey());
                return request.getFilterOperation() == FilterRequest.FilterOperation.AND ?
                        cb.and(cb.and(cb.ge(key, start), cb.le(key, end)), predicate)
                        :
                        cb.or(cb.and(cb.ge(key, start), cb.le(key, end)), predicate);
            }

            log.info("Can not use between for {} field type.", request.getFieldType());
            return predicate;
        }
    };

    public String simpleOperator;

    Operator(String simpleOperator) {
        this.simpleOperator = simpleOperator;
    }

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate);

    public static Operator fromString(String text) {
        for (Operator b : Operator.values()) {
            if (b.simpleOperator.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}

