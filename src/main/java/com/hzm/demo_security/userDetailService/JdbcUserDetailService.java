package com.hzm.demo_security.userDetailService;

import com.hzm.demo_security.dto.RbacUserdetail;
import com.hzm.demo_security.entity.User;
import com.hzm.demo_security.mapstruct.UserMapper;
import com.hzm.demo_security.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;

@Component
public class JdbcUserDetailService implements UserDetailsService{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        RbacUserdetail rbacUserdetail = new RbacUserdetail(user.getUserName(),
                user.getPassword(),new HashSet<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        return rbacUserdetail;
    }
}

