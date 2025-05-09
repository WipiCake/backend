package com.wipi.inferfaces.controller.pick;

import com.wipi.domain.pick.PickCommand;
import com.wipi.domain.pick.PickService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pick")
public class PickController {

    private final PickService pickService;

    public APIResponse<Void> save(@Valid PickRequest.Save request, @LoginUsers User user) {
        pickService.save(PickCommand.Save.of(request.getProductId(),user.getUserId()));
        return APIResponse.success();
    }

}
