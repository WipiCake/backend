package com.wipi.inferfaces.controller.delivery;

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
    public static class Save {

        @Schema(description = "배송지 제목", example = "우리집")
        @NotBlank(message = "배송지 제목은 필수입니다.")
        private final String title;

        @Schema(description = "우편번호", example = "12345")
        @NotBlank(message = "우편번호는 필수입니다.")
        private final String zipAddress;

        @Schema(description = "기본 주소", example = "서울특별시 강남구 테헤란로 123")
        @NotBlank(message = "기본 주소는 필수입니다.")
        private final String mainAddress;

        @Schema(description = "상세 주소", example = "101동 202호")
        private final String detailAddress;

        @Schema(description = "휴대폰 번호", example = "01012345678")
        @NotBlank(message = "전화번호는 필수입니다.")
        private final String phoneNumber;

        @Schema(description = "기본 배송지 여부", example = "TRUE")
        @NotNull(message = "기본 배송지 여부는 필수입니다.")
        private final DefaultDelivery defaultDelivery;

        public Save(
                String title,
                String zipAddress,
                String mainAddress,
                String detailAddress,
                String phoneNumber,
                DefaultDelivery defaultDelivery
        ) {
            this.title = title;
            this.zipAddress = zipAddress;
            this.mainAddress = mainAddress;
            this.detailAddress = detailAddress;
            this.phoneNumber = phoneNumber;
            this.defaultDelivery = defaultDelivery;
        }

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
