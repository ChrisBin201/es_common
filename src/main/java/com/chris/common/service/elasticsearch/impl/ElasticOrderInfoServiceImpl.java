package com.chris.common.service.elasticsearch.impl;

import com.chris.common.caller.UserServiceCaller;
import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.repo.elasticsearch.OrderInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomOrderInfoRepo;
import com.chris.common.service.elasticsearch.ElasticOrderInfoService;
import com.chris.data.dto.PaginationResult;
import com.chris.data.dto.user.SellerDTO;
import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.elasticsearch.sub.ShopDetail;
import com.chris.data.entity.order.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ElasticOrderInfoServiceImpl implements ElasticOrderInfoService {

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    @Autowired
    private CustomOrderInfoRepo customOrderInfoRepo;

    @Autowired
    private UserServiceCaller userServiceCaller;


    @Override
    public PaginationResult<OrderInfo> searchByCustomer(String status, PageRequest pageRequest) {
        //TODO implement this method
        long total = 0;
        long pageNumber = 0;
        List<OrderInfo> list = null;
        String statusStr = "";
        if(!status.isBlank()) {
            OrderLine.OrderStatus orderStatus = OrderLine.OrderStatus.fromString(status);
            statusStr = orderStatus.name();
        }
        SearchHits<OrderInfo> orderInfos = customOrderInfoRepo.searchByCustomer(statusStr, pageRequest);

        total = orderInfos.getTotalHits();
        pageNumber = pageRequest.getPageNumber();
        list = orderInfos.map(SearchHit::getContent).toList();

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public PaginationResult<OrderInfo> searchBySeller(String status, PageRequest pageRequest) {
        //TODO implement this method
        long total = 0;
        long pageNumber = 0;
        List<OrderInfo> list = null;
        String statusStr = "";
        if(!status.isBlank()) {
            OrderLine.OrderStatus orderStatus = OrderLine.OrderStatus.fromString(status);
            statusStr = orderStatus.name();
        }
        SearchHits<OrderInfo> orderInfos = customOrderInfoRepo.searchBySeller(statusStr, pageRequest);

        total = orderInfos.getTotalHits();
        pageNumber = pageRequest.getPageNumber();
        list = orderInfos.map(SearchHit::getContent).toList();

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public long saveOrderInfo(OrderLine orderLine) {
        SellerDTO sellerDTO = userServiceCaller.getSellerInfo(orderLine.getProductItem().getProductInfo().getSellerId()).block();
        sellerDTO.setId(orderLine.getProductItem().getProductInfo().getSellerId());
        OrderInfo orderInfo = OrderInfo.from(orderLine, ShopDetail.from(sellerDTO));
        return orderInfoRepo.save(orderInfo).getId();
    }
}
