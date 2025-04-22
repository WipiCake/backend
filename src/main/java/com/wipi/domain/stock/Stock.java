package com.wipi.domain.stock;

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

    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    public Stock(Long productId, int quantity) {
        validateQuantity(quantity);
        this.productId = productId;
        this.quantity = quantity;
    }

    public static Stock create(Long productId, int quantity) {
        return new Stock(productId, quantity);
    }

    public void deduct(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.quantity -= quantity;
    }

    public void recovery(int quantity) {
        this.quantity += quantity;
    }

    private static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
        }
    }
}
