package com.chris.data.redis;

import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@RedisHash(value = "accessToken")
public class AccessToken {

    @Indexed
    private long id;
    private String accessToken;

    @Indexed
    @Searchable
    @NonNull
    private String username;

    @Indexed
    @Searchable
    private Set<String> roles;

    @Indexed
    @NonNull
    @Searchable
    private String uuid;

    @Indexed
    @TimeToLive(unit = TimeUnit.SECONDS)
    @NonNull
    private Date expiredTime;

    @Indexed
    @Searchable
    @NonNull
    private String serviceName;
}
