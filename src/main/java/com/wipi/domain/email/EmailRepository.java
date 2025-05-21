package com.wipi.domain.email;

import java.util.List;
import java.util.Optional;

public interface EmailRepository {
    EmailVerification save(EmailVerification emailVerification);
    Optional<EmailVerification> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode);
    void deleteAllByEmail(String email);
    Optional<List<EmailVerification>> findByEmailAndPurpose(String toEmail, String purpose);
}
