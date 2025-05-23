package com.wipi.app.order;

import com.wipi.domain.order.OrderCommand;
import com.wipi.domain.order.OrderService;
import com.wipi.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final ProductService productService;
    private final OrderService orderService;

    public Long createOrder(OrderCriteria.Order criteria) {
        final long totalPrice = productService.calculatePrice(criteria.toCommandPrice());

        Long result = orderService.createOrder(OrderCommand.CreateOrder.of(criteria.getUserid(),
        totalPrice,criteria.getOrderProductList().stream().map(
        req -> OrderCommand.OrderProduct.of(req.getProductId(), req.getQuantity())).toList()));

        return result;
    }



}
