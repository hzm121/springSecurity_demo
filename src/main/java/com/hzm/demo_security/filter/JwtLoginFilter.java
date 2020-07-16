package com.hzm.demo_security.filter;

import com.hzm.demo_security.Authentication.JwtAuthenticationToken;
import com.hzm.demo_security.JwtAuthenticationHandler.JwtAuthenticationFailureHandler;
import com.hzm.demo_security.JwtAuthenticationHandler.JwtAuthenticationSuccessHandler;
import com.hzm.demo_security.utils.HttpUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    public JwtLoginFilter(AuthenticationManager authenticationManager, RequestMatcher requestMatcher) {
        setAuthenticationManager(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(requestMatcher);
        this.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String body = HttpUtils.getBody(request);
        String username = HttpUtils.getValueFromJson(body, usernameParameter);
        String password = HttpUtils.getValueFromJson(body, passwordParameter);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        this.getRememberMeServices().loginSuccess(request, response, authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.logger.debug("Authentication request failed: " + failed.toString(), failed);
        this.logger.debug("Updated SecurityContextHolder to contain null Authentication");

        this.getRememberMeServices().loginFail(request, response);
        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
