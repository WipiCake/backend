package com.wipi.inferfaces.controller.delivery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wipi.domain.delivery.DefaultDelivery;
import com.wipi.domain.delivery.DeliveryAddressCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressRequest {

    @Getter
    @NoArgsConstructor
    public static class Save {

        @Schema(description = "배송지 제목", example = "우리집")
        @NotBlank(message = "배송지 제목은 필수입니다.")
        private String title;

        @Schema(description = "우편번호", example = "12345")
        @NotBlank(message = "우편번호는 필수입니다.")
        private String zipAddress;

        @Schema(description = "기본 주소", example = "서울특별시 강남구 테헤란로 123")
        @NotBlank(message = "기본 주소는 필수입니다.")
        private String mainAddress;

        @Schema(description = "상세 주소", example = "101동 202호")
        private String detailAddress;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        @NotBlank(message = "전화번호는 필수입니다.")
        private String phoneNumber;

        @Schema(description = "기본 배송지 여부", example = "TRUE")
        @NotNull(message = "기본 배송지 여부는 필수입니다.")
        private DefaultDelivery defaultDelivery;

        public DeliveryAddressCommand.Save toCommand(String userId) {
            return DeliveryAddressCommand.Save.of(
                    userId,
                    title,
                    zipAddress,
                    mainAddress,
                    detailAddress,
                    phoneNumber,
                    defaultDelivery
            );
        }
    }
}
