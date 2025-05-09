package com.wipi.inferfaces.controller.pick;

import com.wipi.app.pick.PickFacade;
import com.wipi.app.pick.PickResult;
import com.wipi.domain.pick.PickCommand;
import com.wipi.domain.pick.PickService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pick")
public class PickController {

    private final PickService pickService;
    private final PickFacade pickFacade;

    @PostMapping("/save")
    public APIResponse<Void> save(@Valid @RequestBody PickRequest.Save request, @LoginUsers User user) {
        pickService.save(PickCommand.Save.of(request.getProductId(),user.getUserId()));
        return APIResponse.success();
    }

    @GetMapping("/getPicks")
    public APIResponse<List<PickResult.GetUserPicks>> getProducts(@Valid @LoginUsers User user) {
        List<PickResult.GetUserPicks> data = pickFacade.getUserPicks(user.getUserId());
        return APIResponse.success(data);
    }


}
