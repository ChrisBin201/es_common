package com.chris.common.repo.elasticsearch.custom;

import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.entity.product.Category;
import com.chris.data.entity.product.ProductItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;

import java.util.List;

public interface CustomProductInfoRepo {

    SearchHits<ProductInfo> searchByCustomer (String keyword, int rating, long categoryId, List<Double> price, PageRequest pageRequest);

    SearchHits<ProductInfo> searchBySeller (String keyword, String status, long categoryId, List<Double> price, PageRequest pageRequest);

    SearchHits<ProductInfo> searchByAdmin (String keyword, String status, long categoryId, List<Double> price, PageRequest pageRequest);

    SearchHits<ProductInfo> findAllByCategoriesTree (List<Category> categories);
}
