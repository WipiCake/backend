package com.wipi.domain.product;

import com.wipi.infra.comm.CommFileService;
import com.wipi.support.properties.ImagesPathProperties;
import com.wipi.support.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
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

    public List<ProductInfo.ListSelling> getAllSellingList() {
        List<Product> productList = productRepository.findAllBySellStatus(ProductSellingStatus.SELLING);
        List<ProductInfo.ListSelling> result = new ArrayList<>();

        log.info("[상품 개수] {}", productList.size());

        for (Product product : productList) {
            log.info("▶ 상품 정보: {}", Utils.toJson(product));

            Stock stock = stockRepository.findByProductId(product.getProductId())
                    .orElseThrow(() -> new RuntimeException("재고가 존재하지 않습니다: " + product.getProductId()));
            log.info("▶ 재고 정보: {}", Utils.toJson(stock));

            List<ProductImage> productImageList = productImageRepository.findByProductId(product.getProductId());
            log.info("▶ 이미지 개수: {}", productImageList.size());

            String thumbnail = null;
            List<String> detailImages = new ArrayList<>();

            for (ProductImage image : productImageList) {
                log.info(" - 이미지 정보: {}", Utils.toJson(image));

                String imageResource = commFileService.loadImage(image.getSavedFileName(), basePath);

                if (image.getIsThumbnail().equals(IsThumbnail.TRUE)) {
                    thumbnail = imageResource;
                    log.info("   ㄴ 썸네일로 설정됨");
                } else {
                    detailImages.add(imageResource);
                    log.info("   ㄴ 상세이미지로 추가됨");
                }
            }

            ProductInfo.ListSelling listSelling = ProductInfo.ListSelling.of(
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getType(),
                    product.getSellStatus(),
                    stock.getQuantity(),
                    thumbnail,
                    detailImages
            );

            log.info("▶ 변환된 ListSelling DTO: {}", Utils.toJson(listSelling));
            result.add(listSelling);
        }

        log.info("📦 최종 반환 리스트: {}", Utils.toJson(result));
        return result;
    }




    private void validProductId(Long productId){
        productRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 상품입니다 : " + productId)
        );
    }


}
