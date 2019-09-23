package com.streambuf.subtitle.common.responses;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class ApiResponses<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 不需要返回结果
     *
     * @param status
     */
    public static ApiResponses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        return SuccessResponses.<Void>builder().status(status.value()).build();

    }

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> ApiResponses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        return SuccessResponses.<T>builder().status(status.value()).result(object).build();
    }
}
