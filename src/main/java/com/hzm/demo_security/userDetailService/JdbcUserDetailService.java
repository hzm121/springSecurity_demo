package com.hzm.demo_security.userDetailService;

import com.hzm.demo_security.dto.RbacUserdetail;
import com.hzm.demo_security.entity.User;
import com.hzm.demo_security.mapstruct.UserMapper;
import com.hzm.demo_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class JdbcUserDetailService implements UserDetailsService {
    @Resource
    private UserService userServiceImpl;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByName(username);
        return userMapper.userToRabcUserDetails(user);
    }
}
