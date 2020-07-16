package com.hzm.demo_security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebApiReturnEnum {
    SUCCESS("0","success"),
    FAILURE("-1","failure");
    private String code;
    private String msg;

}
