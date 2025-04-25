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
        //todo 1. 상품/재고 등록
        Long savedProductId = productService.register(criteria.toRegisterCommand());

        //todo 2. 썸네일 이미지 등록
        productService.registerThumbnailImage(criteria.toRegisterThumbnailImageCommand(savedProductId));

        //todo 3. 디테일 이미지 등록
        productService.registerDetailImages(criteria.toRegisterDetailImages(savedProductId));
    }


}
