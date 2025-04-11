package com.wipi.app;

import com.wipi.domain.user.User;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.model.dto.res.ResUserSignupDto;
import com.wipi.inferfaces.model.param.UserSignupParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFrontService {

    private final UserService userService;

    //회원가입
    public ResUserSignupDto userSignup(UserSignupParam param) {
        // todo 이메일이 중복되는지
        userService.validateDuplicateEmail(param.getEmail());

        // todo 유저 아이디가 중복되는지
        userService.validateDuplicateUserId(param.getUserId());

        // todo 회원가입
        User user = userService.saveUser(param);

        return new ResUserSignupDto(user.getUserId(), user.getEmail(), user.getRole());
    }

}
