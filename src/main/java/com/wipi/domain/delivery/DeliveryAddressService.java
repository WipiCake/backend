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
            findIdByDefaultAddress(command.getUserId())
                    .ifPresent(deliveryAddress -> {
                        deliveryAddress.changeToNonDefault();
                        deliveryAddressRepository.save(deliveryAddress);
                    });
        }

        deliveryAddressRepository.save(command.toEntity());
    }

    @Transactional
    public void deleteDeliveryAddress(Long id, String userId){
        deliveryAddressRepository.deleteByIdAndUserId(id, userId);
    }


    private Optional<DeliveryAddress> findIdByDefaultAddress(String userId) {
        return deliveryAddressRepository.findByUserIdAndDefaultDelivery(userId, DefaultDelivery.TRUE);
    }


}
