package com.wipi.inferfaces.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqSendEmailDto {
    private String toEmail;
    private String subject;
    private String body;
    private String code;
}
