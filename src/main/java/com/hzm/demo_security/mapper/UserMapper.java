package com.hzm.demo_security.mapper;

import com.hzm.demo_security.entity.User;

public interface UserMapper {
    User selectByName(String username);
}
