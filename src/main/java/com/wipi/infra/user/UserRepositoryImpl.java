package com.wipi.infra.user;

import com.wipi.domain.user.User;
import com.wipi.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String username) {
        return userJpaRepository.findByEmail(username);
    }
}
