package com.chris.common.service.elasticsearch.impl;

import com.chris.common.caller.UserServiceCaller;
import com.chris.common.repo.elasticsearch.OrderInfoRepo;
import com.chris.common.repo.elasticsearch.RatingInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomOrderInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomRatingInfoRepo;
import com.chris.common.service.elasticsearch.ElasticRatingInfoService;
import com.chris.data.dto.PaginationResult;
import com.chris.data.dto.user.CustomerDTO;
import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.RatingInfo;
import com.chris.data.elasticsearch.sub.CustomerDetail;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticRatingInfoServiceImpl implements ElasticRatingInfoService {

    @Autowired
    private RatingInfoRepo ratingInfoRepo;

    @Autowired
    private CustomRatingInfoRepo customRatingInfoRepo;

    @Autowired
    private UserServiceCaller userServiceCaller;

    @Override
    public PaginationResult<RatingInfo> searchByCustomer(int rating, List<Long> productItemId, PageRequest pageRequest) {
        //TODO implement this method
        long total = 0;
        long pageNumber = 0;
        List<RatingInfo> list = null;
        SearchHits<RatingInfo> ratingInfos = customRatingInfoRepo.searchByCustomer(rating,productItemId, pageRequest);

        total = ratingInfos.getTotalHits();
        pageNumber = pageRequest.getPageNumber();
        list = ratingInfos.map(SearchHit::getContent).toList();
        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public long saveRatingInfo(Rating rating) {
        CustomerDTO customerDTO = userServiceCaller.getCustomerInfo(rating.getCustomerId()).block();
        RatingInfo ratingInfo = RatingInfo.from(CustomerDetail.from(customerDTO), rating);
        return ratingInfoRepo.save(ratingInfo).getId();
    }

    @Override
    public RatingInfo findByOrderLineIdAndCustomerId(long orderLineId, long customerId) {
        return ratingInfoRepo.findByOrderLineIdAndCustomerId(orderLineId, customerId).orElse(null);
    }
}
