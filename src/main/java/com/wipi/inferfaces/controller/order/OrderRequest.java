package com.wipi.inferfaces.controller.order;

import com.wipi.app.order.OrderCriteria;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    @Getter
    @NoArgsConstructor
    public static class Order {
        @Valid
        @NotEmpty
        private List<OrderProduct> items;

        public OrderCriteria.Order toCriteria(String userId) {
            List<OrderCriteria.OrderProduct> products = items.stream()
                    .map(i -> OrderCriteria.OrderProduct.of(
                            i.getProductId(),
                            i.getQuantity()
                    ))
                    .toList();

            return OrderCriteria.Order.of(userId, products);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderProduct {
        private Long productId;
        private Long quantity;
    }
}

