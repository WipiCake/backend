package com.wipi.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyRestPwByEmailParam {
    private String fromEmail;
    private String verificationCode;
    private String purpose;
}

