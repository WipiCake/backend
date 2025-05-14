package com.wipi.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCommand {

    @Getter
    public static class ModifyLogin {
        private final String userId;
        private final String password;

        private ModifyLogin(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public static ModifyLogin of(String userId, String password) {
            return new ModifyLogin(userId, password);
        }
    }

    @Getter
    public static class ModifyPersonal {
        private final String userId;
        private final String password1;
        private final String password2;
        private final String nickName;
        private final String email;
        private final String phoneNumber;
        private final String gender;
        private final String birthDt;
        private final String zipAddress;
        private final String mainAddress;
        private final String detailAddress;

        private ModifyPersonal(
                String userId,
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
            this.userId = userId;
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
                String userId,
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
            return new ModifyPersonal(userId, password1, password2, nickName, email, phoneNumber, gender, birthDt, zipAddress, mainAddress, detailAddress);
        }
    }
}
