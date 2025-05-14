package com.wipi.domain.delivery;

import java.util.Optional;

public interface DeliveryAddressRepository {
    void save(DeliveryAddress deliveryAddress);
    Optional<DeliveryAddress> findByUserIdAndDefaultDelivery(String userId, DefaultDelivery defaultDelivery);
    void deleteByIdAndUserId(Long deliveryAddressId, String userId);
    Optional<DeliveryAddress> findByIdAndUserId(Long id, String userId);
}
