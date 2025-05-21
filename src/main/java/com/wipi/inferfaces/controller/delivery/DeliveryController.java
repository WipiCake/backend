package com.wipi.inferfaces.controller.delivery;

import com.wipi.domain.delivery.DeliveryAddressInfo;
import com.wipi.domain.delivery.DeliveryAddressService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
@Tag(name = "delivery", description = "배송 관련 API")
public class DeliveryController {

    private final DeliveryAddressService deliveryAddressService;

    @Operation(summary = "배송지 등록")
    @PostMapping("/register")
    public APIResponse<Void> register(@Valid @RequestBody DeliveryAddressRequest.Save request,
                                  @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.save(request.toCommand(user.getUserId()));
        return APIResponse.success();
    }
    @Operation(summary = "배송지 삭제", description = "/{id} --> id는 배송지 고유 ID입니다. ")
    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id,
                                    @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.deleteDeliveryAddress(id,user.getUserId());
        return APIResponse.success();
    }

    @Operation(summary = "배송지 수정")
    @PostMapping("/update")
    public APIResponse<Void> update(@Valid @RequestBody DeliveryAddressRequest.Update request,
                                    @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.updateDeliveryAddress(request.toCommand(user.getUserId()));
        return APIResponse.success();
    }

    @Operation(summary = "사용자별 전체 배송지 조회")
    @GetMapping("/getAll")
    public APIResponse<List<DeliveryAddressInfo.GetAll>> getAll(@Parameter(hidden = true) @LoginUsers User user) {
        List<DeliveryAddressInfo.GetAll> list = deliveryAddressService.getAll(user.getUserId());
        return APIResponse.success(list);
    }

    @Operation(summary = "사용자별 상세 배송지 조회", description = "{id} --> id는 배송지 고유 ID입니다.")
    @GetMapping("/getDetail/{id}")
    public APIResponse<DeliveryAddressInfo.GetDetail> getDetail(@Parameter(hidden = true) @LoginUsers User user,
                                                                @PathVariable Long id) {
        DeliveryAddressInfo.GetDetail detail = deliveryAddressService.getDetail(id,user.getUserId());
        return APIResponse.success(detail);
    }

}
