package com.chris.common.dto.specification.custom;

import com.chris.common.dto.criteria.SearchCriteria;
import com.chris.common.dto.criteria.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Objects;

public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Autowired
    public GenericSpecification(final SearchCriteria searchCriteria, Class<T> clazz) {
        super();
//        String dataType = findDataType(searchCriteria.getKey(), clazz);
//        if (dataType.equals("Integer")) {
//            searchCriteria.setValue(DataUtils.convertToDataType(Integer.class, searchCriteria.getValue().toString()));
//            searchCriteria.setDataType(Integer.class);
//        }
//        if (dataType.equals("Long")) {
//            searchCriteria.setValue(DataUtils.convertToDataType(Long.class, searchCriteria.getValue().toString()));
//            searchCriteria.setDataType(Long.class);
//
//        }
//        if (dataType.equals("Double")) {
//            searchCriteria.setValue(DataUtils.convertToDataType(Double.class, searchCriteria.getValue().toString()));
//            searchCriteria.setDataType(Double.class);
//
//        }
//        if (dataType.equals("String")) {
////            searchCriteria.setValue(DataUtils.convertToDataType(String.class, searchCriteria.getValue().toString()));
//        }
//        if (dataType.equals("Float")) {
//            searchCriteria.setValue(DataUtils.convertToDataType(Float.class, searchCriteria.getValue().toString()));
//            searchCriteria.setDataType(Float.class);
//
//        }
        this.searchCriteria = searchCriteria;
    }

//    /**
//     * find datatype with fieldname
//     *
//     * @param fieldName
//     * @return
//     */
//    public String findDataType(String fieldName, Class<T> clazz) {
//        String dataType = null;
//        Field[] fields = clazz.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            if (fieldName.equals(field.getName())) {
//                String[] str = field.getType().getTypeName().split(Pattern.quote("."));
//                // str[2] = dataType
//                dataType = str[2];
//                break;
//            }
//        }
//        return dataType;
//    }

    @Override
    public Specification<T> and(Specification<T> other) {

        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Object strToSearch = searchCriteria.getValue();
//        String strToSearch = searchCriteria.getValue();
        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase() + "%");
            case DOES_NOT_CONTAIN:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase() + "%");

            case BEGINS_WITH:

                return cb.like(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase() + "%");
            case DOES_NOT_BEGIN_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase() + "%");
            case ENDS_WITH:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase());
            case DOES_NOT_END_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase());
            case EQUAL:
                if (strToSearch instanceof String) {
                    return cb.equal(cb.lower(root.<String>get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());
                } else {
                    return cb.equal(root.<Object>get(searchCriteria.getKey()), strToSearch);
                }

            case NOT_EQUAL:
                if (strToSearch instanceof String) {
                    return cb.notEqual(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());
                } else {
                    return cb.notEqual(root.<Object>get(searchCriteria.getKey()), strToSearch);
                }
            case NUL:
                return cb.isNull(cb.lower(root.get(searchCriteria.getKey())));
            case NOT_NULL:
                return cb.isNotNull(cb.lower(root.get(searchCriteria.getKey())));
            case GREATER_THAN:
//                if (strToSearch instanceof String) {
//                }
//                else {
//                    return cb.greaterThan(root.get(searchCriteria.getKey()), strToSearch);
//                }
                return cb.greaterThan(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());

            case GREATER_THAN_EQUAL:
//                if (strToSearch instanceof String) {
//                }
//                else {
//                    return cb.greaterThanOrEqualTo(root.<Object>get(searchCriteria.getKey()), strToSearch);
//                }
                return cb.greaterThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN:
                return cb.lessThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:

                return cb.lessThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        return null;
    }
}
