package com.wipi.infra.email;

import com.wipi.domain.email.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRedisRepository extends CrudRepository<EmailVerification, String> {

    Optional<EmailVerification> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode);

}
