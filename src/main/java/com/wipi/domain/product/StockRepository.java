package com.wipi.domain.product;


import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Stock save(Stock stock);
    List<Stock> findAll();
    Optional<Stock> findByProductId(Long productId);
}
