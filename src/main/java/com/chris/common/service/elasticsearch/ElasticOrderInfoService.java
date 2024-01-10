package com.chris.common.service.elasticsearch;

import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.entity.order.Order;
import com.chris.data.entity.order.OrderLine;
import org.springframework.data.domain.PageRequest;

public interface ElasticOrderInfoService {

    PaginationResult<OrderInfo> searchByCustomer (String status, PageRequest pageRequest);

    PaginationResult<OrderInfo> searchBySeller (String status, PageRequest pageRequest);


    long saveOrderInfo(Order order);

    OrderInfo getById(long id);

}
