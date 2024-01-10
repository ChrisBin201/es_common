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
import com.chris.data.entity.order.Order;
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
        long total = 0;
        long pageNumber = 0;
        List<OrderInfo> list = null;
        String statusStr = "";
        if(!status.isBlank()) {
            Order.OrderStatus orderStatus = Order.OrderStatus.fromString(status);
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
        long total = 0;
        long pageNumber = 0;
        List<OrderInfo> list = null;
        String statusStr = "";
        if(!status.isBlank()) {
            Order.OrderStatus orderStatus = Order.OrderStatus.fromString(status);
            statusStr = orderStatus.name();
        }
        SearchHits<OrderInfo> orderInfos = customOrderInfoRepo.searchBySeller(statusStr, pageRequest);

        total = orderInfos.getTotalHits();
        pageNumber = pageRequest.getPageNumber();
        list = orderInfos.map(SearchHit::getContent).toList();

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public long saveOrderInfo(Order order) {
        SellerDTO sellerDTO = userServiceCaller.getSellerInfo(order.getSellerId()).block();
        sellerDTO.setId(order.getSellerId());
        OrderInfo orderInfo = OrderInfo.from(order, ShopDetail.from(sellerDTO));
        return orderInfoRepo.save(orderInfo).getId();
    }

    @Override
    public OrderInfo getById(long id) {
        return orderInfoRepo.findById(id).orElseThrow(() ->
                new CommonException(HttpStatus.NOT_FOUND.value(), CommonErrorCode.ORDER_NOT_FOUND.getCode(), CommonErrorCode.ORDER_NOT_FOUND.getMessage())
        );
    }
}
