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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_identity_id")
    private Long userIdentityId;

    @Column(name = "email", length = 99, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 99, nullable = false)
    private String password;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @Column(name = "nick_name", length = 99)
    private String nickName;

    @Column(name = "phone_number", length = 99)
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

    public User(Long userIdentityId, String role) {
        this.userIdentityId = userIdentityId;
        this.role = role;
    }
}
