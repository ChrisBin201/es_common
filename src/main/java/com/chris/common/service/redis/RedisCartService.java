package com.chris.common.service.redis;

import com.chris.data.redis.AccessToken;
import com.chris.data.redis.Cart;

import java.util.List;
import java.util.Optional;

public interface RedisCartService {

    void saveCart(Cart cart);
    void updateCart(Cart cart);
    Optional<List<Cart>> getCarts(long customerId);

    void deleteCart(long customerId,long productItemId);
    void deleteCarts(long customerId);

}
