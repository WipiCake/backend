package com.wipi.domain.delivery;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public List<DeliveryAddressInfo.GetAll> getAll(String userId){
        List<DeliveryAddress> list = deliveryAddressRepository.findAllByUserId(userId);
        return DeliveryAddressInfo.GetAll.fromEntity(list);
    }

    public DeliveryAddressInfo.GetDetail getDetail(Long id, String userId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndUserId(id,userId).orElseThrow(()
                -> new RuntimeException("해당 배송지 정보를 찾을 수 없습니다"));

        return DeliveryAddressInfo.GetDetail.fromEntity(deliveryAddress);
    }

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
        deliveryAddressRepository.findByIdAndUserId(id,userId).orElseThrow(
                () -> new RuntimeException("해당 배송주소지가 존재하지 않습니다."));
        deliveryAddressRepository.deleteByIdAndUserId(id, userId);
    }

    @Transactional
    public void updateDeliveryAddress(DeliveryAddressCommand.Update command) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndUserId(
                command.getDeliveryAddressId(),
                command.getUserId()
        ).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 배송지입니다. id=" + command.getDeliveryAddressId())
        );

        if (command.getDefaultDelivery() == DefaultDelivery.TRUE) {
            findIdByDefaultAddress(command.getUserId())
                    .filter(existingDefault -> !existingDefault.getDeliveryAddressId().equals(deliveryAddress.getDeliveryAddressId()))
                    .ifPresent(existingDefault -> {
                        existingDefault.changeToNonDefault();
                        deliveryAddressRepository.save(existingDefault);
                    });
        }

        deliveryAddress.update(command);
    }

    private Optional<DeliveryAddress> findIdByDefaultAddress(String userId) {
        return deliveryAddressRepository.findByUserIdAndDefaultDelivery(userId, DefaultDelivery.TRUE);
    }


}
