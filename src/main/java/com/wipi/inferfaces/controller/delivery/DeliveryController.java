package com.wipi.inferfaces.controller.delivery;

import com.wipi.domain.delivery.DeliveryAddress;
import com.wipi.domain.delivery.DeliveryAddressCommand;
import com.wipi.domain.delivery.DeliveryAddressInfo;
import com.wipi.domain.delivery.DeliveryAddressService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
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

    @PostMapping("/register")
    public APIResponse<Void> register(@Valid @RequestBody DeliveryAddressRequest.Save request,
                                  @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.save(request.toCommand(user.getUserId()));
        return APIResponse.success();
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id,
                                    @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.deleteDeliveryAddress(id,user.getUserId());
        return APIResponse.success();
    }

    @PostMapping("/update")
    public APIResponse<Void> update(@Valid @RequestBody DeliveryAddressRequest.Update request,
                                    @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.updateDeliveryAddress(request.toCommand(user.getUserId()));
        return APIResponse.success();
    }

    @GetMapping("/getAll")
    public APIResponse<List<DeliveryAddressInfo.GetAll>> getAll(@Parameter(hidden = true) @LoginUsers User user) {
        List<DeliveryAddressInfo.GetAll> list = deliveryAddressService.getAll(user.getUserId());
        return APIResponse.success(list);
    }

    @GetMapping("/getDetail/{id}")
    public APIResponse<DeliveryAddressInfo.GetDetail> getDetail(@Parameter(hidden = true) @LoginUsers User user,
                                                                @PathVariable Long id) {
        DeliveryAddressInfo.GetDetail detail = deliveryAddressService.getDetail(id,user.getUserId());
        return APIResponse.success(detail);
    }

}
