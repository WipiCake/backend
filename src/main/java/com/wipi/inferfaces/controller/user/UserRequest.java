package com.wipi.inferfaces.controller.user;

import com.wipi.domain.user.UserCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRequest {

    @Getter
    public static class ModifyLogin{
        @NotNull
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        @Schema(type = "string", description = "사용자 비밀번호", example = "securePass123")
        private final String password;

        public ModifyLogin(String password) {
            this.password = password;
        }

        public static ModifyLogin of(String password) {
            return new ModifyLogin(password);
        }
    }

    @Getter
    public static class ModifyPersonal{
        @Schema(description = "비밀번호", example = "password1234")
        private final String password1;

        @Schema(description = "비밀번호 확인", example = "password1234")
        private final String password2;

        @Schema(description = "닉네임", example = "jaemin")
        private final String nickName;

        @Schema(description = "이메일", example = "example@email.com")
        private final String email;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        private final String phoneNumber;

        @Schema(description = "성별", example = "남자")
        private final String gender;

        @Schema(description = "생년월일", example = "1995-01-01")
        private final String birthDt;

        @Schema(description = "우편번호", example = "12345")
        private final String zipAddress;

        @Schema(description = "기본 주소", example = "서울시 강남구 테헤란로")
        private final String mainAddress;

        @Schema(description = "상세 주소", example = "101동 202호")
        private final String detailAddress;

        public ModifyPersonal(
                String password1,
                String password2,
                String nickName,
                String email,
                String phoneNumber,
                String gender,
                String birthDt,
                String zipAddress,
                String mainAddress,
                String detailAddress
        ) {
            this.password1 = password1;
            this.password2 = password2;
            this.nickName = nickName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            this.birthDt = birthDt;
            this.zipAddress = zipAddress;
            this.mainAddress = mainAddress;
            this.detailAddress = detailAddress;
        }

        public static ModifyPersonal of(
                String password1,
                String password2,
                String nickName,
                String email,
                String phoneNumber,
                String gender,
                String birthDt,
                String zipAddress,
                String mainAddress,
                String detailAddress
        ) {
            return new ModifyPersonal(password1, password2, nickName, email, phoneNumber, gender, birthDt, zipAddress, mainAddress, detailAddress);
        }

        public UserCommand.ModifyPersonal toCommandModifyPersonal(String userId) {
            return UserCommand.ModifyPersonal.of(
                    userId,
                    this.password1,
                    this.password2,
                    this.nickName,
                    this.email,
                    this.phoneNumber,
                    this.gender,
                    this.birthDt,
                    this.zipAddress,
                    this.mainAddress,
                    this.detailAddress
            );
        }

    }

}
