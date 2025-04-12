package com.wipi.domain.sms;

import java.util.Optional;

public interface SmsCoolRepository {
    void save(SmsCool smsCool);
    Optional<SmsCool> findByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);
    void deleteByPhoneNumber(String phoneNumber);
}
