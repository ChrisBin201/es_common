package com.chris.common.service.elasticsearch;

import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.RatingInfo;
import com.chris.data.entity.order.Rating;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ElasticRatingInfoService {
    PaginationResult<RatingInfo> searchByCustomer (int rating, List<Long> productItemId, PageRequest pageRequest);

    long saveRatingInfo(Rating rating);

    RatingInfo findByOrderLineIdAndCustomerId(long orderLineId, long customerId);

}
