package com.hzm.demo_security.service;

import com.hzm.demo_security.entity.User;

/**
 * @author hzm
 */
public interface UserService {
    User getUserByName(String username);
}
