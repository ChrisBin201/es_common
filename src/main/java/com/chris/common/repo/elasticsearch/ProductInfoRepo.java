package com.chris.common.repo.elasticsearch;

import com.chris.data.elasticsearch.ProductInfo;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepo extends ElasticsearchRepository<ProductInfo, Long> {
}
