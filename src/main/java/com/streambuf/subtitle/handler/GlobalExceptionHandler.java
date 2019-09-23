package com.streambuf.subtitle.handler;


import com.streambuf.subtitle.common.ErrorCode;
import com.streambuf.subtitle.common.responses.FailedResponse;
import com.streambuf.subtitle.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * 统一处理异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<FailedResponse> handlerException(RuntimeException e) {

        if(e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            ErrorCode errorCode = apiException.getErrorCode();
            int httpStatus = errorCode.getHttpCode();
            return ResponseEntity.status(httpStatus).body(
                    FailedResponse.builder()
                            .status(httpStatus)
                            .msg(errorCode.getMsg())
                            .exception(apiException.toString())
                            .build());
        }

        return ResponseEntity.badRequest().body(
                FailedResponse
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .msg(e.getMessage())
                        .exception(e.toString())
                        .time(LocalDateTime.now())
                        .build());
    }
}
