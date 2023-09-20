package com.chris.common.config.jwt;

import com.chris.common.constant.Constant;
import com.chris.common.utils.DataUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class JwtAuthenticationResponse {

    private String uuid;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    private String tokenType = "Bearer";
    private boolean success = false;
    private String username;
    // private UserDetails user;
    private List<String> role;
    private List<String> permissions;

    public JwtAuthenticationResponse(String accessToken, long expiresIn, String uuid, UserDetails userDetails ) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.uuid = uuid;
        this.success = true;
        // this.user = userDetails;
        if (userDetails != null) {
            this.username = userDetails.getUsername();
            if (DataUtils.notNullOrEmpty(userDetails.getAuthorities())) {
                role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                permissions = role;
            }
        }
    }

    public JwtAuthenticationResponse(String accessToken, String username, List<String> role) {
        this.accessToken = accessToken;
        this.expiresIn = Constant.JWT_AUTH_TOKEN_VALIDITY;
        this.success = true;
        this.username = username;
        this.role = role;
        this.permissions = role;

    }

    public JwtAuthenticationResponse(String accessToken, String username, String uuid) {
//        this.uuid = uuid;
//        JwtTokenResponse tokenResponse = new JwtTokenResponse();
//        tokenResponse.setAccessToken(accessToken);
//        tokenResponse.setExpiresIn(JWT_AUTH_TOKEN_VALIDITY);
        this.setUuid(uuid);
        this.setAccessToken(accessToken);
        this.success = true;
        this.username = username;

    }
}
