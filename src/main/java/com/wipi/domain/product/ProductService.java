package com.wipi.domain.product;

import com.wipi.infra.product.ProductImageJpaRepository;
import com.wipi.infra.product.ProductJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductJpaRepository productJpaRepository;

    public List<ProductInfo.ListSelling> getAllSelling() {
        List<Product> list = productRepository.getProductAll();

        return list.stream()
                .filter(product -> product.getSellStatus().equals(ProductSellingStatus.SELLING))
                .map(product -> ProductInfo.ListSelling.of(
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getType()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void productRegister(ProductCommand.Register command){
        Product product = Product.create(
                command.getName(),
                command.getPrice(),
                command.getDescription(),
                command.getType(),
                command.getSellStatus()
        );
        Product savedProduct = productRepository.save(product);

        List<ProductImage> imageList = ProductImage.create(command.getRegisterImageList(), savedProduct.getProductId());
        productImageRepository.saveAll(imageList);
    }

    private void storeImages(){

    }




}
