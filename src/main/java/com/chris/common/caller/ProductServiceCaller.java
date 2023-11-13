package com.chris.common.caller;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.utils.JsonUtil;
import com.chris.data.dto.ResponseData;
import com.chris.data.dto.order.OrderLineDTO;
import com.chris.data.entity.order.OrderLine;
import com.chris.data.entity.order.sub.ProductItemDetail;
import com.chris.data.entity.product.ProductItem;
import io.vertx.core.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ProductServiceCaller {

    String PRODUCT_SERVICE_ENDPOINT = "http://127.0.0.1:8200/api/product-service";

    @Autowired
    private VertxCaller caller;

    public Mono<ProductItemDetail> findProductItemById(long id) {
        String url = PRODUCT_SERVICE_ENDPOINT + "/product-item/{id}";
        return caller.getMono(url, ProductItemDetail.class, String.valueOf(id))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CommonException) {
                        log.error(((CommonException) throwable).getMessage());
                        return Mono.error(throwable);
                    }
                    String errorMessage = String.format("Error get product item with error %s", throwable.getLocalizedMessage());
                    log.error(errorMessage);
                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
                });
    }

    public Mono<List<ProductItemDetail>> checkInventoryManyProductItem(List<OrderLineDTO> orderLines) {
        String url = PRODUCT_SERVICE_ENDPOINT + "/product-item";
        OrderLineDTO[]  orderLineArray = orderLines.toArray(OrderLineDTO[]::new);
        log.info("orderLineArray: {}", orderLineArray);
        return caller.requestToMono(HttpMethod.POST,url, orderLineArray, OrderLineDTO[].class, ProductItemDetail[].class)
                .flatMap(productItems -> Mono.just(List.of(productItems)))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CommonException) {
                        log.error(((CommonException) throwable).getMessage());
                        return Mono.error(throwable);
                    }
                    String errorMessage = String.format("Error get many product items with error %s", throwable.getLocalizedMessage());
                    log.error(errorMessage);
                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
                });
    }
}
