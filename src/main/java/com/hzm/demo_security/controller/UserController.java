package com.hzm.demo_security.controller;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
public class UserController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/Hello")
    public String Hello() {
        return "hello";
    }

}