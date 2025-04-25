package com.wipi.infra.product;

import com.wipi.domain.product.Stock;
import com.wipi.domain.product.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public Stock save(Stock stock) {
        return stockJpaRepository.save(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockJpaRepository.findAll();
    }
}
