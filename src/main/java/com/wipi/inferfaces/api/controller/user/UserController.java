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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "유저 API", description = "회원가입·비밀번호 변경 등 유저 관련 기능")
public class UserController {

    private final UserFrontService userFrontService;
    private final UserService userService;

    @Operation(
            summary = "회원 가입",
            description = "이메일·비밀번호 등 정보를 받아 새 사용자를 등록합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원가입 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ResUserSignupDto.class)
            )
    )
    @PostMapping(
            path = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<ResUserSignupDto>> signup(
            @Parameter(
                    description = "회원가입 요청 파라미터",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserSignupParam.class)
                    )
            )
            @RequestBody @Valid UserSignupParam param
    ) {
        ResUserSignupDto resDto = userFrontService.userSignup(param);
        log.info("UserController/signup : {}", Utils.toJson(param));
        return RestResponseEntity.ok("회원가입에 성공하였습니다.", resDto);
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "현재 로그인된 사용자의 비밀번호를 새 비밀번호로 변경합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "비밀번호 변경 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = APIResponse.class)
            )
    )
    @PostMapping(
            path = "/updatePw",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public APIResponse<Void> updatePw(
            @Parameter(
                    description = "비밀번호 변경 요청 파라미터",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserUpdatePwParam.class)
                    )
            )
            @RequestBody @Valid UserUpdatePwParam param,

            @Parameter(hidden = true)
            @LoginUsers User user
    ) {
        log.info("UserController/updatePw - user: {}, param: {}",
                Utils.toJson(user), Utils.toJson(param));

        userService.updatePasswordByUserId(
                param.getPassword(),
                param.getPassword2(),
                user
        );
        return APIResponse.success();
    }
}
