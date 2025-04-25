package com.wipi.domain.product;

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
        Map<String,String> savedImage = saveImagesForPath(command.getThumbnailImage());

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
            savedImages.add(saveImagesForPath(file));
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


    // 단일 상품 이미지 저장
    private Map<String, String> saveImagesForPath(MultipartFile file) {
        try {
            String uploadDir = imagesPathProperties.getPath().replace("file:", "") + basePath;
            String uuid = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String savedFileName = uuid + "_" + originalFileName;
            String savedFullPath = uploadDir + File.separator + savedFileName;

            File dest = new File(savedFullPath);


            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            String webPath = imagesPathProperties.getSrc().replace("/**", "") + basePath + "/" + savedFileName;

            return Map.of(
                    "originalFileName", originalFileName,
                    "savedFileName", savedFileName,
                    "webPath", webPath
            );
        } catch (Exception e) {
            throw new RuntimeException("썸네일 이미지 저장 중 오류가 발생했습니다.", e);
        }
    }


}
