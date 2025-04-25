package com.wipi.domain.product;

import com.wipi.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "wipi_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long price;

    @Column(length = 2000)
    private String description;

    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "sell_status")
    private ProductSellingStatus sellStatus;

    private Product(String name, long price, String description, String type, ProductSellingStatus sellStatus) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.sellStatus = sellStatus;
    }

    public static Product create(String name, long price, String description, String type, ProductSellingStatus sellStatus) {
        return new Product(name, price,description,type,sellStatus);
    }

    private static void validate(String name, long price, ProductSellingStatus sellStatus) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("상품 가격은 0보다 커야 합니다.");
        }

        if (sellStatus == null) {
            throw new IllegalArgumentException("상품 판매 상태는 필수입니다.");
        }
    }
}