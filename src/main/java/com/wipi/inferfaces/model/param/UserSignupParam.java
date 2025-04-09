package com.wipi.inferfaces.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserSignupParam {
    @Schema(example = "user@example.com", description = "사용자 이메일")
    @NotNull(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(example = "securePassword123", description = "사용자 비밀번호 (8자 이상)")
    @NotNull(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Schema(example = "wipiUser", description = "닉네임")
    @NotNull(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 1, message = "닉네임은 비어 있을 수 없습니다.")
    private String nickName;

    @Schema(example = "01012345678", description = "전화번호")
    @NotNull(message = "전화번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "전화번호는 최소 8자 이상이어야 합니다.")
    private String phoneNumber;

    @Schema(example = "04524", description = "우편번호")
    @NotNull(message = "우편번호는 필수 입력 항목입니다.")
    @Size(min = 1, message = "우편번호는 비어 있을 수 없습니다.")
    private String zipAddress;

    @Schema(example = "서울특별시 중구 세종대로", description = "기본 주소")
    @NotNull(message = "기본주소는 필수 입력 항목입니다.")
    @Size(min = 1, message = "기본주소는 비어 있을 수 없습니다.")
    private String mainAddress;

    @Schema(example = "101동 202호", description = "상세 주소")
    private String detailAddress;

    @Schema(example = "1995-05-20", description = "생년월일 (YYYY-MM-DD)")
    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    @Size(min = 1, message = "생년월일은 비어 있을 수 없습니다.")
    private String birthDt;

    @Schema(example = "man", description = "성별 (man 또는 woman)")
    @NotNull(message = "성별은 필수 입력 항목입니다.")
    @Size(min = 1, message = "성별은 비어 있을 수 없습니다.")
    private String gender;
}
