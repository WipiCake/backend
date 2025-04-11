package com.wipi.inferfaces.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ProcessEmailVerificationParam {
    private String toEmail;
    private String purpose;
}
