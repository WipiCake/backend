package com.wipi.inferfaces.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqSaveEmailVerificationDto {
    private String toEmail;
    private String purpose;
    private String verificationCode;
}
