package com.chris.common.caller;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.dto.user.CustomerDTO;
import com.chris.data.dto.user.SellerDTO;
import com.chris.data.entity.product.ProductItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserServiceCaller {

    String USER_SERVICE_ENDPOINT = "http://127.0.0.1:8100/api/user-service";

    @Autowired
    private VertxCaller caller;

    public Mono<SellerDTO> getSellerInfo(long id) {
        String url = USER_SERVICE_ENDPOINT + "/user/seller/{id}";
        return caller.getMono(url, SellerDTO.class, String.valueOf(id))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CommonException) {
                        log.error(((CommonException) throwable).getMessage());
                        return Mono.error(throwable);
                    }
                    String errorMessage = String.format("Error get seller info with error %s", throwable.getLocalizedMessage());
                    log.error(errorMessage);
                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
                });
    }

    public Mono<CustomerDTO> getCustomerInfo(long id) {
        String url = USER_SERVICE_ENDPOINT + "/user/customer/{id}";
        return caller.getMono(url, CustomerDTO.class, String.valueOf(id))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CommonException) {
                        log.error(((CommonException) throwable).getMessage());
                        return Mono.error(throwable);
                    }
                    String errorMessage = String.format("Error get customer info with error %s", throwable.getLocalizedMessage());
                    log.error(errorMessage);
                    return Mono.error(new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(), errorMessage));
                });
    }

}
