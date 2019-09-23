package com.streambuf.subtitle.invitation;

import com.streambuf.subtitle.common.constant.EncryConstant;
import com.streambuf.subtitle.common.utils.MD5Util;
import com.streambuf.subtitle.user.model.po.InvitationCode;
import com.streambuf.subtitle.user.repository.InvitationCodeRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class InvitationTest {
    @Autowired
    private InvitationCodeRepo invitationCodeRepo;

//    @Test
    public void generate() {
        for (int i = 0; i < 100; i++) {
            String code = MD5Util.crypt(EncryConstant.SALT + System.currentTimeMillis());
            invitationCodeRepo.save(new InvitationCode(code, 0));
        }
    }
}
