package com.wipi.domain.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentHistoryCommand {

    @Getter
    public static class Save {
        private final String userId;
        private final Long amount;
        private final Long orderId;
        private final String transactionId;
        private final String status;

        private Save(String userId, Long amount, Long orderId, String transactionId, String status) {
            this.userId = userId;
            this.amount = amount;
            this.orderId = orderId;
            this.transactionId = transactionId;
            this.status = status;
        }

        public static Save of(String userId, Long amount, Long orderId, String transactionId, String status) {
            return new Save(userId, amount, orderId, transactionId, status);
        }
    }

}
