package com.hzm.demo_security.filter;

import com.hzm.demo_security.Authentication.JwtAuthenticationToken;
import com.hzm.demo_security.enums.WebApiReturnEnum;
import com.hzm.demo_security.utils.HttpUtils;
import com.hzm.demo_security.utils.JwtUtils;
import com.hzm.demo_security.vo.Return;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.print.DocFlavor;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {
    private List<AntPathRequestMatcher> whiteList;

    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager, List<AntPathRequestMatcher> whiteList) {
        super(authenticationManager);
        this.whiteList = whiteList;
    }

    private void checkWhiteList(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        JwtAuthenticationToken jwtToken = null;
        try {
            boolean skip = whiteList.stream().anyMatch(a -> {
                return a.matches(request);
            });
            if (skip) {
                chain.doFilter(request, response);
            }else {
                jwtToken = JwtUtils.getJwtTokenFromHeader(request);
                boolean nonExpired = JwtUtils.isTokenNonExpired(jwtToken.getToken());
                if (nonExpired) {
                    this.onSuccessfulAuthentication(request, response, jwtToken);
                } else {
                    this.onUnsuccessfulAuthentication(request, response, null);
                }
                chain.doFilter(request, response);
            }
        } catch (AuthenticationException e) {
            this.onUnsuccessfulAuthentication(request, response, e);
        }


    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        //将认证信息塞入上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        log.error("Authentication request failed: " + failed.toString(), failed);
        HttpUtils.printJsonStrWithResponse(response, new Return<String>("token校验失败", WebApiReturnEnum.FAILURE.toString()));
    }
}
