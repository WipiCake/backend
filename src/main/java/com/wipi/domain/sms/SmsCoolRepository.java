package com.wipi.domain.sms;

import java.util.List;
import java.util.Optional;

public interface SmsCoolRepository {
    void save(SmsCool smsCool);
    Optional<SmsCool> findByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);
    void deleteByPhoneNumber(String phoneNumber);
    void deleteAllByPhoneNumber(String phoneNumber);
    Optional<List<SmsCool>> findByPhoneNumberAndPurpose(String phoneNumber, String purpose);
}
