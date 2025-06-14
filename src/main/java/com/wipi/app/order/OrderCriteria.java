package com.wipi.app.order;

import com.wipi.domain.product.ProductCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static kotlin.reflect.jvm.internal.impl.builtins.StandardNames.FqNames.list;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCriteria {


    @Getter
    public static class Order{
        private final String userid;
        private final List<OrderProduct> orderProductList;

        private Order(String userid, List<OrderProduct> orderProductList) {
            this.userid = userid;
            this.orderProductList = orderProductList;
        }

        public static Order of(String userid, List<OrderProduct> orderProduct) {
            return new Order(userid, orderProduct);
        }

        public List<ProductCommand.CalculateProductsPrice> toCommandPrice() {
            return orderProductList.stream()
                    .map(op -> ProductCommand.CalculateProductsPrice.of(op.getProductId(), op.getQuantity()))
                    .toList();
        }

    }
    @Getter
    public static class OrderProduct{
        private final long productId;
        private final long quantity;

        private OrderProduct(long productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public static OrderProduct of(long productId, long quantity) {
            return new OrderProduct(productId, quantity);
        }
    }


}
