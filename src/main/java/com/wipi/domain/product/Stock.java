package com.wipi.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wipi_stock")
public class Stock {
    @Id
    @Column(name = "stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sockId;

    @Column(name = "product_id", unique = true)
    private Long productId;

    private long quantity;

    private Stock(Long productId, long quantity) {
        validateQuantity(quantity);
        this.productId = productId;
        this.quantity = quantity;
    }

    public static Stock create(Long productId, long quantity) {
        return new Stock(productId, quantity);
    }

    public void deduct(long quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.quantity -= quantity;
    }

    public void recovery(long quantity) {
        this.quantity += quantity;
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
        }
    }
}
