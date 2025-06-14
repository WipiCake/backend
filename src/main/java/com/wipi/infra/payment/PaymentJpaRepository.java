package com.wipi.infra.payment;

import com.wipi.domain.payment.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentHistory, Long> {

}
