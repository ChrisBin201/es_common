package com.chris.common.service.elasticsearch.impl;

import com.chris.common.config.UserDetailsInfo;
import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.repo.elasticsearch.ProductInfoRepo;
import com.chris.common.repo.elasticsearch.custom.CustomProductInfoRepo;
import com.chris.common.service.elasticsearch.ElasticProductInfoService;
import com.chris.data.dto.PaginationResult;
import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.elasticsearch.sub.SaleInfo;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.Rating;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.chris.data.entity.product.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public PaginationResult<ProductInfo> searchByCustomer(String keyword, int rating, long categoryId, String priceStr, PageRequest pageRequest, String sortField) {
        long total = 0;
        long pageNumber = 0;
        List<ProductInfo> list = null;
        try {

            if(!sortField.isBlank()) {
                if(ProductInfo.SORT_FIELDS.stream().noneMatch(sf-> sf.getKey().equals(sortField) ))
                    throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_SORT_FIELD.getCode(), CommonErrorCode.INVALID_SORT_FIELD.getMessage());
                Sort sort  = ProductInfo.SORT_FIELDS.stream().filter(sf -> sf.getKey().equals(sortField)).findFirst().get().getValue();
                pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);
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
    public PaginationResult<ProductInfo> searchBySeller(String keyword, String status, long categoryId, String priceStr, PageRequest pageRequest) {
        long total = 0;
        long pageNumber = 0;
        List<ProductInfo> list = null;
        try {

//            if(!pageRequest.getSort().isEmpty()) {
//                String sortField = pageRequest.getSort().stream().findFirst().get().getProperty();
//                if(ProductInfo.SORT_FIELDS.stream().noneMatch(sf-> sf.getKey().equals(sortField)))
//                    throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_SORT_FIELD.getCode(), CommonErrorCode.INVALID_SORT_FIELD.getMessage());
//
//                    Sort.Direction direction =  pageRequest.getSort().stream().findFirst().get().getDirection();
//                    String sortKey = pageRequest.getSort().stream().findFirst().get().getProperty();
//                    String sortValue = ProductInfo.SORT_FIELDS.stream().filter(sf -> sf.getKey().equals(sortKey)).findFirst().get().getValue();
//                    pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.by(direction,sortValue));
//            }

            List<Double> prices = Stream.of(priceStr.split(",")).map(Double::parseDouble).toList();
            SearchHits<ProductInfo> productInfos = customProductInfoRepo.searchBySeller(keyword, status, categoryId, prices, pageRequest);

            total = productInfos.getTotalHits();
            pageNumber = pageRequest.getPageNumber();
            list = productInfos.map(SearchHit::getContent).toList();
        } catch (NumberFormatException e) {
            throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_PRICE_FILTER.getCode(), e.getMessage());

        }

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public PaginationResult<ProductInfo> searchByAdmin(String keyword, String status, long categoryId, String priceStr, PageRequest pageRequest) {
        long total = 0;
        long pageNumber = 0;
        List<ProductInfo> list = null;
        try {

//            if(!pageRequest.getSort().isEmpty()) {
//                String sortField = pageRequest.getSort().stream().findFirst().get().getProperty();
//                if(ProductInfo.SORT_FIELDS.stream().noneMatch(sf-> sf.getKey().equals(sortField)))
//                    throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_SORT_FIELD.getCode(), CommonErrorCode.INVALID_SORT_FIELD.getMessage());
//
//                    Sort.Direction direction =  pageRequest.getSort().stream().findFirst().get().getDirection();
//                    String sortKey = pageRequest.getSort().stream().findFirst().get().getProperty();
//                    String sortValue = ProductInfo.SORT_FIELDS.stream().filter(sf -> sf.getKey().equals(sortKey)).findFirst().get().getValue();
//                    pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.by(direction,sortValue));
//            }

            List<Double> prices = Stream.of(priceStr.split(",")).map(Double::parseDouble).toList();
            SearchHits<ProductInfo> productInfos = customProductInfoRepo.searchByAdmin(keyword, status, categoryId, prices, pageRequest);

            total = productInfos.getTotalHits();
            pageNumber = pageRequest.getPageNumber();
            list = productInfos.map(SearchHit::getContent).toList();
        } catch (NumberFormatException e) {
            throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_PRICE_FILTER.getCode(), e.getMessage());

        }

        return new PaginationResult<>(total, pageNumber, list);
    }

    @Override
    public ProductInfo findById(long id) {
        return productInfoRepo.findById(id).orElseThrow(
                () -> new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.PRODUCT_NOT_FOUND.getCode(), CommonErrorCode.PRODUCT_NOT_FOUND.getMessage())
        );
    }

    @Override
    public long saveProductInfo(ProductInfo productInfo) {
        return productInfoRepo.save(productInfo).getId();
    }

    @Override
    public long updateInventoryProductInfo(ProductItemDetail productItem) {
        ProductInfo productInfo =  productInfoRepo.findById(productItem.getProductInfo().getId()).orElseThrow(
                () -> new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.PRODUCT_NOT_FOUND.getCode(), CommonErrorCode.PRODUCT_NOT_FOUND.getMessage())
        );
        productInfo.getProductItems().forEach(item -> {
            if(item.getId() == productItem.getId()) {
                item.setQuantityInStock(productItem.getQuantityInStock());
            }
        });
        return productInfoRepo.save(productInfo).getId();
    }

    @Override
    public long updateSales(OrderLine orderLine) {
//        SearchHits<ProductInfo> productInfo =  customProductInfoRepo.findByProductItemId(orderLine.getProductItem().getId());
//
//        if(productInfo.getTotalHits() == 0) {
//            throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.PRODUCT_NOT_FOUND.getCode(), CommonErrorCode.PRODUCT_NOT_FOUND.getMessage());
//        }
//
//        ProductInfo product = productInfo.getSearchHit(0).getContent();
        ProductInfo product = productInfoRepo.findById(orderLine.getProductItem().getProductInfo().getId()).orElseThrow(
                () -> new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.PRODUCT_NOT_FOUND.getCode(), CommonErrorCode.PRODUCT_NOT_FOUND.getMessage())
        );
        SaleInfo saleInfo = product.getSales();
        saleInfo.setTotalQuantity(saleInfo.getTotalQuantity() + orderLine.getQuantity());
        saleInfo.setOrderCount(saleInfo.getOrderCount() + 1);

        return productInfoRepo.save(product).getId();
    }

    @Override
    public long updateRatingAverage(Rating rating) {
        // TODO implement update rating average
        ProductInfo product = productInfoRepo.findById(rating.getOrderLine().getProductItem().getProductInfo().getId()).orElseThrow(
                () -> new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.PRODUCT_NOT_FOUND.getCode(), CommonErrorCode.PRODUCT_NOT_FOUND.getMessage())
        );
        //set rating average
        long orderCount = product.getSales().getOrderCount();
        product.setRatingAverage((product.getRatingAverage() * orderCount + rating.getRating()) / (orderCount + 1));
        productInfoRepo.save(product);
        return productInfoRepo.save(product).getId();
    }
}
