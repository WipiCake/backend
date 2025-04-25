package com.wipi.domain.product;


import java.util.List;

public interface StockRepository {
    Stock save(Stock stock);
    List<Stock> findAll();
}
