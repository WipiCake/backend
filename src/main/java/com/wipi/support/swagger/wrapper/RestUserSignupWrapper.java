package com.wipi.support.swagger.wrapper;

import com.wipi.inferfaces.model.dto.res.ResUserSignupDto;
import com.wipi.inferfaces.model.rest.RestResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 예시")
public class RestUserSignupWrapper extends RestResponse<ResUserSignupDto> {

    @Schema(example = "200", description = "응답 상태 코드")
    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Schema(example = "회원가입에 성공하였습니다.", description = "응답 메시지")
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RestUserSignupWrapper(int status, String message, ResUserSignupDto data) {
        super(status, message, data);
    }
}
