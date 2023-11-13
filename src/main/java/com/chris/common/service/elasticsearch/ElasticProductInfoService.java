package com.chris.common.service.elasticsearch;

import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Rating;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.chris.data.entity.product.ProductItem;
import org.hibernate.boot.Metadata;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;

public interface ElasticProductInfoService {
    //ProductInfo
    PaginationResult<ProductInfo> searchByCustomer (String keyword, int rating, long categoryId, String priceStr, PageRequest pageRequest, String sort);

    PaginationResult<ProductInfo> searchBySeller (String keyword, String status, long categoryId, String priceStr, PageRequest pageRequest);

    PaginationResult<ProductInfo> searchByAdmin (String keyword, String status, long categoryId, String priceStr, PageRequest pageRequest);

    ProductInfo findById(long id);
    long saveProductInfo(ProductInfo productInfo);

    long updateInventoryProductInfo(ProductItemDetail productItem);

    long updateSales(OrderLine orderLine);

    long updateRatingAverage(Rating rating);
}
