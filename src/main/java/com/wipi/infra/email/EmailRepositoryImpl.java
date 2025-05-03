package com.wipi.infra.email;

import com.wipi.domain.email.EmailRepository;
import com.wipi.domain.email.EmailVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
        emailRedisRepository.findByEmail(email)
                .ifPresent(emailVerification -> emailRedisRepository.deleteById(emailVerification.getId()));
    }
    @Override
    public Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode) {
        return emailRedisRepository.findByEmailAndVerificationCode(email, verificationCode);
    }

    @Override
    public void deleteAllByEmail(String email) {
        Iterable<EmailVerification> allEmails = emailRedisRepository.findAll();

        StreamSupport.stream(allEmails.spliterator(), false)
                .filter(emailVerification -> emailVerification.getEmail().equals(email))
                .forEach(emailVerification -> emailRedisRepository.deleteById(emailVerification.getId()));
    }

}
