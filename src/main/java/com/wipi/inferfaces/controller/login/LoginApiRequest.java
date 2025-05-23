package com.wipi.inferfaces.controller.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginApiRequest {

    @Schema(description = "유저 아이디", example = "admin")
    private String username;

    @Schema(description = "유저 비밀번호", example = "11112222")
    private String password;


}
