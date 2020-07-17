package com.hzm.demo_security.mapstruct;

import com.hzm.demo_security.dto.RbacUserdetail;
import com.hzm.demo_security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
