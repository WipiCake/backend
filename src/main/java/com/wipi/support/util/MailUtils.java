package com.wipi.support.util;

import java.util.Random;

public class MailUtils {

    public static final String setFrom = "wipi_cake";

    // todo 본문,제목
    public static String getSubjectForVerificationEmail() {
        return "이메일 인증 코드";
    }

    public static String getSubjectForFindPassword() {
        return "임시 비밀번호 발급 안내";
    }

    public static String getBodyForVerificationEmail(String code) {
        return String.format(
                "안녕하세요, 요청하신 서비스에 대한 이메일 인증 코드입니다.\n\n" +
                        "아래 인증 코드를 입력해 주세요:\n\n" +
                        "%s\n\n" +
                        "※ 본 인증 코드는 10분간만 유효합니다.",
                code
        );
    }

    public static String getBodyForFindPassword(String tempPassword) {
        return String.format(
                "안녕하세요, 요청하신 비밀번호 재설정을 위한 임시 비밀번호를 안내드립니다.\n\n" +
                        "임시 비밀번호: %s\n\n" +
                        "로그인 후 반드시 마이페이지에서 비밀번호를 변경해 주세요.\n\n" +
                        "※ 보안을 위해 이 임시 비밀번호는 10분간만 유효합니다.\n" +
                        "※ 본인이 요청하지 않은 경우, 이 이메일을 무시하셔도 됩니다.",
                tempPassword
        );
    }

}
