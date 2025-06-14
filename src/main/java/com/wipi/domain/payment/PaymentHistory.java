package com.wipi.domain.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wipi_payment_history")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_id")
    private Long paymentHistoryId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String status;

    private PaymentHistory(String userId, Long amount, Long orderId, String transactionId, String status) {
        this.userId = userId;
        this.amount = amount;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.status = status;
    }

    public static PaymentHistory create(String userId, Long amount, Long orderId, String transactionId, String status) {
        return new PaymentHistory(userId, amount, orderId, transactionId, status);
    }
}
