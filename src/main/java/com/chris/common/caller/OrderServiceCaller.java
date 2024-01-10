package com.chris.common.caller;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.dto.order.CheckoutInfo;
import com.chris.data.entity.order.Invoice;
import com.chris.data.entity.order.OrderLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OrderServiceCaller {

    String ORDER_SERVICE_ENDPOINT = "http://127.0.0.1:8400/api/order-service";

    @Autowired
    private VertxCaller caller;

//    public Mono<OrderLine> findOrderLineById(long id) {
//        String url = ORDER_SERVICE_ENDPOINT + "/order/get-order-line/{id}";
//        return caller.getMono(url, OrderLine.class, String.valueOf(id))
//                .onErrorResume(throwable -> {
//                    if (throwable instanceof CommonException) {
//                        log.error(((CommonException) throwable).getMessage());
//                        return Mono.error(throwable);
//                    }
//                    String errorMessage = String.format("Error get order line with error %s", throwable.getLocalizedMessage());
//                    log.error(errorMessage);
//                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
//                });
//    }

    public Mono<Invoice> getCheckoutInfo(long id) {
        String url = ORDER_SERVICE_ENDPOINT + "/order/invoice/{id}/checkout-info";
        return caller.getMono(url, Invoice.class, String.valueOf(id))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CommonException) {
                        log.error(((CommonException) throwable).getMessage());
                        return Mono.error(throwable);
                    }
                    String errorMessage = String.format("Error get invoice info with error %s", throwable.getLocalizedMessage());
                    log.error(errorMessage);
                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
                });
    }

}
