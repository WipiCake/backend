package com.wipi.inferfaces.api.controller.user;

import com.wipi.app.user.UserFrontService;
import com.wipi.domain.user.CustomUserDetails;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.api.resolver.LoginUsers;
import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.dto.res.ResUserSignupDto;
import com.wipi.inferfaces.model.param.UserSignupParam;
import com.wipi.inferfaces.model.param.UserUpdatePwParam;
import com.wipi.inferfaces.model.rest.RestResponse;
import com.wipi.inferfaces.model.rest.RestResponseEntity;
import com.wipi.support.swagger.wrapper.RestUserSignupWrapper;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Operation(summary = "회원가입", description = "사용자 회원가입")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "성공 시 [status,message,data] 형식으로 반환",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestUserSignupWrapper.class)
            )
    )
    public ResponseEntity<RestResponse<ResUserSignupDto>> signup(@RequestBody @Valid UserSignupParam param){
        ResUserSignupDto resDto = userFrontService.userSignup(param);

        log.info("UserController/signup : {}", Utils.toJson(param));
        return RestResponseEntity.ok("회원가입에 성공하였습니다.", resDto);
    }

    @PostMapping(value = "/updatePw")
    public APIResponse<Void> updatePw(@RequestBody @Valid UserUpdatePwParam param, @LoginUsers CustomUserDetails user) {
        log.info("유저 : {}", Utils.toJson(user));
        userService.updatePasswordByUserId(param.getPassword(),param.getPassword2(),user.getUser());
        return APIResponse.success();
    }

}
