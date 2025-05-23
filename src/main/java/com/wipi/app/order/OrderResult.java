package com.wipi.app.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResult {

    @Getter
    public static class Order {
        private final Long orderId;
        private final double totalPrice;
        private final List<OrderProduct> items;

        private Order(Long orderId, double totalPrice,List<OrderProduct> items) {
            this.orderId = orderId;
            this.totalPrice = totalPrice;
            this.items = items;
        }

        public static Order of(Long orderId, double totalPrice,List<OrderProduct> items) {
            return new Order(orderId,totalPrice,items);
        }
    }

    @Getter
    public static class OrderProduct{
        private final Long productId;
        private final Long quantity;

        private OrderProduct(Long productId, Long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public static OrderProduct of(Long productId, Long quantity) {
            return new OrderProduct(productId,quantity);
        }


    }
}
