package com.wipi.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {

    @Getter
    public static class ListSelling{
        private final Long productId;
        private final String name;
        private final long price;
        private final String description;
        private final String type;

        private ListSelling(Long productId, String name, long price, String description, String type) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
        }

        public static ListSelling of(Long productId, String name, long price, String description, String type) {
            return new ListSelling(productId, name, price, description, type);
        }



    }

}
