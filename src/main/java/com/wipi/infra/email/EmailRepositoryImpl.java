package com.wipi.infra.email;

import com.wipi.domain.email.EmailRepository;
import com.wipi.domain.email.EmailVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

    private final EmailRedisRepository emailRedisRepository;

    @Override
    public EmailVerification save(EmailVerification emailVerification) {
        return emailRedisRepository.save(emailVerification);
    }

    @Override
    public Optional<EmailVerification> findByEmail(String email) {
        return emailRedisRepository.findByEmail(email);
    }

    @Override
    public void deleteByEmail(String email) {
        emailRedisRepository.deleteByEmail(email);
    }

    @Override
    public Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode) {
        return emailRedisRepository.findByEmailAndVerificationCode(email, verificationCode);
    }

}
