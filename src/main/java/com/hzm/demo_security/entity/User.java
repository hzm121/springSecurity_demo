package com.hzm.demo_security.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String userName;
    private Integer roleId;
    private LocalDateTime created;
    private LocalDateTime updated;
}
