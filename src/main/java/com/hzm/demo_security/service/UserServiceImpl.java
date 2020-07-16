package com.hzm.demo_security.service;

import com.hzm.demo_security.entity.User;
import com.hzm.demo_security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserByName(String username) {
        return userMapper.selectByName(username);
    }
}
