package com.wipi.infra.user;

import com.wipi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUserId(String userId);

    Optional<User> findByUserId(String userId);
}
