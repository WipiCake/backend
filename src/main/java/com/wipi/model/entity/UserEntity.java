package com.wipi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class UserEntity {



    @Id
    @Column(length = 55)
    private String userId;

    @Column(length = 155)
    private String name;

    @Column(length = 99)
    private String role;


    @Column(length = 99)
    private String email;

    @Column(length = 99)
    private String password;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    private String provider;
    private String providerId;
    public UserEntity(String name) {
        this.name = name;
    }
    public UserEntity(String name, String role) {
        this.name = name;
        this.role = role;
    }
    public UserEntity(String userId, String name, String role, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public UserEntity(String userId, String name, String role, String email, String provider, String providerId) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

}
