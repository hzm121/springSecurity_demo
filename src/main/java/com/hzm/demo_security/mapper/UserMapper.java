package com.hzm.demo_security.mapper;

import com.hzm.demo_security.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByName(String username);
}
