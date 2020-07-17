package com.hzm.demo_security;

import com.hzm.demo_security.entity.User;
import com.hzm.demo_security.mapstruct.UserMapper;
import com.hzm.demo_security.service.UserService;
import com.hzm.demo_security.userDetailService.JdbcUserDetailService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.hzm.demo_security.mapper")
public class DemoSecurityApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(DemoSecurityApplication.class, args);
    }

    @Resource
    private UserDetailsService jdbcUserDetailService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String hzm = new BCryptPasswordEncoder().encode("hzm");
    }
}
