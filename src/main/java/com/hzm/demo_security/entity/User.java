package com.hzm.demo_security.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String userName;
    private String password;
    private Integer roleId;
    private Integer locked;
    private LocalDateTime created;
    private LocalDateTime updated;
}
