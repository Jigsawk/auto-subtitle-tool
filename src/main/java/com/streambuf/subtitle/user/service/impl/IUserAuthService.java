package com.streambuf.subtitle.user.service.impl;

import com.streambuf.subtitle.user.model.vo.UserLoginVO;
import com.streambuf.subtitle.user.model.vo.UserSignUpVO;

public interface IUserAuthService {
    String login(UserLoginVO vo);

    String signUp(UserSignUpVO vo);
}
