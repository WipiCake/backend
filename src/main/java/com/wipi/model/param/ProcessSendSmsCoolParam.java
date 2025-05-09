package com.wipi.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessSendSmsCoolParam {

    @NotBlank
    private String toPhoneNumber;

    @NotBlank
    private String purpose;

}
