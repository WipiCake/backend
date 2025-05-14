package com.wipi.domain.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public void save(DeliveryAddressCommand.Save command) {
        deliveryAddressRepository.save(command.toEntity());
    }


}
