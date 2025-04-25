package com.wipi.unit.product;

import com.wipi.domain.product.*;
import com.wipi.support.properties.ImagesPathProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ImagesPathProperties imagesPathProperties;

    private final String basePath = "/product";


    @Test
    @DisplayName("상품 등록이 정상적으로 된다")
    void registerProduct() {
        // given
        ProductCommand.Register command = ProductCommand.Register.of(
                "테스트 상품",
                10000L,
                "테스트 설명",
                "ELECTRONICS",
                ProductSellingStatus.SELLING,
                10L
        );

        // when
        productService.register(command);

        // then
        List<Product> products = productRepository.findAll();
        List<Stock> stocks = stockRepository.findAll();

        assertThat(products).hasSize(1);
        assertThat(stocks).hasSize(1);

        Product product = products.get(0);
        Stock stock = stocks.get(0);

        assertThat(product.getName()).isEqualTo("테스트 상품");
        assertThat(stock.getQuantity()).isEqualTo(10L);
        assertThat(stock.getProductId()).isEqualTo(product.getProductId());
    }

    @Test
    @DisplayName("썸네일 이미지 등록이 정상적으로 된다")
    void registerThumbnailImage() throws Exception {
        // given
        Product product = productRepository.save(Product.create("상품", 1000L, "설명", "TYPE", ProductSellingStatus.SELLING));
        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnail", "thumbnail.jpg", "image/jpeg", "test-thumbnail-content".getBytes()
        );

        ProductCommand.RegisterThumbnailImage command = ProductCommand.RegisterThumbnailImage.of(
                product.getProductId(),IsThumbnail.TRUE, thumbnail
        );

        // when
        productService.registerThumbnailImage(command);

        // then
        List<ProductImage> images = productImageRepository.findAll();
        assertThat(images).hasSize(1);


        ProductImage image = images.get(0);
        assertThat(image.getProductId()).isEqualTo(product.getProductId());
        assertThat(image.getIsThumbnail()).isEqualTo(IsThumbnail.TRUE);
    }

    @Test
    @DisplayName("디테일 이미지 여러개 등록이 정상적으로 된다")
    void registerDetailImages() throws Exception {
        // given
        Product product = productRepository.save(Product.create("상품", 1000L, "설명", "TYPE", ProductSellingStatus.SELLING));
        MockMultipartFile detail1 = new MockMultipartFile(
                "detail1", "detail1.jpg", "image/jpeg", "detail-image1".getBytes()
        );
        MockMultipartFile detail2 = new MockMultipartFile(
                "detail2", "detail2.jpg", "image/jpeg", "detail-image2".getBytes()
        );

        ProductCommand.RegisterDetailImages command = ProductCommand.RegisterDetailImages.of(
                List.of(detail1, detail2), IsThumbnail.TRUE, product.getProductId()
        );

        // when
        productService.registerDetailImages(command);

        // then
        List<ProductImage> images = productImageRepository.findAll();
        assertThat(images).hasSize(2);
        assertThat(images.get(0).getIsThumbnail()).isEqualTo(IsThumbnail.FALSE);
        assertThat(images.get(1).getIsThumbnail()).isEqualTo(IsThumbnail.FALSE);
    }






}
