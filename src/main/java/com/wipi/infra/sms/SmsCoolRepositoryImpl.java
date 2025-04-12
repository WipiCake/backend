package com.wipi.infra.sms;

import com.wipi.domain.sms.SmsCool;
import com.wipi.domain.sms.SmsCoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SmsCoolRepositoryImpl implements SmsCoolRepository {

    private final SmsCoolRedisRepository smsCoolRedisRepository;

    @Override
    public void save(SmsCool smsCool) {
        smsCoolRedisRepository.save(smsCool);
    }

    @Override
    public Optional<SmsCool> findByPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode) {
        return smsCoolRedisRepository.findByPhoneNumberAndVerificationCode(phoneNumber,verificationCode);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        smsCoolRedisRepository.deleteByPhoneNumber(phoneNumber);
    }
}
