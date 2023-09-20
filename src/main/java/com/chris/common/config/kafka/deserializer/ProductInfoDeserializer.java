package com.chris.common.config.kafka.deserializer;

import com.chris.data.elasticsearch.ProductInfo;
import com.chris.data.entity.product.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ProductInfoDeserializer implements Deserializer<ProductInfo> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ProductInfo deserialize(String s, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing Product");
                return null;
            }
            System.out.println("Deserializing Product...");
            return objectMapper.readValue(new String(data, "UTF-8"), ProductInfo.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Product");
        }
    }
}
