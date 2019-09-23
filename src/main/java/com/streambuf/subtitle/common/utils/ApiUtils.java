/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to parse in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.streambuf.subtitle.common.utils;


import com.streambuf.subtitle.common.constant.EncryConstant;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.exception.LoginException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class ApiUtils {

    public static Integer currentUid() {
        // TODO 从全局 request 中获取  token
//        String jwt = "";
        return 0;
    }

    /**
     * 获取当前用户id
     */
    public static Long currentUid(String jwt) {
        Key key = Keys.hmacShaKeyFor(EncryConstant.SECRET.getBytes());
        Long userId = null;
        Date expireDate = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getExpiration();
        if (expireDate.getTime() < new Date().getTime()) {
            throw new LoginException(ErrorCodeEnum.AUTHENTICATION_EXPIRE);
        }
        try {
            userId = Long.valueOf(Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getId());
        } catch (JwtException e) {
            throw new LoginException(ErrorCodeEnum.UNAUTHORIZED);
        }
        return userId;
    }

}
