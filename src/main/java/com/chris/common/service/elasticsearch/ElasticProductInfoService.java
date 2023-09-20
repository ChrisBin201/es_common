package com.chris.common.service.elasticsearch;

import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import org.hibernate.boot.Metadata;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;

public interface ElasticProductInfoService {
    //ProductInfo
    PaginationResult<ProductInfo> searchByCustomer (String keyword, int rating, long categoryId, String priceStr, PageRequest pageRequest);
    long saveProductInfo(ProductInfo productInfo);
}
