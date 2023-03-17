package com.inq.wishhair.wesharewishhair.auth.utils;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token-validity}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidityInMilliseconds) {

        this.secretKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, refreshTokenValidityInMilliseconds);
    }

    private String createToken(Long userId, long validityInMilliseconds) {
        //payload
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        //Expires At
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiration = now.plusSeconds(validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Long getId(String token) {
        return getClaims(token)
                .getBody()
                .get("id", Long.class);
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            Date expiration = claims.getBody().getExpiration();
            Date now = new Date();
            return expiration.after(now);
        } catch (ExpiredJwtException e) {
            throw new WishHairException(ErrorCode.AUTH_EXPIRED_TOKEN);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new WishHairException(ErrorCode.AUTH_INVALID_TOKEN);
        }
    }
}
