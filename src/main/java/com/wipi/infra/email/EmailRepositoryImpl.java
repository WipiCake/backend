package com.wipi.infra.email;

import com.wipi.domain.email.EmailRepository;
import com.wipi.domain.email.EmailVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {

    private final EmailRedisRepository emailRedisRepository;

    @Override
    public EmailVerification save(EmailVerification emailVerification) {
        return emailRedisRepository.save(emailVerification);
    }
}
