package com.wipi.support.util;

public class SmsUtils {

    public static final String fromNumber = "01046887175";
    public static final long expirationMinute = 5;

    public static String getContentForVerificationCode(String code) {
        return String.format("[WIPI] 인증번호는 [%s]입니다. 10분 내에 입력해주세요.", code);
    }


}
