package com.streambuf.subtitle.exception;


import com.streambuf.subtitle.common.enums.ErrorCode;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;


public class ApiException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final ErrorCode errorCode;

    public ApiException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.msg());
        this.errorCode = errorCodeEnum.convert();
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;

    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
