package com.wipi.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

public class User {

    @Id
    @Column(name = "user_id", length = 49)
    private String userId;

    @Column(name = "password", length = 99, nullable = false)
    private String password;

    @Column(name = "email", length = 49, unique = true, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String role;

    @Column(name = "nick_name", length = 49)
    private String nickName;

    @Column(name = "create_at", length = 20)
    private OffsetDateTime createAt;

    @Column(name = "update_at", length = 20)
    private OffsetDateTime updateAt;

    @Column(name = "phone_number", length = 49)
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

    private String provider;
    private String providerId;

    public User(String userId, String password, String email, String role, String nickName, OffsetDateTime createAt, String phoneNumber, String gender, String birthDt, String zipAddress, String mainAddress, String detailAddress) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.role = role;
        this.nickName = nickName;
        this.createAt = createAt;
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
}
