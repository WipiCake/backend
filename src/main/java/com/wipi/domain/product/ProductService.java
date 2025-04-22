package com.wipi.domain.product;

import com.wipi.inferfaces.product.ProductRequest;
import com.wipi.infra.product.ProductJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductJpaRepository productJpaRepository;

    public List<ProductInfo.ListSelling> getAllSelling() {
        List<Product> list = productRepository.findAll();

        return list.stream()
                .filter(product -> product.getSellStatus() == ProductSellingStatus.SELLING)
                .map(product -> {
                    String thumbnailPath = productImageRepository
                            .findByProductIdAndIsThumbnail(product.getProductId(), IsThumbnail.TRUE)
                            .map(ProductImage::getImagePath)
                            .orElse(null);

                    return ProductInfo.ListSelling.of(
                            product.getProductId(),
                            product.getName(),
                            product.getPrice(),
                            product.getDescription(),
                            product.getType(),
                            thumbnailPath
                    );
                })
                .collect(Collectors.toList());
    }



    @Transactional
    public void productRegister(ProductRequest.Register request, List<MultipartFile> imageFiles) {
        Product product = Product.create(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getType(),
                ProductSellingStatus.valueOf(request.getSellStatus().toUpperCase())
        );
        Product savedProduct = productRepository.save(product);

        String basePath = new File("src/main/resources/static/img/product").getAbsolutePath();
        List<ProductCommand.RegisterImage> registerImages = new ArrayList<>();

        for (int i = 0; i < imageFiles.size(); i++) {
            MultipartFile file = imageFiles.get(i);
            ProductRequest.RegisterImage imageMeta = request.getImageList().get(i);
            String savedFileName = saveImageFile(file, basePath);
            String imagePath = "/img/product/" + savedFileName;
            registerImages.add(imageMeta.toCommand(savedFileName, imagePath));
        }

        List<ProductImage> imageEntities = ProductImage.create(registerImages, savedProduct.getProductId());
        productImageRepository.saveAll(imageEntities);
    }

    private String saveImageFile(MultipartFile file, String saveDirectory) {
        String originalName = file.getOriginalFilename();
        String saveName = UUID.randomUUID() + "_" + originalName;

        File targetFile = new File(saveDirectory, saveName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패: " + originalName, e);
        }

        return saveName;
    }



}
