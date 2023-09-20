package com.chris.common.service.elasticsearch.impl;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.repo.elasticsearch.ProductInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomProductInfoRepo;
import com.chris.common.service.elasticsearch.ElasticProductInfoService;
import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ElasticProductInfoServiceImpl implements ElasticProductInfoService {

    @Autowired
    private ProductInfoRepo productInfoRepo;

    @Autowired
    private CustomProductInfoRepo customProductInfoRepo;



    @Override
    public PaginationResult<ProductInfo> searchByCustomer(String keyword, int rating, long categoryId, String priceStr, PageRequest pageRequest) {
        long total = 0;
        long pageNumber = 0;
        List<ProductInfo> list = null;
        try {

            if(!pageRequest.getSort().isEmpty()) {
                String sortField = pageRequest.getSort().stream().findFirst().get().getProperty();
                if(!ProductInfo.SORT_FIELDS.contains(sortField))
                    throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_SORT_FIELD.getCode(), CommonErrorCode.INVALID_SORT_FIELD.getMessage());
            }

            List<Double> prices = Stream.of(priceStr.split(",")).map(Double::parseDouble).toList();
            SearchHits<ProductInfo> productInfos = customProductInfoRepo.searchByCustomer(keyword, rating, categoryId, prices, pageRequest);

            total = productInfos.getTotalHits();
            pageNumber = pageRequest.getPageNumber();
            list = productInfos.map(SearchHit::getContent).toList();
        } catch (NumberFormatException e) {
            throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_PRICE_FILTER.getCode(), e.getMessage());

        }
//        catch (Exception e) {
//            throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_SEARCH.getCode(),CommonErrorCode.INVALID_SEARCH.getMessage() );
//        }

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public long saveProductInfo(ProductInfo productInfo) {
        return productInfoRepo.save(productInfo).getId();
    }
}
