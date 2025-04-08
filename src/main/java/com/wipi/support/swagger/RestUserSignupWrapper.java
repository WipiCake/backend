package com.wipi.support.swagger;

import com.wipi.inferfaces.dto.ResUserSignupDto;
import com.wipi.inferfaces.rest.RestResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 예시")
public class RestUserSignupWrapper extends RestResponse<ResUserSignupDto> {

    public RestUserSignupWrapper() {
        super(200, "회원가입에 성공하였습니다.",
                new ResUserSignupDto(1L, "wipi@naver.com"));
    }
}
