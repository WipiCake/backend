package com.wipi.domain.product;

import com.wipi.infra.comm.CommFileService;
import com.wipi.support.properties.ImagesPathProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductImageRepository productImageRepository;
    private final ImagesPathProperties imagesPathProperties;
    private final CommFileService commFileService;
    private final String basePath = "/product";

    @Transactional
    public Long register(ProductCommand.Register command){
        Product product = productRepository.save(Product.create(
                command.getName(),
                command.getPrice(),
                command.getDescription(),
                command.getType(),
                command.getSellStatus()
        ));

        stockRepository.save(Stock.create(
                product.getProductId(),
                command.getQuantity())
        );
        return product.getProductId();
    }

    @Transactional
    public void registerThumbnailImage(ProductCommand.RegisterThumbnailImage command){
        validProductId(command.getProductId());
        Map<String,String> savedImage = commFileService.saveImagesForPath(command.getThumbnailImage(), basePath);

        productImageRepository.save(ProductImage.of(
                command.getProductId(),
                savedImage.get("originalFileName"),
                savedImage.get("savedFileName"),
                savedImage.get("webPath"),
                command.getIsThumbnail()
        ));
    }

    @Transactional
    public void registerDetailImages(ProductCommand.RegisterDetailImages command){
        validProductId(command.getProductId());
        List<MultipartFile> files = command.getDetailImages();

        if(files.size() > 10){
            throw new RuntimeException("이미지 10개 이상 저장이 불가합니다.");
        }

        List<Map<String,String>> savedImages = new ArrayList<>();

        for(MultipartFile file : files){
            savedImages.add(commFileService.saveImagesForPath(file, basePath));
        }

        for(Map<String,String> savedImage : savedImages){
            productImageRepository.save(ProductImage.of(
                    command.getProductId(),
                    savedImage.get("originalFileName"),
                    savedImage.get("savedFileName"),
                    savedImage.get("webPath"),
                    command.getIsThumbnail()
            ));
        }
    }


    private void validProductId(Long productId){
        productRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 상품입니다 : " + productId)
        );
    }


}
