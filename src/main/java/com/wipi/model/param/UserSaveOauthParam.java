package com.wipi.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveOauthParam {

    private String userId;
    private String email;
    private String password;
    private String role;
    private String name;
    private String provider;
    private String providerId;






}
