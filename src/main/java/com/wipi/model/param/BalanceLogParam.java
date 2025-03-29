package com.wipi.model.param;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceLogParam {
    private Map<String, Object> savedAccount;
    private Map<String, Object> updatedAccount;
    private Map<String, Object> classMethod;


}
