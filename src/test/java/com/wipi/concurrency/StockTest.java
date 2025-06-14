package com.wipi.concurrency;

import com.wipi.domain.product.ProductCommand;
import com.wipi.domain.product.ProductService;
import com.wipi.domain.product.Stock;
import com.wipi.domain.product.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class StockTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    final Long productId = 12L;

    @BeforeEach
    void setup() {
        stockRepository.save(Stock.create(productId, 10L));
    }


    @Test
    void 동시에_100명이_재고를_차감하면_10개만_차감된다() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    productService.deductStock(ProductCommand.DeductStock.of(productId, 1L));
                } catch (Exception e) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock  = stockRepository.findByProductId(productId).orElseThrow();
        System.out.println("남은 재고 수량: " + stock.getQuantity());

        assertEquals(0L, stock.getQuantity());
    }
}
