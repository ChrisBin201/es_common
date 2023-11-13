package com.chris.common.repo.elasticsearch;

import com.chris.data.elasticsearch.OrderInfo;
import com.chris.data.elasticsearch.ProductInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoRepo extends ElasticsearchRepository<OrderInfo, Long> {
}
