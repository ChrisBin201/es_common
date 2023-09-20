package com.chris.common.constant;

import org.springframework.stereotype.Component;

@Component
public class MessageEvent {

    public static final String PRODUCT_TOPIC = "product_";
    public static final String UPLOAD_PRODUCT = PRODUCT_TOPIC + "upload";
    public static final String REVIEW_PRODUCT = PRODUCT_TOPIC + "review";
}
