package com.wipi.inferfaces.controller.user;

import com.wipi.app.user.UserFrontService;
import com.wipi.domain.user.User;
import com.wipi.domain.user.UserCommand;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
import com.wipi.model.dto.res.ResUserSignupDto;
import com.wipi.model.param.UserSignupParam;
import com.wipi.model.param.UserUpdatePwParam;
import com.wipi.model.rest.RestResponse;
import com.wipi.model.rest.RestResponseEntity;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
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

    @Operation(summary = "유저 회원가입", description = "유저 회원가입 입니다.")
    @PostMapping("/signup")
    public ResponseEntity<RestResponse<ResUserSignupDto>> signup(@RequestBody @Valid UserSignupParam param){
        ResUserSignupDto resDto = userFrontService.userSignup(param);

        log.info("UserController/signup : {}", Utils.toJson(param));
        return RestResponseEntity.ok("회원가입에 성공하였습니다.", resDto);
    }

    @Operation(summary = "비밀번호 찾기 검증 완료후 비밀번호 변경",description = "유저 권한이 필요한 사항, 비밀번호찾기 인증이 완료되면 이 API를 호출하면됩니다. ,Refresh,Access 토큰 같이 요청 필요")
    @PostMapping(value = "/updatePw")
    public APIResponse<Void> updatePw(@RequestBody @Valid UserUpdatePwParam param,
                                      @Parameter(hidden = true) @LoginUsers User user) {
        log.info("유저 : {}", Utils.toJson(user));
        log.info("파람 : {}", Utils.toJson(param));

        userService.updatePasswordByUserId(param.getPassword(),param.getPassword2(), user);
        return APIResponse.success();
    }
//    @PostMapping(value = "/modify/login")
//    public APIResponse<Void> modifyLogin(@RequestBody @Valid UserRequest.ModifyLogin request,
//                                         @Parameter(hidden = true) @LoginUsers User user){
//
//        userService.modifyLogin(UserCommand.ModifyLogin.of(user.getUserId(), request.getPassword()));
//        return APIResponse.success();
//    }

    @Operation(summary= "개인정보 수정", description = "유저의 개인정보를 변경합니다., Refresh,Access 토큰 같이 요청 필요")
    @PostMapping(value = "/modify/personal")
    public APIResponse<Void> modifyPersonal(@RequestBody @Valid UserRequest.ModifyPersonal request,
                                            @Parameter(hidden = true) @LoginUsers User user){

        userService.modifyPersonal(request.toCommandModifyPersonal(user.getUserId()));
        return APIResponse.success();
    }

    @Operation(summary = "유저 상세 정보 조회", description = "Refresh,Access 토큰 같이 요청 필요")
    @PostMapping(value = "/getDetail")
    public APIResponse<User> getDetail(@Parameter(hidden = true) @LoginUsers User user){
        User findUser = userService.findUser(user.getUserId());
        return APIResponse.success(findUser);
    }


}
