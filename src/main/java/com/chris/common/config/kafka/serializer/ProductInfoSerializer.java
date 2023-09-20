package com.chris.common.config.kafka.serializer;

import com.chris.data.elasticsearch.ProductInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
@Slf4j
public class ProductInfoSerializer implements Serializer<ProductInfo> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String s, ProductInfo data) {
        try {
            if (data == null){
                log.info("Null received at serializing Product");
                return null;
            }
            log.info("Serializing Product...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Product to byte[]");
        }
    }
}
