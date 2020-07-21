package com.hzm.demo_security.config;

import com.hzm.demo_security.filter.JwtLoginFilter;
import com.hzm.demo_security.filter.JwtTokenAuthenticationFilter;
import com.hzm.demo_security.provider.JwtAuthenticationProvider;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //登录的URL
               // .antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        http.addFilterBefore(new JwtLoginFilter(this.authenticationManager(), new AntPathRequestMatcher("/login", "POST")),
                UsernamePasswordAuthenticationFilter.class);
        List<AntPathRequestMatcher> antPathMatchers = Arrays.asList(new AntPathRequestMatcher("/swagger**/**"),
                new AntPathRequestMatcher("/webjars/**"),
                new AntPathRequestMatcher("/v2/**")
        );
        http.addFilterBefore(new JwtTokenAuthenticationFilter(this.authenticationManager(),antPathMatchers),UsernamePasswordAuthenticationFilter.class);
    }

    @Resource
    private UserDetailsService jdbcUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationProvider(jdbcUserDetailService));
    }

}
