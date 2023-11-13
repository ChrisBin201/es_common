package com.chris.common.repo.elasticsearch.custom.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch.core.UpdateByQueryRequest;
import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import com.chris.common.config.UserDetailsInfo;
import com.chris.common.repo.elasticsearch.ProductInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomProductInfoRepo;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.entity.product.Category;
import com.chris.data.entity.product.Product;
import com.chris.data.entity.product.ProductItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CustomProductInfoRepoImpl implements CustomProductInfoRepo {

    private static final String INDEX_NAME = "product_info";

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ProductInfoRepo productInfoRepo;

    @Override
    public SearchHits<ProductInfo> searchByCustomer(String keyword, int rating, long categoryId, List<Double> price, PageRequest pageRequest) {
        log.info("sort {}, direction {}", pageRequest.getSort().stream().findFirst().get().getProperty(), pageRequest.getSort().stream().findFirst().get().getDirection());
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
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery);
        query.setPageable(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()));
        query.addSort(pageRequest.getSort());
        return elasticsearchOperations.search(query, ProductInfo.class, IndexCoordinates.of(INDEX_NAME));
    }

    @Override
    public SearchHits<ProductInfo> searchBySeller(String keyword, String status, long categoryId, List<Double> price, PageRequest pageRequest) {
        String filterQuery = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfo userLogin = (UserDetailsInfo) authentication.getPrincipal();
        if(userLogin.getId() > 0) {
            filterQuery+= """
                    ,{
                        "match": {
                            "seller_id": %d
                        }
                    }
                    """;
            filterQuery = String.format(filterQuery, userLogin.getId());
        }

        if(!status.isBlank() && !status.equals(Product.ProductStatus.getAll()) && !status.equals(Product.ProductStatus.getOutOfStock())) {
            String condition = """
                    ,{
                        "match": {
                            "status": "%s"
                        }
                    }
                    """;
            filterQuery += String.format(condition, status) ;
        }

        if(!status.isBlank() && status.equals(Product.ProductStatus.getOutOfStock())) {
            //TODO total quantity = 0
            String condition = """
                    
                    """;
            filterQuery += String.format(condition, status);
        }

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
        filterQuery = filterQuery.replaceFirst(",", "");

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
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery, pageRequest);

        return elasticsearchOperations.search(query, ProductInfo.class);
    }

    @Override
    public SearchHits<ProductInfo> searchByAdmin(String keyword, String status, long categoryId, List<Double> price, PageRequest pageRequest) {
        String filterQuery = "";

        if(!status.isBlank() && !status.equals(Product.ProductStatus.getAll()) && !status.equals(Product.ProductStatus.getOutOfStock())) {
            String condition = """
                    ,{
                        "match": {
                            "status": "%s"
                        }
                    }
                    """;
            filterQuery += String.format(condition, status) ;
        }

        if(!status.isBlank() && status.equals(Product.ProductStatus.getOutOfStock())) {
            //TODO total quantity = 0
            String condition = """
                    
                    """;
            filterQuery += String.format(condition, status);
        }

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
        filterQuery = filterQuery.replaceFirst(",", "");

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
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery, pageRequest);

        return elasticsearchOperations.search(query, ProductInfo.class);
    }

    @Override
    public SearchHits<ProductInfo> findAllByCategoriesTree(List<Category> categories) {
        String stringQuery = "";

        if(categories != null && categories.size() > 0) {
            String filterQuery = """
                    {
                        "bool": {
                            "must": [
                                %s
                            ]
                        }
                    }
                    """;
            List<String> categoryCondition = new ArrayList<>();
            for (Category category : categories) {
                categoryCondition.add(
                        String.format("""
                        {
                            "match": {
                                "categories_tree.id": %d
                            }
                        }
                        """, category.getId())
                );
            }
            stringQuery += String.format(filterQuery, String.join(",", categoryCondition));
        }
        log.info("stringQuery: {}", stringQuery);
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery);

        return elasticsearchOperations.search(query, ProductInfo.class);
    }
}
