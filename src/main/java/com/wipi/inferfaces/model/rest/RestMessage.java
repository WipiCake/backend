package com.wipi.inferfaces.model.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestMessage {
    @Schema(example = "이메일 전송이 완료되었습니다.", description = "응답 메세지")
    private String message;
}
