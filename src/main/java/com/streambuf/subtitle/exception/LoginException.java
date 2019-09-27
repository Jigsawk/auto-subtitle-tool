package com.streambuf.subtitle.exception;


import com.streambuf.subtitle.common.enums.ErrorCode;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;

public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final ErrorCode errorCode;

    public LoginException(ErrorCodeEnum e) {
        super(e.msg());
        this.errorCode = e.convert();
    }
}
