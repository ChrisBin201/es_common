package com.chris.common.repo.elasticsearch.custom;

import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.elasticsearch.RatingInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomRatingInfoRepo {

    SearchHits<RatingInfo> searchByCustomer (int rating, List<Long> productItemId, PageRequest pageRequest);

    SearchHits<RatingInfo> findAllByProductId (long id);

}
