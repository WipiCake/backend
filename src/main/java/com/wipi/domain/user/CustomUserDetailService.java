package com.wipi.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserBy : {}", username);

        User entity = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("해당 계정이 존재하지 않습니다: {}", username);
                    return new UsernameNotFoundException("해당 계정이 존재하지 않습니다.");
                });

        return new CustomUserDetails(entity);
    }

}