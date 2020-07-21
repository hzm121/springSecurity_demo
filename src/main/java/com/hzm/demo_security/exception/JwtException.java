package com.hzm.demo_security.exception;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtException extends AuthenticationException {

    public JwtException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtException(String msg) {
        super(msg);
    }
}
