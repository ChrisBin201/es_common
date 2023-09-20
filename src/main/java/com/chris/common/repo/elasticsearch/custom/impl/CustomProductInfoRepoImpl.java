package com.chris.common.repo.elasticsearch.custom.impl;

import com.chris.common.repo.elasticsearch.custom.CustomProductInfoRepo;
import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomProductInfoRepoImpl implements CustomProductInfoRepo {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ProductInfo> searchByCustomer(String keyword, int rating, long categoryId, List<Double> price, PageRequest pageRequest) {
        String filterQuery = "";

        filterQuery+= """
                {
                    "match": {
                        "status": "ACTIVE"
                    }
                }
                """;

        if(!keyword.isBlank()) {
            String condition = """
                    ,{
                        "wildcard": {
                            "name": {
                                "value": "*%s*"
                            }
                        }
                    }
                    """;
            filterQuery += String.format(condition, keyword);
        }

        if(rating > 0) {
            String condition = """
                    ,{
                        "range": {
                            "rating_average": {
                                "gte": %d
                            }
                        }
                    }
                    """;
            filterQuery += String.format(condition, rating);
        }

        if(categoryId > 0) {
            String condition = """
                    ,{
                        "match": {
                            "categories_tree.id": %d
                        }
                    }
                    """;
            filterQuery += String.format(condition, categoryId);
        }

        if(price != null ) {
            if(price.size() == 1){
                String condition = """
                    ,{
                        "range": {
                            "product_items.price": {
                                "gte": %f
                            }
                        }
                    }
                    """;
                filterQuery += String.format(condition, price.get(0));
            }
            if(price.size() == 2  && price.get(0) < price.get(1)){
                String condition = """
                    ,{
                        "range": {
                            "product_items.price": {
                                "gte": %f,
                                "lte": %f
                            }
                        }
                    }
                    """;
                filterQuery += String.format(condition, price.get(0), price.get(1));
            }
        }

        String stringQuery = String.format("""
                 {
                    "bool": {
                        "must": [
                            %s
                        ],
                        "should": [
                            {
                                "wildcard": {
                                    "description": {
                                        "value": "*%s*"
                                    }
                                }
                            }
                        ]
                    }
                 }
                """,
                filterQuery, keyword);
        log.info("stringQuery: {}", stringQuery);
        Query query = new StringQuery(stringQuery, pageRequest);

        return elasticsearchOperations.search(query, ProductInfo.class);
    }
}
