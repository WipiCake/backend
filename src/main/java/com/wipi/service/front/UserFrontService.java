package com.wipi.service.front;

import com.wipi.model.entity.UserEntity;
import com.wipi.model.param.UserSignupParam;
import com.wipi.model.result.ResUserSignupDto;
import com.wipi.service.persist.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFrontService {
    private final UserService userService;

    public ResUserSignupDto userSignup(UserSignupParam param) {
        //회원가입 중복체크
        userService.validateDuplicateUser(param);

        //회원가입
        UserEntity user = userService.saveUser(param);

        return new ResUserSignupDto(user.getUserId(), user.getEmail() );
    }

}
