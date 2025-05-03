package com.wipi.support.util;

import java.util.List;

public class ValidUtils {


    public static void validVerifyPurpose(String purpose, List<String> acceptPurpose) {
        if (!acceptPurpose.contains(purpose)) {
            throw new RuntimeException("올바르지 않은 요청입니다. (purpose 값 오류)");
        }
    }
}
