package com.chris.common.repo.elasticsearch.custom.impl;

import com.chris.common.repo.elasticsearch.custom.CustomRatingInfoRepo;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.elasticsearch.RatingInfo;
import com.chris.data.entity.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomRatingInfoRepoImpl implements CustomRatingInfoRepo {

    private static final String INDEX_NAME = "rating_info";

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Override
    public SearchHits<RatingInfo> searchByCustomer(int rating, List<Long> productItemId, PageRequest pageRequest) {
        //TODO implement this method
        String filterQuery = "";
        if(rating > 0) {
            String condition = """
                    ,{
                        "match": {
                            "rating": %d
                        }
                    }
                    """;
            filterQuery += String.format(condition, rating);
        }


        if(productItemId != null && !productItemId.isEmpty()) {
            String productItemIdString = "";
            for (Long id : productItemId) {
                productItemIdString += String.format(",%s", id);
            }
            productItemIdString = productItemIdString.replaceFirst(",", "");
            //add brackets
            productItemIdString = String.format("[%s]", productItemIdString);
            String condition = """
                    ,{
                        "terms": {
                            "product_item_id": %s
                        }
                    }
                    """;
            filterQuery += String.format(condition, productItemIdString);
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
        org.springframework.data.elasticsearch.core.query.Query query = new StringQuery(stringQuery, pageRequest);

        return elasticsearchOperations.search(query, RatingInfo.class, IndexCoordinates.of(INDEX_NAME));
    }

    @Override
    public SearchHits<RatingInfo> findAllByProductId(long id) {
        //TODO implement this method
        return null;
    }

}
