package com.wipi.inferfaces.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserUpdatePwParam {


    @NotBlank
    private String password;

    @NotBlank
    private String password2;
}
