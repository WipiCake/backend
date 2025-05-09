package com.wipi.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ProcessEmailVerificationParam {

    @NotBlank
    private String toEmail;

    @NotBlank
    private String purpose;


}
