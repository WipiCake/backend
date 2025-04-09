package com.wipi.domain.email;

public interface EmailRepository {
    EmailVerification save(EmailVerification emailVerification);
    EmailVerification findByEmail(String email);
    void deleteByEmail(String email);
}
