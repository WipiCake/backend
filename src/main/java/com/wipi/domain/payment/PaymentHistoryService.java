package com.wipi.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public void save(PaymentHistoryCommand.Save command) {
        paymentHistoryRepository.save(PaymentHistory.create(command.getUserId(),
        command.getAmount(),command.getOrderId(),command.getTransactionId(),command.getStatus()));
    }

}
