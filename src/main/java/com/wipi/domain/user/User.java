package com.wipi.domain.user;

import com.wipi.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wipi_user")
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "email", length = 99, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 99, nullable = false)
    private String password;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @Column(name = "nick_name", length = 99, nullable = false)
    private String nickName;

    @Column(name = "phone_number", length = 99, nullable = false)
    private String phoneNumber;

    @Column(name = "gender", length = 49)
    private String gender;

    @Column(name = "birth_dt", length = 49)
    private String birthDt;

    @Column(name = "zip_address", length = 49)
    private String zipAddress;

    @Column(name = "main_address", length = 49)
    private String mainAddress;

    @Column(name = "detail_address", length = 49, nullable = true)
    private String detailAddress;

    public User(String email, String password, String role, String nickName, String phoneNumber, String gender, String birthDt, String zipAddress, String mainAddress, String detailAddress) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDt = birthDt;
        this.zipAddress = zipAddress;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }

    public User(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public void updatePersonal(UserCommand.ModifyPersonal command) {
        if (!command.getPassword1().equals(command.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        this.password = command.getPassword1();
        this.nickName = command.getNickName();
        this.email = command.getEmail();
        this.phoneNumber = command.getPhoneNumber();
        this.gender = command.getGender();
        this.birthDt = command.getBirthDt();
        this.zipAddress = command.getZipAddress();
        this.mainAddress = command.getMainAddress();
        this.detailAddress = command.getDetailAddress();
    }
}
