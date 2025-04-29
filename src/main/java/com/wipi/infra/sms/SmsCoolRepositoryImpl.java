package com.wipi.infra.sms;

import com.wipi.domain.email.EmailVerification;
import com.wipi.domain.sms.SmsCool;
import com.wipi.domain.sms.SmsCoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.stream.StreamSupport;

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

    @Override
    public void deleteAllByPhoneNumber(String phoneNumber) {
        Iterable<SmsCool> allEmails = smsCoolRedisRepository.findAll();

        StreamSupport.stream(allEmails.spliterator(), false)
                .filter(sms -> sms.getPhoneNumber().equals(phoneNumber))
                .forEach(sms -> smsCoolRedisRepository.deleteById(sms.getId()));
    }

    @Override
    public Optional<SmsCool> findByPhoneNumber(String phoneNumber) {
        return smsCoolRedisRepository.findByPhoneNumber(phoneNumber);
    }


}


