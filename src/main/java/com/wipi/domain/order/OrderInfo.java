package com.wipi.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfo {

    @Getter
    public static class CreateOrder {
        private final Long orderId;
        private final double productTotalPrice;

        private CreateOrder(Long orderId, double productTotalPrice) {
            this.orderId = orderId;
            this.productTotalPrice = productTotalPrice;
        }

        public static CreateOrder of(Long orderId, double productTotalPrice) {
            return new CreateOrder(orderId, productTotalPrice);
        }
    }

    @Getter
    public static class GetOrder{
        private final Long orderId;
        private final OrderStatus orderStatus;
        private final long productTotalPrice;

        public GetOrder(Long orderId, OrderStatus orderStatus, long productTotalPrice) {
            this.orderId = orderId;
            this.orderStatus = orderStatus;
            this.productTotalPrice = productTotalPrice;
        }

        public static GetOrder of(Long orderId, OrderStatus orderStatus, long productTotalPrice) {
            return new GetOrder(orderId,orderStatus, productTotalPrice);
        }

    }

    @Getter
    public static class GetOrderItems{
        List<OrderProduct> items;

        private GetOrderItems(List<OrderProduct> items) {
            this.items = items;
        }

        public static GetOrderItems of(List<OrderProduct> items) {
            return new GetOrderItems(items);
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