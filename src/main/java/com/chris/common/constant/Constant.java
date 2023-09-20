package com.chris.common.constant;

import org.springframework.stereotype.Component;

@Component
public class Constant {
    public static final String ACTION_USER = "ACTION_USER";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";

    public static final long JWT_AUTH_TOKEN_VALIDITY = 604800000 ;
    public static final String JWT_SECRET_TOKEN_VALIDITY = "9a02115a835ee03d5fb83cd8a468ea33e4090aaaec87f53c9fa54512bbef4db8dc656c82a315fa0c785c08b0134716b81ddcd0153d2a7556f2e154912cf5675f";


}
