package com.wipi.app.product;

import com.wipi.domain.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;

    @Transactional
    public void registerProduct(ProductCriteria.Register criteria){
        Long savedProductId = productService.register(criteria.toRegisterCommand());

        productService.registerThumbnailImage(criteria.toRegisterThumbnailImageCommand(savedProductId));

        productService.registerDetailImages(criteria.toRegisterDetailImages(savedProductId));
    }


}
