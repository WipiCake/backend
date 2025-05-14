package com.wipi.infra.delivery;

import com.wipi.domain.delivery.DeliveryAddress;
import com.wipi.domain.delivery.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryAddressRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public void save(DeliveryAddress deliveryAddress) {
        deliveryJpaRepository.save(deliveryAddress);
    }
}
