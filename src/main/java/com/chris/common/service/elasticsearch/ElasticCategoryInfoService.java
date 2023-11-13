package com.chris.common.service.elasticsearch;

import com.chris.data.elasticsearch.CategoryInfo;
import com.chris.data.entity.product.Category;

import java.util.List;

public interface ElasticCategoryInfoService {
    long saveCategoryInfo(CategoryInfo categoryInfo);

    CategoryInfo findById(long id);

    List<CategoryInfo> findAll();

    List<CategoryInfo> search(String keyword);

}
