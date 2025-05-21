package com.wipi.domain.product;

import com.wipi.domain.pick.Pick;
import com.wipi.infra.comm.CommFileService;
import com.wipi.support.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
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
    private final CommFileService commFileService;
    public final static String basePath = "/product";

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
        findProduct(command.getProductId());
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
        findProduct(command.getProductId());
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

        for (Product product : productList) {
            Stock stock = stockRepository.findByProductId(product.getProductId()).orElseThrow(
                    () -> new RuntimeException("해당 재고가 존재하지 않습니다 : "+ product.getProductId())
            );
            List<ProductImage> productImageList = productImageRepository.findByProductId(product.getProductId());

            String thumbnail = null;
            List<String> detailImages = new ArrayList<>();

            for (ProductImage image : productImageList) {

                String imageResource = commFileService.loadImage(image.getSavedFileName(), basePath);

                if (image.getIsThumbnail().equals(IsThumbnail.TRUE)) {
                    thumbnail = imageResource;
                } else {
                    detailImages.add(imageResource);
                }
            }

            ProductInfo.ListSelling listSelling = ProductInfo.ListSelling.of(
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getType(),
                    product.getSellStatus(),
                    stock.getQuantity(),
                    thumbnail,
                    detailImages
            );

            log.info("ListSelling DTO: {}", Utils.toJson(listSelling));
            result.add(listSelling);
        }

        return result;
    }

    public ProductInfo.ProductDetail getProductDetail(Long productId){
        Product product = findSellingProduct(productId);
        Stock stock = findValidStock(productId);
        List<ProductImage> images = productImageRepository.findByProductId(productId);

        String thumbnail = null;
        List<String> detailImages = new ArrayList<>();

        for(ProductImage image : images){

            String imageResource = commFileService.loadImage(image.getSavedFileName(), basePath);

            if(image.getIsThumbnail().equals(IsThumbnail.TRUE)){
                thumbnail = imageResource;
            } else{
                detailImages.add(imageResource);
            }
        }

        return ProductInfo.ProductDetail.of(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getType(),
                product.getSellStatus(),
                stock.getQuantity(),
                thumbnail,
                detailImages
        );


    }

    public List<ProductInfo.GetPickProducts> getPickProducts(List<Long> productIdList){

        List<ProductInfo.GetPickProducts> result = new ArrayList<>();

        for (Long productId : productIdList) {
            Product product = productRepository.findByProductId(productId)
                    .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

            String thumbnailPath = productImageRepository.findByProductId(productId).stream()
                    .filter(img -> img.getIsThumbnail().equals(IsThumbnail.TRUE))
                    .map(ProductImage::getSavedFileName)
                    .findFirst()
                    .orElse(null);

            long quantity = stockRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("해당 재고가 존재하지 않습니다.")).getQuantity();

            result.add(ProductInfo.GetPickProducts.of(
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getType(),
                    product.getSellStatus(),
                    quantity,
                    thumbnailPath
            ));
        }

        return result;
    }


    private Stock findValidStock(Long productId){
        Stock stock =  stockRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("해당 재고가 존재하지 않습니다 : " + productId)
        );
        if(stock.getQuantity() <= 0){
             throw new RuntimeException("해당 상품의 재고수량이 부족합니다 : " + productId);
        }
        return stock;
    }

    private Product findSellingProduct(Long productId){
        Product product = productRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("해당 상품이 존재하지 않습니다 : " + productId)
        );
        if(!product.getSellStatus().equals(ProductSellingStatus.SELLING)){
            throw new RuntimeException("해당 상품은 판매중이지 않습니다 : " + productId);
        }
        return product;
    }

    private Product findProduct(Long productId){
        return productRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("해당 상품이 존재하지 않습니다 : " + productId)
        );
    }

}
