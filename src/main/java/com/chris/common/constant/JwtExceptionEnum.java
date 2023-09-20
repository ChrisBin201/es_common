package com.chris.common.constant;

public enum JwtExceptionEnum {

    INVALID_JWT_SIGNATURE("Invalid JWT signature"),
    INVALID_JWT_TOKEN("Invalid JWT Token"),
    EXPIRED_JWT_TOKEN("Expired JWT Token"),
    UNSUPPORT_JWT_TOKEN("Unsupported JWT Token"),
    JWT_CLAIMS_EMPTY("JWT claims string is empty");

    final String typeValue;

    JwtExceptionEnum(final String typeValue) {
        this.typeValue = typeValue;
    }

    public String getName() {
        return name();
    }

    public String getValue() {
        return typeValue;
    }

    public Integer getOrdinal(String name) {
        return JwtExceptionEnum.valueOf(name).ordinal();
    }

    @Override
    public String toString() {
        return name();
    }
}
