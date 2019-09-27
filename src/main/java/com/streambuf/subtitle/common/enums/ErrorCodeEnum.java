package com.streambuf.subtitle.common.enums;

import com.streambuf.subtitle.common.enums.ErrorCode;

import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpServletResponse;

public enum ErrorCodeEnum {


    /**
     * 400
     */
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, true, "请求参数错误或不完整"),
    /**
     * JSON格式错误
     */
    JSON_FORMAT_ERROR(HttpServletResponse.SC_BAD_REQUEST, true, "JSON格式错误"),

    /**
     * 不支持的视频格式
     */
    NOT_SUPPORT_CONTENT_TYPE(HttpServletResponse.SC_BAD_REQUEST, true, "不支持的文件格式"),

    /**
     * 401
     */
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, true, "请先进行认证"),
    AUTHENTICATION_EXPIRE(HttpServletResponse.SC_UNAUTHORIZED, true, "登录凭证已过期，请重新登录"),
    /**
     * 403
     */
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, true, "无权查看"),
    /**
     * 404
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该路径"),

    /**
     * 405
     */
    METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, true, "请求方式不支持"),
    /**
     * 406
     */
    NOT_ACCEPTABLE(HttpServletResponse.SC_NOT_ACCEPTABLE, true, "不可接受该请求"),
    /**
     * 411
     */
    LENGTH_REQUIRED(HttpServletResponse.SC_LENGTH_REQUIRED, true, "长度受限制"),
    /**
     * 415
     */
    UNSUPPORTED_MEDIA_TYPE(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, true, "不支持的媒体类型"),
    /**
     * 416
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE, true, "不能满足请求的范围"),
    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, true, "服务器正在升级，请耐心等待"),
    /**
     * 上传失败
     */
    UPLOAD_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, true, "上传失败"),
    /**
     * 503
     */
    SERVICE_UNAVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, true, "请求超时"),
    /**
     * 演示系统，无法该操作
     */
    DEMO_SYSTEM_CANNOT_DO(HttpServletResponse.SC_SERVICE_UNAVAILABLE, true, "演示系统，无法该操作"),
    //----------------------------------------------------业务异常----------------------------------------------------
    /**
     * 用户名密码错误
     */
    USERNAME_OR_PASSWORD_IS_WRONG(HttpServletResponse.SC_BAD_REQUEST, true, "用户名密码错误"),
    /**
     * 该文件未找到
     */
    FILE_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "该文件未找到"),

    /**
     * 邀请码不正确
     */
    INVITATION_CODE_WRONG(HttpServletResponse.SC_BAD_REQUEST, true, "邀请码不正确"),

    /**
     * 超出今日上传次数限制
     */
    UPLOAD_RATE_LIMIT(HttpServletResponse.SC_BAD_REQUEST, true, "超出今日上传次数限制"),


    /**
     * 用户名被占用
     */
    SIGN_UP_ERROR(HttpServletResponse.SC_BAD_REQUEST, true, "用户名被占用"),

    /**
     * 微信登录信息验证失败
     */
    WECHAT_VALIDATE_ERROR(HttpServletResponse.SC_BAD_REQUEST, true, "微信验证失败"),
    /**
     * 用户被禁用
     */
    USER_IS_DISABLED(HttpServletResponse.SC_NOT_ACCEPTABLE, true, "用户被禁用"),
    /**
     * 未找到该用户
     */
    USER_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该用户"),

    /**
     * 原密码不正确
     */
    ORIGINAL_PASSWORD_IS_INCORRECT(HttpServletResponse.SC_BAD_REQUEST, true, "原密码不正确"),
    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(HttpServletResponse.SC_BAD_REQUEST, true, "用户名已存在"),
    /**
     * 未找到该菜单
     */
    MENU_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "未找到该菜单"),
    /**
     * 数据插入失败
     */
    INSERT_ERROR(HttpServletResponse.SC_NOT_IMPLEMENTED, true, "数据插入失败"),

    /**
     * 上传图片到S3失败
     */
    UPLOAD_TO_S3_ERROR(HttpServletResponse.SC_NOT_IMPLEMENTED, true, "上传图片失败"),

    /**
     * 活动场次未找到
     */
    ACTIVITY_VENUES_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "活动场次未找到"),

    /**
     * 24小时内发送短信次数超出限制
     */
    SEND_MESSAGE_EXCESS(HttpServletResponse.SC_BAD_REQUEST, true, "24小时内发送短信次数超出限制"),
    ;



    private final int httpCode;
    private final boolean show;
    private final String msg;

    ErrorCodeEnum(int httpCode, boolean show, String msg) {
        this.httpCode = httpCode;
        this.msg = msg;
        this.show = show;
    }

    /**
     * 转换为ErrorCode(自定义返回消息)
     *
     * @param msg
     * @return
     */
    public ErrorCode convert(String msg) {
        return ErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg).build();
    }

    /**
     * 转换为ErrorCode
     *
     * @return
     */
    public ErrorCode convert() {
        return ErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg()).build();
    }

    public int httpCode() {
        return this.httpCode;
    }

    public String msg() {
        return this.msg;
    }

    public boolean show() {
        return this.show;
    }
}
