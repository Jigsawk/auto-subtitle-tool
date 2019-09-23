package com.streambuf.subtitle.user.controller;


import com.streambuf.subtitle.common.controller.SuperController;
import com.streambuf.subtitle.common.responses.ApiResponses;
import com.streambuf.subtitle.user.model.vo.RecordVO;
import com.streambuf.subtitle.user.model.vo.UserLoginVO;
import com.streambuf.subtitle.user.model.vo.UserSignUpVO;
import com.streambuf.subtitle.user.service.impl.RecordService;
import com.streambuf.subtitle.user.service.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@Validated
public class UserAuthController extends SuperController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private RecordService recordService;

    @PostMapping("/login")
    public ApiResponses<String> login(@RequestBody UserLoginVO param) {
        String token = userAuthService.login(param);
        return success(HttpStatus.OK, token);
    }

    @PostMapping("/sign")
    public ApiResponses<String> signUp(@RequestBody UserSignUpVO param) {
        String token = userAuthService.signUp(param);
        return success(HttpStatus.CREATED, token);
    }

    @GetMapping("/info")
    public ApiResponses<List<RecordVO>> getInfo() {
        return success(HttpStatus.OK, recordService.getRecordInfo(currentUid()));
    }
}
