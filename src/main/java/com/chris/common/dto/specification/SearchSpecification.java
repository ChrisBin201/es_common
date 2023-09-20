package com.chris.common.dto.specification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SearchSpecification<T> implements Specification<T> {

//    private static final long serialVersionUID = -9153865343320750644L;

    private final transient SearchRequest request;
//    private transient FilterOption[] filterableFields = {} ;
//    private transient Map<String,String> sortableFields = new HashMap<>() ;

    public SearchSpecification(SearchRequest request) {
        this.request = request;
    }
    public SearchSpecification(SearchRequest request, FilterOption[] filterableFields, Map<String,String> sortableFields) {
        this.request = request;

        //pre-process request
        //filter
        //filter allowed keys
        List<FilterRequest> filters = this.request.getFilters()
                .stream().filter(f -> Arrays.stream(filterableFields)
                        .anyMatch(ff -> ff.key.equals(f.getKey())
                                && Arrays.stream(ff.operators).anyMatch(fo -> fo.equals(Operator.fromString(f.getOperator())))))
                .collect(Collectors.toList());
        //set field type
        filters.stream().forEach(f -> f.setFieldType(Arrays.stream(filterableFields)
                .filter(ff -> ff.key.equals(f.getKey()))
                .findFirst().get().type));
        //convert key to field name in db
        filters.stream().forEach(f -> f.setKey(Arrays.stream(filterableFields)
                .filter(ff -> ff.key.equals(f.getKey()))
                .findFirst().get().field));
        this.request.setFilters(filters);

        //sort
        //filter allowed keys
        List<SortRequest> sorts = this.request.getSorts()
                .stream().filter(s -> sortableFields.containsKey(s.getKey()))
                .collect(Collectors.toList());
        //convert key to field name in db
        sorts.stream().forEach(s -> s.setKey(sortableFields.get(s.getKey())));
        this.request.setSorts(sorts);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

        List<FilterRequest> filters = this.request.getFilters();
        for (FilterRequest filter : filters) {
            log.info("Filter: {} {} {} {}", filter.getKey(), filter.getOperator().toString(), filter.getValue());
            predicate = Operator.fromString(filter.getOperator()).build(root, cb, filter, predicate);
        }

        List<SortRequest> sorts = this.request.getSorts();
        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : sorts) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }

        query.orderBy(orders);
        return predicate;
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 100));
    }

}

