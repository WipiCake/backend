package com.wipi.infra.delivery;

import com.wipi.domain.delivery.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryAddress, Long> {

}

