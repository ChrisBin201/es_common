package com.chris.common.repo.elasticsearch;

import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.RatingInfo;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingInfoRepo extends ElasticsearchRepository<RatingInfo, Long> {

    @Query("""
            {
                "bool": {
                    "must": [
                        {
                            "match": {
                                "order_line.id": ?0
                            }
                        },
                        {
                            "match": {
                                "customer.id": ?1
                            }
                        }
                    ]
                }
            }
    """
    )
    Optional<RatingInfo> findByOrderLine_IdAndCustomerId(long orderLineId, long customerId);
}
