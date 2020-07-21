package com.hzm.demo_security.constant;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstant {
    public static final String SECRET = "HJAHDJAHJHD";
    public static final long EXPIRATION = 1 * 60 * 60;
    public static final String AUTHENTICATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_HEAD = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String SPRING_AUTHORITY = "authority";
    public static final String CREATED = "created";
    public static final String USERNAME = "username";

}
