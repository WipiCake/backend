package com.wipi.inferfaces.controller.pick;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PickRequest {

    @Getter
    public static class Save{
        @NotNull(message = "상품 ID 값이 없습니다.")
        @Positive(message = "상품 ID 값이 잘못되었습니다.")
        private final Long productId;

        private Save(Long productId) {
            this.productId = productId;
        }

        public static Save of(Long productId) {
            return new Save(productId);
        }

    }


}
