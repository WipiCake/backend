package com.wipi.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    Boolean existByUserId(String userId);
}
