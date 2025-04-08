package com.wipi.domain.user;

import com.wipi.inferfaces.param.UserSignupParam;
import com.wipi.infra.user.UserRoleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleProperties userRoleProperties;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다.")
        );
    }

    public void validateDuplicateEmail(UserSignupParam param) {
        if (userRepository.existsByEmail(param.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
    }

    public User saveUser(UserSignupParam param) {
        final String userRole = userRoleProperties.getUser();
        final String encodedPassword = passwordEncoder.encode(param.getPassword());

        User user = new User();
            user.setEmail(param.getEmail());
            user.setPassword(encodedPassword);
            user.setRole(userRole);
            user.setNickName(param.getNickName());
            user.setPhoneNumber(param.getPhoneNumber());
            user.setBirthDt(user.getBirthDt());
            user.setGender(param.getGender());
            user.setZipAddress(param.getZipAddress());
            user.setMainAddress(param.getMainAddress());
            user.setDetailAddress(param.getDetailAddress());

        return userRepository.save(user);
    }



}
