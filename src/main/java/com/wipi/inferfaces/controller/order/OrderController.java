package com.wipi.inferfaces.controller.order;

import com.wipi.app.order.OrderFacade;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문 생성", description = "장바구니 주문 생성")
    @PostMapping
    public APIResponse<Long> createOrder(@Valid @RequestBody OrderRequest.Order request, @Parameter(hidden = true) @LoginUsers User user) {
        Long orderId = orderFacade.createOrder(request.toCriteria(user.getUserId()));
        return APIResponse.success(orderId);
    }
}
