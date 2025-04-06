package com.wipi.service.persist;

import com.wipi.exception.UserRuntimeException;
import com.wipi.model.entity.UserEntity;
import com.wipi.model.param.UserSignupParam;
import com.wipi.repository.UserRepository;
import com.wipi.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity saveOauthUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity saveUser(UserSignupParam param) {
        String encodedPassword = passwordEncoder.encode(param.getPassword());

        UserEntity userSaveEntity = new UserEntity(
                Utils.getUserUUID(),
                encodedPassword,
                param.getEmail(),
                "ROLE_USER",
                param.getNickName(),
                OffsetDateTime.now(ZoneId.of("Asia/Seoul")),
                param.getPhoneNumber(),
                param.getGender(),
                param.getBirthDt(),
                param.getZipAddress(),
                param.getMainAddress(),
                param.getDetailAddress()
        );

        return userRepository.save(userSaveEntity);
    }

    public void validateDuplicateUser(UserSignupParam param) {
        if (userRepository.existsByEmail(param.getEmail())) {
            throw new UserRuntimeException("이미 존재하는 이메일입니다.");
        }
    }

}
