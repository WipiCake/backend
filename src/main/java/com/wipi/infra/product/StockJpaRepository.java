package com.wipi.infra.product;

import com.wipi.domain.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<Stock, Long> {

}
