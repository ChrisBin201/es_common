package com.chris.common.repo.elasticsearch;

import com.chris.data.elasticsearch.CategoryInfo;
import com.chris.data.elasticsearch.OrderInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInfoRepo extends ElasticsearchRepository<CategoryInfo, Long> {
}
