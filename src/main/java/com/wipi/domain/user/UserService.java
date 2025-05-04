package com.wipi.domain.user;

import com.wipi.domain.email.EmailRepository;
import com.wipi.domain.email.EmailVerification;
import com.wipi.domain.jwt.JwtService;
import com.wipi.inferfaces.model.param.UserSignupParam;
import com.wipi.support.properties.UserRoleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleProperties userRoleProperties;
    private final EmailRepository emailRepository;

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

    public void updatePasswordByUserId(String newPassword, String confirmPassword, User user) {
        if (user == null || user.getUserId() == null || user.getRole() == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        }

        if (newPassword == null || confirmPassword == null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 서로 일치하지 않습니다.");
        }

        User findUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + user.getUserId()));

        findUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(findUser);
    }


    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다.")
        );
    }



}
