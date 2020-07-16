package com.hzm.demo_security.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Return<T> {
    public T data;
    public String msg;

    public Return(String msg) {
        this.msg = msg;
    }
}
