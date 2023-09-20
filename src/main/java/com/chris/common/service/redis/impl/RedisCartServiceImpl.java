package com.chris.common.service.redis.impl;

import com.chris.common.repo.redis.RedisAccessTokenRepo;
import com.chris.common.repo.redis.RedisCartRepo;
import com.chris.common.service.redis.RedisCartService;
import com.chris.common.utils.DataUtils;
import com.chris.data.redis.AccessToken;
import com.chris.data.redis.Cart;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RedisCartServiceImpl implements RedisCartService {

    private static final Logger logger = LoggerFactory.getLogger(RedisCartServiceImpl.class);

    private static final String CART = "Cart";

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    RedisCartRepo redisCartRepo;


    public RedisCartServiceImpl() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Override
    public void saveCart(Cart cart) {
        try {
            Optional<List<Cart>> oldCarts = getCarts(cart.getCustomerId());
            List<Cart> carts = null;
            if(oldCarts.isPresent()){
                carts = oldCarts.get();
                boolean cartExist = false;
                for(Cart oldCart : carts){
                    if(oldCart.getProductItem().getId() == cart.getProductItem().getId()){
                        oldCart.setQuantity(cart.getQuantity());
                        cartExist = true;
                        break;
                    }
                }
                if(!cartExist){
                    carts.add(cart);
                }
            }
            else{
                carts = new ArrayList<>();
                carts.add(cart);
            }
            String jsonCarts = objectMapper.writeValueAsString(carts);
            redisCartRepo.saveCart(jsonCarts, CART.trim(), String.valueOf(cart.getCustomerId()));
        } catch (Exception e) {
            logger.error("An error when save CART into redis db", e);
        }
    }

//    @Override
//    public void updateCart(Cart cart) {
//        try {
//            String jsonToken = objectMapper.writeValueAsString(cart);
//            redisCartRepo.updateCart(jsonToken, CART.trim(), String.valueOf(cart.getCustomerId()));
//        } catch (Exception e) {
//            logger.error("An error when update CART into redis db", e);
//        }
//    }

    @Override
    public Optional<List<Cart>> getCarts(long customerId) {
        try {
            Object cartsObject = redisCartRepo.getCartsByCustomer(String.valueOf(customerId), CART.trim());
            Cart[] carts = objectMapper.readValue(cartsObject.toString(), Cart[].class);

            if (DataUtils.notNull(carts)) {
                return Optional.of(List.of(carts));
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.error("An error when get CART into redis db", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteCarts(long customerId) {
        try {
            redisCartRepo.deleteCarts(String.valueOf(customerId), CART.trim());
        } catch (Exception e) {
            logger.error("An error when DELETE CART into redis db", e);
        }
    }
}
