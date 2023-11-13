package com.chris.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;

public final class JsonUtil {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = (new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));
    }

    public JsonUtil(){}

    public static <T> byte[] convertObjectToBytes(T t) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(t);
    }

    public static <T> T convertJsonToObject(String json, Class<T> outputType) throws JsonProcessingException {
        return MAPPER.readValue(json, outputType);
    }

    public static String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        return StringUtils.equals(ow.writeValueAsString(object), "null") ? null : ow.writeValueAsString(object);
    }

    public static String convertToJsonDefaultNull(Object object){
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            return StringUtils.equals(ow.writeValueAsString(object), "null") ? null : ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
