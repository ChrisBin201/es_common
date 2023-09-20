package com.chris.common.handler;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
    // 400
    BAD_REQUEST(400, "BAD_REQUEST", "Bad request"),
    // 401
    UNAUTHORIZED(401, "UNAUTHORIZED", "Unauthorized"),
    // 403
    FORBIDDEN(403, "FORBIDDEN", "Forbidden"),
    // 404
    NOT_FOUND(404, "NOT_FOUND", "Not found"),
    // 405
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", "Method not allowed"),
    // 406
    NOT_ACCEPTABLE(406, "NOT_ACCEPTABLE", "Not acceptable"),
    // 408
    REQUEST_TIMEOUT(408, "REQUEST_TIMEOUT", "Request timeout"),
    // 409
    CONFLICT(409, "CONFLICT", "Conflict"),
    // 410
    GONE(410, "GONE", "Gone"),
    // 411
    LENGTH_REQUIRED(411, "LENGTH_REQUIRED", "Length required"),
    // 412
    PRECONDITION_FAILED(412, "PRECONDITION_FAILED", "Precondition failed"),
    // 413
    PAYLOAD_TOO_LARGE(413, "PAYLOAD_TOO_LARGE", "Payload too large"),
    // 415
    UNSUPPORTED_MEDIA_TYPE(415, "UNSUPPORTED_MEDIA_TYPE", "Unsupported media type"),
    // 416
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "REQUESTED_RANGE_NOT_SATISFIABLE", "Requested range not satisfiable"),
    // 417
    EXPECTATION_FAILED(417, "EXPECTATION_FAILED", "Expectation failed"),
    // 422
    UNPROCESSABLE_ENTITY(422, "UNPROCESSABLE_ENTITY", "Unprocessable entity"),
    // 429
    TOO_MANY_REQUESTS(429, "TOO_MANY_REQUESTS", "Too many requests"),
    // 500
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "Internal server error"),
    // 501
    NOT_IMPLEMENTED(501, "NOT_IMPLEMENTED", "Not implemented"),
    // 502
    BAD_GATEWAY(502, "BAD_GATEWAY", "Bad gateway"),
    // 503
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE", "Service unavailable"),
    // 504
    GATEWAY_TIMEOUT(504, "GATEWAY_TIMEOUT", "Gateway timeout"),
    // 505
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP_VERSION_NOT_SUPPORTED", "Http version not supported"),

    //CUSTOM ERROR

    //PRODUCT_SERVICE
    INVALID_COUNTRY_ADDRESS( "INVALID_COUNTRY_ADDRESS", "Invalid detail address"),
    INVALID_PRODUCT( "INVALID_PRODUCT", "Product is invalid"),

    INVALID_PRODUCT_STATUS( "INVALID_PRODUCT_STATUS", "Product status is invalid"),

    //SEARCH SERVICE
    INVALID_SEARCH( "INVALID_SEARCH", "Search is invalid"),
    INVALID_PRICE_FILTER( "INVALID_PRICE_FILTER", "Price filter is invalid"),

    INVALID_SORT_FIELD( "INVALID_SORT_FIELD", "Sort field is invalid");


    private  int status;
    private final String code;
    private final String message;

    CommonErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
