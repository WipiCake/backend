package com.wipi.inferfaces.api.controller.user;

import com.wipi.app.user.UserFrontService;
import com.wipi.domain.user.User;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.api.resolver.LoginUsers;
import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.dto.res.ResUserSignupDto;
import com.wipi.inferfaces.model.param.UserSignupParam;
import com.wipi.inferfaces.model.param.UserUpdatePwParam;
import com.wipi.inferfaces.model.rest.RestResponse;
import com.wipi.inferfaces.model.rest.RestResponseEntity;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(description = "유저관련 API", name = "유저 API")
public class UserController {

    private final UserFrontService userFrontService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<RestResponse<ResUserSignupDto>> signup(@RequestBody @Valid UserSignupParam param){
        ResUserSignupDto resDto = userFrontService.userSignup(param);

        log.info("UserController/signup : {}", Utils.toJson(param));
        return RestResponseEntity.ok("회원가입에 성공하였습니다.", resDto);
    }

    @PostMapping(value = "/updatePw")
    public APIResponse<Void> updatePw(@RequestBody @Valid UserUpdatePwParam param, @LoginUsers User user) {
        log.info("유저 : {}", Utils.toJson(user));
        log.info("파람 : {}", Utils.toJson(param));

        userService.updatePasswordByUserId(param.getPassword(),param.getPassword2(), user);
        return APIResponse.success();
    }

}
