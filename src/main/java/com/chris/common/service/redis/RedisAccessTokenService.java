package com.chris.common.service.redis;

import com.chris.data.redis.AccessToken;

import java.util.Optional;

public interface RedisAccessTokenService {
    void saveAccessToken(AccessToken token);
    void updateAccessToken(AccessToken token);
    Optional<AccessToken> getAccessToken(String uuid);
    void deleteAccessToken(String uuid);
//    void saveRefreshToken(RefreshToken token);
//    void updateRefreshToken(RefreshToken token);
//    Optional<RefreshToken> getRefreshToken(String uuid);
//    void deleteRefreshToken(String uuid);
    String generateUUIDVersion1();

}
