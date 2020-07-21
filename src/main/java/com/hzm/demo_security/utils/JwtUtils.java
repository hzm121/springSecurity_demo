package com.hzm.demo_security.utils;

import com.hzm.demo_security.Authentication.JwtAuthenticationToken;
import com.hzm.demo_security.constant.JwtConstant;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import jdk.nashorn.internal.parser.TokenKind;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtils {


    /**
     * 生成JwtToken
     *
     * @param authentication
     * @return
     * @throws IllegalArgumentException
     */
    public static String generateJwtToken(Authentication authentication) throws IllegalArgumentException {
        if (authentication == null) {
            throw new IllegalArgumentException("authentication is null");
        }

        String username = authentication.getPrincipal() == null ? "" : ((UserDetails) authentication.getPrincipal()).getUsername();
        Claims claims = new DefaultClaims();
        claims.setSubject(username);
        claims.put(JwtConstant.CREATED, LocalDateTime.now());
        claims.put(JwtConstant.USERNAME, username);
        claims.put(JwtConstant.AUTHORITIES, authentication.getAuthorities());

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(TimeUtils.plus(LocalDateTime.now(), JwtConstant.EXPIRATION, ChronoUnit.DAYS))
                .signWith(SignatureAlgorithm.HS256, JwtConstant.SECRET)
                .compact();
    }

    /**
     * 从请求头获取token
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws JwtException
     */
    public static JwtAuthenticationToken getJwtTokenFromHeader(HttpServletRequest request) throws ServletException, JwtException {
        String authentication = request.getHeader(JwtConstant.AUTHENTICATION_HEADER);
        checkAuthenticationIfBearerToken(authentication);

        if (StringUtils.isEmpty(authentication)) {
            throw new ServletException("Header中Authentication不能为空");
        }

        String jwtToken = authentication.substring(JwtConstant.BEARER_TOKEN_HEAD.length());
        Claims claims = getClaimsFromToken(jwtToken);
        if (claims == null) {
            throw new JwtException("信息载体不能为空");
        }
        Object claim = claims.get(JwtConstant.AUTHORITIES);
        ArrayList<SimpleGrantedAuthority> authorities = null;

        if (claim != null && claim instanceof List) {
            List auth = (List) claim;

            authorities = (ArrayList<SimpleGrantedAuthority>) (auth).stream()
                    .map(p -> {
                        return ((Map) p).get(JwtConstant.SPRING_AUTHORITY);
                    })
                    .map(s -> {
                        return new SimpleGrantedAuthority((String) s);
                    })
                    .collect(Collectors.toList());
        }

        return new JwtAuthenticationToken(
                jwtToken,
                claims.get(JwtConstant.USERNAME),
                null,
                authorities);
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public static boolean isTokenNonExpired(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new JwtException("Token不能为空");
        }

        Claims claims = getClaimsFromToken(token);

        if (claims == null) {
            throw new JwtException("载体信息不能为空");
        }

        Date expiration = claims.getExpiration();
        return LocalDateTime.now().isBefore(TimeUtils.dateToLocalDateTime(expiration));
    }


    /**
     * 检查认证信息
     *
     * @param token
     * @throws AuthenticationException
     */
    private static void checkAuthenticationIfBearerToken(String token) throws AuthenticationException {
        if (StringUtils.isEmpty(token) || !token.startsWith(JwtConstant.BEARER_TOKEN_HEAD)) {
            throw new com.hzm.demo_security.exception.JwtException("Token不为空且需要以Bearer开头");
        }
    }

    private static Claims getClaimsFromToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return Jwts.parser()
                .setSigningKey(JwtConstant.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
