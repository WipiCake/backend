package com.wipi.domain.product;

import java.util.List;

public interface ProductRepository {
    List<Product> getProductAll();
    Product save(Product product);
}
