package com.wipi.infra.email;

import com.wipi.domain.email.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRedisRepository extends CrudRepository<EmailVerification, String> {

    EmailVerification findByEmail(String email);
    void deleteByEmail(String email);

}
