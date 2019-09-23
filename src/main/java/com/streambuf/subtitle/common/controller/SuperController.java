package com.streambuf.subtitle.common.controller;


import com.streambuf.subtitle.common.responses.ApiResponses;
import com.streambuf.subtitle.common.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuperController {

    private final static String PREFIX = "Bearer";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 成功返回
     *
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(response, object);
    }

    /**
     * 成功返回
     *
     * @return
     */
    public ApiResponses<Void> success() {
        return success(HttpStatus.OK);
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(HttpStatus status, T object) {
        return ApiResponses.<T>success(response, status, object);
    }

    /**
     * 失败返回
     */
    public <T> ApiResponses<T> failure(HttpStatus status, T object) {
        return ApiResponses.<T>success(response, status, object);
    }

    /**
     * 成功返回
     *
     * @param status
     * @return
     */
    public ApiResponses<Void> success(HttpStatus status) {
        return ApiResponses.<Void>success(response, status);
    }

    /**
     * 获取当前用户id
     */
    public Long currentUid() {
        String token = request.getHeader("Authorization").substring(7);
        return ApiUtils.currentUid(token);
    }

}