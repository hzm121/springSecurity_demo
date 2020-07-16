package com.hzm.demo_security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class JwtUtils {
    @Value("${jwt.secret}")
    private static String secret;
    @Value("${jwt.expiration}")
    private static int expiration;

    private static final String AUTHORITIES = "authorities";
    private static final String CREATED = "created";

    public static String generateJwtToken(Authentication authentication) throws IllegalArgumentException {
        if (authentication == null) {
            throw new IllegalArgumentException("authentication is null");
        }

        String username = authentication.getPrincipal() == null ? "" : authentication.getPrincipal().toString();
        Claims claims = new DefaultClaims();
        claims.setSubject(username);
        claims.put(CREATED, LocalDateTime.now());
        claims.put(AUTHORITIES, authentication.getAuthorities());

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(TimeUtils.plus(LocalDateTime.now(), expiration, ChronoUnit.DAYS))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
