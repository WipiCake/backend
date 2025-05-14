package com.wipi.inferfaces.controller.pick;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
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
        @Schema(description = "찜할 상품의 ID", example = "2", type = "integer")
        private final Long productId;

        @JsonCreator
        public Save(@JsonProperty("productId") Long productId) {
            this.productId = productId;
        }

        public static Save of(Long productId) {
            return new Save(productId);
        }

    }


}
