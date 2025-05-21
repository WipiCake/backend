package com.wipi.inferfaces.controller.pick;

import com.wipi.app.pick.PickFacade;
import com.wipi.app.pick.PickResult;
import com.wipi.domain.pick.PickCommand;
import com.wipi.domain.pick.PickService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pick")
@Tag(name = "pick", description = "찜하기 관련 API")
public class PickController {

    private final PickService pickService;
    private final PickFacade pickFacade;

    @Operation(summary = "찜하기")
    @PostMapping("/save")
    public APIResponse<Void> save(@Valid @RequestBody PickRequest.Save request,
                                  @Parameter(hidden = true) @LoginUsers User user) {
        pickService.save(PickCommand.Save.of(request.getProductId(),user.getUserId()));
        return APIResponse.success();
    }

    @Operation(summary = "사용자 찜 전체조회")
    @GetMapping("/getPicks")
    public APIResponse<List<PickResult.GetUserPicks>> getProducts(@Parameter(hidden = true) @Valid @LoginUsers User user) {
        List<PickResult.GetUserPicks> data = pickFacade.getUserPicks(user.getUserId());
        return APIResponse.success(data);
    }

    @Operation(summary = "찜 삭제")
    @DeleteMapping("/{productId}")
    public APIResponse<Void> delete(@PathVariable Long productId,
                                    @Parameter(hidden = true)@LoginUsers User user) {
        pickService.delete(PickCommand.Delete.of(productId, user.getUserId()));
        return APIResponse.success();
    }

}
