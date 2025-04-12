package com.wipi.infra.sms;

import com.wipi.domain.sms.SmsCool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SmsCoolRedisRepository extends CrudRepository<SmsCool, Long> {
    void deleteByPhoneNumber(String phoneNumber);
    Optional<SmsCool> findByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);
}
