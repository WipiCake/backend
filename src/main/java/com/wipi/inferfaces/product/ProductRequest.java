package com.wipi.inferfaces.product;

import com.wipi.domain.product.ProductSellingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    @Getter
    public static class Register {

        @NotBlank
        private final String name;

        @Positive
        private final long price;

        @NotBlank
        private final String description;

        @NotBlank
        private final String type;

        @NotNull
        private final ProductSellingStatus sellStatus;

        @Positive
        private final long quantity;

        private Register(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
        }




    }



}
