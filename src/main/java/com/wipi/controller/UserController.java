package com.wipi.controller;

import com.wipi.model.param.UserSignupParam;
import com.wipi.model.result.ResUserSignupDto;
import com.wipi.rest.RestUserSignup;
import com.wipi.service.front.UserFrontService;
import com.wipi.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(description = "유저관련 API", name = "유저 API")
public class UserController {

    private final UserFrontService userFrontService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자 회원가입")
    @ApiResponse(
            responseCode = "200",
            description = "성공 시 [status,message,data] 형식으로 반환",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestUserSignup.class)
            )
    )
    public RestUserSignup signup(@RequestBody @Valid UserSignupParam param){
        ResUserSignupDto resDto = userFrontService.userSignup(param);

        log.info("UserController/signup : {}", Utils.toJson(param));
        return new RestUserSignup("200","회원가입에 성공하였습니다.",resDto);
    }
}
