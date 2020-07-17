package com.hzm.demo_security.service;

import com.hzm.demo_security.entity.User;
import com.hzm.demo_security.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hzm
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserMapper userMapper;
    @Override
    public User getUserByName(String username) {
        return userMapper.selectByName(username);
    }
}
