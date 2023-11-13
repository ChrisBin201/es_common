package com.chris.common.service.elasticsearch.impl;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.repo.elasticsearch.CategoryInfoRepo;
import com.chris.common.service.elasticsearch.ElasticCategoryInfoService;
import com.chris.data.elasticsearch.CategoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ElasticCategoryInfoServiceImpl implements ElasticCategoryInfoService {

    @Autowired
    private CategoryInfoRepo categoryInfoRepo;

    @Override
    public long saveCategoryInfo(CategoryInfo categoryInfo) {
        return categoryInfoRepo.save(categoryInfo).getId();
    }

    @Override
    public CategoryInfo findById(long id) {
        return categoryInfoRepo.findById(id).orElseThrow(
            () -> new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.CATEGORY_NOT_FOUND.getCode(), CommonErrorCode.CATEGORY_NOT_FOUND.getMessage()));
    }

    @Override
    public List<CategoryInfo> findAll() {
        List<CategoryInfo> categoryInfos = new ArrayList<>();
        categoryInfoRepo.findAll().forEach(categoryInfos::add);
        return categoryInfos;
    }

    @Override
    public List<CategoryInfo> search(String keyword) {
        return null;
    }
}
