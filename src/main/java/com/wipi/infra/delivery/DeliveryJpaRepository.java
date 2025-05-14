package com.wipi.infra.delivery;

import com.wipi.domain.delivery.DefaultDelivery;
import com.wipi.domain.delivery.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryAddress, Long> {
    Optional<DeliveryAddress> findByUserIdAndDefaultDelivery(String userId, DefaultDelivery defaultDelivery);

    void deleteByDeliveryAddressIdAndUserId(Long id,String userId);
}

