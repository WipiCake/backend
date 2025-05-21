package com.wipi.infra.delivery;

import com.wipi.domain.delivery.DefaultDelivery;
import com.wipi.domain.delivery.DeliveryAddress;
import com.wipi.domain.delivery.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryAddressRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public void save(DeliveryAddress deliveryAddress) {
        deliveryJpaRepository.save(deliveryAddress);
    }

    @Override
    public Optional<DeliveryAddress> findByUserIdAndDefaultDelivery(String userId, DefaultDelivery defaultDelivery) {
        return deliveryJpaRepository.findByUserIdAndDefaultDelivery(userId, defaultDelivery);
    }

    @Override
    public void deleteByIdAndUserId(Long deliveryAddressId, String userId) {
        deliveryJpaRepository.deleteByDeliveryAddressIdAndUserId(deliveryAddressId,userId);
    }

    @Override
    public Optional<DeliveryAddress> findByIdAndUserId(Long id, String userId) {
        return deliveryJpaRepository.findByDeliveryAddressIdAndUserId(id,userId);
    }

    @Override
    public List<DeliveryAddress> findAllByUserId(String userId) {
        return deliveryJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        return deliveryJpaRepository.findById(id);
    }
}
