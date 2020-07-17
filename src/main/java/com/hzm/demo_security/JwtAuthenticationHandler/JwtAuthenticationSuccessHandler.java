package com.hzm.demo_security.JwtAuthenticationHandler;

import com.hzm.demo_security.enums.WebApiReturnEnum;
import com.hzm.demo_security.utils.HttpUtils;
import com.hzm.demo_security.utils.JwtUtils;
import com.hzm.demo_security.vo.Return;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sun.net.www.protocol.http.AuthenticationHeader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = JwtUtils.generateJwtToken(authentication);
        HttpUtils.printJsonStrWithResponse(httpServletResponse,new Return<String>(token,"认证成功"));
    }
}
