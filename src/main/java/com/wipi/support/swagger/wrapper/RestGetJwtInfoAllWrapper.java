package com.wipi.support.swagger.wrapper;

import com.wipi.inferfaces.model.dto.res.ResGetJwtInfoAll;
import com.wipi.inferfaces.model.rest.RestResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Redis에 존재하는 Jwt 토큰 정보 전체 조회 예시")
public class RestGetJwtInfoAllWrapper extends RestResponse<ResGetJwtInfoAll> {

    @Schema(example = "200", description = "응답 상태 코드")
    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Schema(example = "조회에 성공하였습니다.", description = "응답 메시지")
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RestGetJwtInfoAllWrapper(int status, String message, ResGetJwtInfoAll data) {
        super(status, message, data);
    }

}
