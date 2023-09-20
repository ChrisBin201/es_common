package com.chris.common.config.jwt;

import com.chris.common.constant.JwtExceptionEnum;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    public String validateError = new String();

    @Value("${app.jwtSecret}")
    private String JWT_SECRET;

    @Value("${app.jwtExpirationInMs}")
    private long JWT_TOKEN_VALIDITY;

    public Date getJwtTokenValidity() {
        Date now = new Date();
        return new Date(now.getTime() + JWT_TOKEN_VALIDITY);
    }
    public long getExpirationInMs(){
        return JWT_TOKEN_VALIDITY;
    }
    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public String getUUID(String token){
        Claims claims = getAllClaimsFromToken(token, true);
        return claims.get("uuid", String.class);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token,false);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token, boolean ignoreExpired) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch ( ExpiredJwtException e ) {
            if(ignoreExpired)
                return e.getClaims();
            else throw e;
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
    public String generateToken(String username, String role, List<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("permissions", permissions);
        return doGenerateToken(claims, username);
    }

    public String generateToken(String username, String role, List<String> permissions, String uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("permissions", permissions);
        claims.put("uuid", uuid);
        return doGenerateToken(claims, username);
    }

    public String generateAccessToken(String username, String uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", uuid);
        return doGenerateToken(claims, username);
    }


//    public String generateRefreshToken(String username, String uuid) {
//        Date now = new Date();
//        Date expriryDate = new Date(now.getTime() + JWT_REFRESH_TOKEN_VALIDITY);
//        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expriryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .claim("uuid", uuid)
//                .compact();
//    }

    public String generateToken(String uuid) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, uuid);
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expriryDate = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now)
                .setExpiration(expriryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    // validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(JWT_SECRET).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature", e);
            this.validateError = JwtExceptionEnum.INVALID_JWT_SIGNATURE.getName();
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token ");
            this.validateError = JwtExceptionEnum.INVALID_JWT_TOKEN.getName();

        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token");
            this.validateError = JwtExceptionEnum.EXPIRED_JWT_TOKEN.getName();

        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT Token");
            this.validateError = JwtExceptionEnum.UNSUPPORT_JWT_TOKEN.getName();

        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty");
            this.validateError = JwtExceptionEnum.JWT_CLAIMS_EMPTY.getName();

        }
        return false;
    }
}