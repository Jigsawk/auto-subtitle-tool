package com.streambuf.subtitle.user.model.vo;

import com.streambuf.subtitle.common.convert.Convert;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO extends Convert implements Serializable {
    private String username;
    private String password;
}
