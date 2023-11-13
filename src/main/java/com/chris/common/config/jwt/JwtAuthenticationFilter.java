package com.chris.common.config.jwt;

import com.chris.common.config.UserDetailsInfo;
import com.chris.common.constant.JwtExceptionEnum;
import com.chris.common.handler.authentication.JwtTokenNotValidException;
import com.chris.common.utils.DataUtils;
import com.chris.data.redis.AccessToken;
import com.chris.common.service.redis.RedisAccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisAccessTokenService redisService;
//    private final UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestToken = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        Optional<AccessToken> token;

            if (DataUtils.notNullOrEmpty(requestToken)) {
                if (requestToken.startsWith("Bearer")) {
                    jwtToken = requestToken.substring(7);
//                    username = jwtTokenProvider.getUsernameFromToken(jwtToken);
                    String uuid = jwtTokenProvider.getUUID(jwtToken);
                    log.info("uuid: {}", uuid);
                    token = redisService.getAccessToken(uuid);
                    if (token.isPresent()) {
                        boolean isValidated = jwtTokenProvider.validateToken(token.get().getAccessToken());
                        if (!isValidated) {
                            if (jwtTokenProvider.validateError.equals(JwtExceptionEnum.EXPIRED_JWT_TOKEN.getName())) {
                                redisService.deleteAccessToken(uuid);
                                throw new JwtTokenNotValidException("JWT token is expired ");
                            }
                        }
                        AccessToken accessToken = token.get();
                        List<GrantedAuthority> authorities = accessToken.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role))
                                .collect(Collectors.toList());

                        UserDetailsInfo userDetails = UserDetailsInfo.builder()
                                .id(accessToken.getId())
                                .username(accessToken.getUsername())
                                .authorities(authorities)
                                .build();

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // after setting the authentication in the context, we specify that
                        // the current user is authenticated. So it passes the spring security configuration successfully
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    } else {
                        throw new JwtTokenNotValidException("JWT token not valid");
                    }
                } else if(requestToken.startsWith("Internal")) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("Internal", null, List.of(new SimpleGrantedAuthority("INTERNAL"))));
                }


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (DataUtils.notNullOrEmpty(username)) {
                    // call from service in message bus

                }

            }


        //test
//        UserDetailsInfo userDetails = UserDetailsInfo.builder()
//                                .id(1)
//                                .username("admin")
//                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
//                                .build();
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        // after setting the authentication in the context, we specify that
//        // the current user is authenticated. So it passes the spring security configuration successfully
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        ContentCachingResponseWrapper responseCachingWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, response);

        // copy body to response
        responseCachingWrapper.copyBodyToResponse();

    }
}
