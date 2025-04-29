package com.wipi.inferfaces.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyFindIdBySmsCoolParam {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String verificationCode;

    @NotBlank
    private String purpose;
}
