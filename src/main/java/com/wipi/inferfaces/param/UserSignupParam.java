package com.wipi.inferfaces.param;

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
    @NotNull(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 1, message = "닉네임은 비어 있을 수 없습니다.")
    private String nickName;

    @NotNull(message = "전화번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "전화번호는 최소 8자 이상이어야 합니다.")
    private String phoneNumber;

    @NotNull(message = "우편번호는 필수 입력 항목입니다.")
    @Size(min = 1, message = "우편번호는 비어 있을 수 없습니다.")
    private String zipAddress;

    @NotNull(message = "기본주소는 필수 입력 항목입니다.")
    @Size(min = 1, message = "기본주소는 비어 있을 수 없습니다.")
    private String mainAddress;

    private String detailAddress;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    @Size(min = 1, message = "생년월일은 비어 있을 수 없습니다.")
    private String birthDt;

    @NotNull(message = "성별은 필수 입력 항목입니다.")
    @Size(min = 1, message = "성별은 비어 있을 수 없습니다.")
    private String gender;
}
