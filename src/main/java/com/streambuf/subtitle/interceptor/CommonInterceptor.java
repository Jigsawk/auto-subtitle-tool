package com.streambuf.subtitle.interceptor;

import com.streambuf.subtitle.common.enums.ErrorCode;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.common.utils.ApiUtils;
import com.streambuf.subtitle.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {
    private final static String PREFIX = "Bearer";
    private final static String AUTH_HEADER = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        String authHeader = request.getHeader(AUTH_HEADER);
        if (StringUtils.isBlank(authHeader)) {
            flag = false;
        }else {
            String token = authHeader.substring(7);
            if (StringUtils.isBlank(token))
                flag = false;
            ApiUtils.currentUid(token);
        }
        if (!flag) {
            throw new ApiException(ErrorCodeEnum.UNAUTHORIZED);
        }
        return flag;
    }
}
