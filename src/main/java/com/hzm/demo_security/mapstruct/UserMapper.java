package com.hzm.demo_security.mapstruct;

import com.hzm.demo_security.dto.RbacUserdetail;
import com.hzm.demo_security.entity.User;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RbacUserdetail userToRabcUserDetails(User user);
}
