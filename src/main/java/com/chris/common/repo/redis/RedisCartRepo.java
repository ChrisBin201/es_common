package com.chris.common.repo.redis;

import com.chris.data.redis.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisCartRepo {
    private static final Logger logger = LoggerFactory.getLogger(RedisCartRepo.class);

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;


    public RedisCartRepo(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void saveCart(String jsonObject, String type, String customerIdField) {
        try {
            hashOperations.put(type, customerIdField, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    };

    public void updateCart(String jsonObject, String type, String customerIdField) {
        saveCart(jsonObject, type, customerIdField);
    };

//    public Object getCart(String customerIdField, String type) {
//        return hashOperations.get(type,  customerIdField);
//    };

    public Object getCartsByCustomer(String customerIdField, String type) {
        return (Object) hashOperations.get(type,  customerIdField);
    };

    public void deleteCarts(String customerIdField, String type) {
        hashOperations.delete(type, customerIdField);
    };

}
