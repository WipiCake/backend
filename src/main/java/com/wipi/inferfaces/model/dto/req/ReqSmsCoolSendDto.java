package com.wipi.inferfaces.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ReqSmsCoolSendDto {
    private String toPhoneNumber;
    private String verificationCode;
}
