package com.chris.common.repo.elasticsearch.custom;

import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomProductInfoRepo {

    SearchHits<ProductInfo> searchByCustomer (String keyword, int rating, long categoryId, List<Double> price, PageRequest pageRequest);

}
