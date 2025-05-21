package com.wipi.domain.delivery;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wipi_delivery_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Long deliveryAddressId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title", length = 49)
    private String title;

    @Column(name = "zip_address", length = 49)
    private String zipAddress;

    @Column(name = "main_address", length = 49)
    private String mainAddress;

    @Column(name = "detail_address", length = 49, nullable = true)
    private String detailAddress;

    @Column(name = "phone_number", length = 49)
    private String phoneNumber;

    @Column(name = "default_delivery",length = 11)
    @Enumerated(EnumType.STRING)
    private DefaultDelivery defaultDelivery;

    private DeliveryAddress(String userId,String title, String zipAddress,String mainAddress, String detailAddress, String phoneNumber,DefaultDelivery defaultDelivery) {
        this.userId = userId;
        this.title = title;
        this.zipAddress = zipAddress;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
        this.defaultDelivery = defaultDelivery;
    }

    public static DeliveryAddress create(
            String userId, String title, String zipAddress,
            String mainAddress, String detailAddress, String phoneNumber,
            DefaultDelivery defaultDelivery
    ) {
        return new DeliveryAddress(userId, title,zipAddress, mainAddress, detailAddress, phoneNumber, defaultDelivery);
    }
    public void changeToNonDefault() {
        this.defaultDelivery = DefaultDelivery.FALSE;
    }

    public void update(DeliveryAddressCommand.Update command) {
        this.title = command.getTitle();
        this.zipAddress = command.getZipAddress();
        this.mainAddress = command.getMainAddress();
        this.detailAddress = command.getDetailAddress();
        this.phoneNumber = command.getPhoneNumber();
        this.defaultDelivery = command.getDefaultDelivery();
    }
}
