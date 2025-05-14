package com.wipi.domain.delivery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressInfo {
    private Long deliveryAddressId;
    private String userId;
    private String title;
    private String zipAddress;
    private String mainAddress;
    private String detailAddress;
    private String phoneNumber;
    private DefaultDelivery defaultDelivery;

    @Getter
    public static class GetAll {
        private Long deliveryAddressId;
        private String userId;
        private String title;
        private String zipAddress;
        private String mainAddress;
        private String detailAddress;
        private String phoneNumber;
        private DefaultDelivery defaultDelivery;

        private GetAll() {}

        public static List<GetAll> fromEntity(List<DeliveryAddress> entities) {
            return entities.stream()
                    .map(entity -> {
                        GetAll dto = new GetAll();
                        dto.deliveryAddressId = entity.getDeliveryAddressId();
                        dto.userId = entity.getUserId();
                        dto.title = entity.getTitle();
                        dto.zipAddress = entity.getZipAddress();
                        dto.mainAddress = entity.getMainAddress();
                        dto.detailAddress = entity.getDetailAddress();
                        dto.phoneNumber = entity.getPhoneNumber();
                        dto.defaultDelivery = entity.getDefaultDelivery();
                        return dto;
                    })
                    .toList();
        }
    }

    @Getter
    public static class GetDetail {
        private Long deliveryAddressId;
        private String userId;
        private String title;
        private String zipAddress;
        private String mainAddress;
        private String detailAddress;
        private String phoneNumber;
        private DefaultDelivery defaultDelivery;

        private GetDetail() {
        }

        public static GetDetail fromEntity(DeliveryAddress entity) {
            GetDetail dto = new GetDetail();
            dto.deliveryAddressId = entity.getDeliveryAddressId();
            dto.userId = entity.getUserId();
            dto.title = entity.getTitle();
            dto.zipAddress = entity.getZipAddress();
            dto.mainAddress = entity.getMainAddress();
            dto.detailAddress = entity.getDetailAddress();
            dto.phoneNumber = entity.getPhoneNumber();
            dto.defaultDelivery = entity.getDefaultDelivery();
            return dto;
        }
    }
}
