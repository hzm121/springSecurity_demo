package com.hzm.demo_security.JwtAuthenticationHandler;

import com.hzm.demo_security.enums.WebApiReturnEnum;
import com.hzm.demo_security.utils.HttpUtils;
import com.hzm.demo_security.utils.JwtUtils;
import com.hzm.demo_security.vo.Return;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpUtils.printJsonStrWithResponse(httpServletResponse, new Return<String>(WebApiReturnEnum.FAILURE.toString()));
    }
}
