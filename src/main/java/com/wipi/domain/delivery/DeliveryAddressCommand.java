package com.wipi.domain.delivery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressCommand {

    @Getter
    public static class Save {
        private final String userId;
        private final String title;
        private final String zipAddress;
        private final String mainAddress;
        private final String detailAddress;
        private final String phoneNumber;

        private final DefaultDelivery defaultDelivery;

        private Save(
                String userId,
                String title,
                String zipAddress,
                String mainAddress,
                String detailAddress,
                String phoneNumber,
                DefaultDelivery defaultDelivery
        ) {
            this.userId = userId;
            this.title = title;
            this.zipAddress = zipAddress;
            this.mainAddress = mainAddress;
            this.detailAddress = detailAddress;
            this.phoneNumber = phoneNumber;
            this.defaultDelivery = defaultDelivery;
        }

        public static Save of(
                String userId,
                String title,
                String zipAddress,
                String mainAddress,
                String detailAddress,
                String phoneNumber,
                DefaultDelivery defaultDelivery
        ) {
            return new Save(userId, title, zipAddress, mainAddress, detailAddress, phoneNumber, defaultDelivery);
        }

        public DeliveryAddress toEntity() {
            return DeliveryAddress.create(
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
