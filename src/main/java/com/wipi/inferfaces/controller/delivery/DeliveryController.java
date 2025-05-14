package com.wipi.inferfaces.controller.delivery;

import com.wipi.domain.delivery.DeliveryAddressService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
@Tag(name = "delivery", description = "배송 관련 API")
public class DeliveryController {

    private final DeliveryAddressService deliveryAddressService;

    public APIResponse<Void> save(@Valid @RequestBody DeliveryAddressRequest.Save request,
                                  @Parameter(hidden = true) @LoginUsers User user) {
        deliveryAddressService.save(request.toCommand(user.getUserId()));
        return APIResponse.success();
    }

}
