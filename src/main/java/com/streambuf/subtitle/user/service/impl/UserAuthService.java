package com.streambuf.subtitle.user.service.impl;

import com.streambuf.subtitle.common.constant.EncryConstant;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.common.utils.MD5Util;
import com.streambuf.subtitle.exception.ApiException;
import com.streambuf.subtitle.user.model.po.InvitationCode;
import com.streambuf.subtitle.user.model.po.User;
import com.streambuf.subtitle.user.model.vo.UserLoginVO;
import com.streambuf.subtitle.user.model.vo.UserSignUpVO;
import com.streambuf.subtitle.user.repository.InvitationCodeRepo;
import com.streambuf.subtitle.user.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserAuthService implements IUserAuthService {
    private static final String ROLE = "user";
    private static final Integer USED = 1;
    private static final Integer UNUSED = 0;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private InvitationCodeRepo invitationCodeRepo;

    @Override
    public String login(UserLoginVO param) {
        Optional<User> optional = userRepo.findByUsernameAndPassword(param.getUsername(), MD5Util.crypt(param.getPassword()));
        if (optional.isPresent()) {
            return generatorJWT(optional.get().getId());
        } else {
            throw new ApiException(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG);
        }
    }

    @Override
    @Transactional
    public String signUp(UserSignUpVO param) {
        Optional<User> optional = userRepo.findByUsername(param.getUsername());
        if (optional.isPresent()) {
            throw new ApiException(ErrorCodeEnum.SIGN_UP_ERROR);
        }
        Optional<InvitationCode> optionalCode = invitationCodeRepo.findByCodeAndStatus(param.getInvitation(), UNUSED);
        if (!optionalCode.isPresent()) {
            throw new ApiException(ErrorCodeEnum.INVITATION_CODE_WRONG);
        }
        param.setPassword(MD5Util.crypt(param.getPassword()));
        User user = userRepo.save(param.convert(User.class));
        InvitationCode invitation = optionalCode.get();
        invitation.setStatus(USED);
        invitation.setUserId(user.getId());
        invitationCodeRepo.save(invitation);
        return generatorJWT(user.getId());
    }

    private String generatorJWT(Long userId) {
        Key key = Keys.hmacShaKeyFor(EncryConstant.SECRET.getBytes());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 2);
        // 失效时间为2个月
        return Jwts.builder().setSubject(ROLE)
                .setId(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(key)
                .compact();
    }
}
