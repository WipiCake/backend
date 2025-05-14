package com.wipi.domain.delivery;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    @Transactional
    public void save(DeliveryAddressCommand.Save command) {
        if (command.getDefaultDelivery().equals(DefaultDelivery.TRUE)) {
            findIdByDefaultAddress(command.getUserId(), DefaultDelivery.TRUE)
                    .ifPresent(deliveryAddress -> {
                        deliveryAddress.changeToNonDefault();
                        deliveryAddressRepository.save(deliveryAddress);
                    });
        }

        deliveryAddressRepository.save(command.toEntity());
    }

    private Optional<DeliveryAddress> findIdByDefaultAddress(String userId, DefaultDelivery defaultDelivery) {
        return deliveryAddressRepository.findByUserIdAndDefaultDelivery(userId, defaultDelivery);
    }


}
