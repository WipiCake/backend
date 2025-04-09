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

    public ResUserSignupDto userSignup(UserSignupParam param) {
        userService.validateDuplicateEmail(param.getEmail());
        User user = userService.saveUser(param);

        return new ResUserSignupDto(user.getUserIdentityId(), user.getEmail());
    }

}
