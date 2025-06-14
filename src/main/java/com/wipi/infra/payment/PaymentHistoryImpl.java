package com.wipi.infra.payment;

import com.wipi.domain.payment.PaymentHistory;
import com.wipi.domain.payment.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentHistoryImpl implements PaymentHistoryRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void save(PaymentHistory paymentHistory) {
        paymentJpaRepository.save(paymentHistory);
    }
}
