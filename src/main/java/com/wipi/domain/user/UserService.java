package com.wipi.domain.user;

import com.wipi.inferfaces.model.param.UserSignupParam;
import com.wipi.support.properties.UserRoleProperties;
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

    public void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
    }

    public void validateDuplicateUserId(String userId) {
        if (userRepository.existByUserId(userId)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
    }

    public User saveUser(UserSignupParam param) {
        final String userRole = userRoleProperties.getUser();
        final String encodedPassword = passwordEncoder.encode(param.getPassword());

        User user = new User();
            user.setUserId(param.getUserId());
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

    public User updatePasswordByEmail(String email, String password) {
        User findUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일이 존재하지 않습니다."));
        findUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(findUser);
    }



}
