package com.chris.common.repo.elasticsearch.custom;


import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.ProductInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomOrderInfoRepo {

    SearchHits<OrderInfo> searchByCustomer (String status, PageRequest pageRequest);

    SearchHits<OrderInfo> searchBySeller (String status, PageRequest pageRequest);


}
