package com.chris.common.repo.elasticsearch;

import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.RatingInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingInfoRepo extends ElasticsearchRepository<RatingInfo, Long> {

    Optional<RatingInfo> findByOrderLineIdAndCustomerId(long orderLineId, long customerId);
}
