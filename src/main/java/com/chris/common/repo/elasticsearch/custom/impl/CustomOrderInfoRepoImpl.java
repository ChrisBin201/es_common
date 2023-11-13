package com.chris.common.repo.elasticsearch.custom.impl;

import com.chris.common.config.UserDetailsInfo;
import com.chris.common.repo.elasticsearch.custom.CustomOrderInfoRepo;
import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CustomOrderInfoRepoImpl implements CustomOrderInfoRepo {

    private static final String INDEX_NAME = "order_info";

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<OrderInfo> searchByCustomer(String status, PageRequest pageRequest) {
        //TODO: implement search orders
        String filterQuery = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfo userLogin = (UserDetailsInfo) authentication.getPrincipal();
        if(userLogin.getId() > 0) {
            String condition = """
                    ,{
                        "match": {
                            "customer_id": %d
                        }
                    }
                    """;
            filterQuery += String.format(condition, userLogin.getId());
        }

        if(!status.isBlank()) {
            String condition = """
                    ,{
                        "match": {
                            "status": "%s"
                        }
                    }
                    """;
            filterQuery += String.format(condition, status);
        }
        filterQuery = filterQuery.replaceFirst(",", "");

        String stringQuery = String.format("""
                 {
                    "bool": {
                        "must": [
                            %s
                        ]
                    }
                 }
                """,
                filterQuery);
        log.info("stringQuery: {}", stringQuery);
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery);
        query.setPageable(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()));
        query.addSort(pageRequest.getSort());
        return elasticsearchOperations.search(query, OrderInfo.class, IndexCoordinates.of(INDEX_NAME));
    }

    @Override
    public SearchHits<OrderInfo> searchBySeller(String status, PageRequest pageRequest) {
        String filterQuery = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfo userLogin = (UserDetailsInfo) authentication.getPrincipal();
        if(userLogin.getId() > 0) {
            String condition = """
                    ,{
                        "match": {
                            "shop_info.id": %d
                        }
                    }
                    """;
            filterQuery += String.format(condition, userLogin.getId());
        }

        if(!status.isBlank()) {
            String condition = """
                    ,{
                        "match": {
                            "status": "%s"
                        }
                    }
                    """;
            filterQuery += String.format(condition, status);
        }
        filterQuery = filterQuery.replaceFirst(",", "");

        String stringQuery = String.format("""
                 {
                    "bool": {
                        "must": [
                            %s
                        ]
                    }
                 }
                """,
                filterQuery);
        log.info("stringQuery: {}", stringQuery);
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery);
        query.setPageable(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()));
        query.addSort(pageRequest.getSort());
        return elasticsearchOperations.search(query, OrderInfo.class, IndexCoordinates.of(INDEX_NAME));
    }
}
