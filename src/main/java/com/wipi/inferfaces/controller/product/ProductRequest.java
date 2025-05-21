package com.wipi.inferfaces.controller.product;

import com.wipi.app.product.ProductCriteria;
import com.wipi.domain.product.ProductSellingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Register {
        @Schema(description = "상품명", example = "애플 에어팟 프로")
        private String name;

        @Schema(description = "상품 가격", example = "329000")
        private long price;

        @Schema(description = "상품 설명", example = "노이즈 캔슬링 기능이 있는 무선 이어폰")
        private String description;

        @Schema(description = "상품 종류", example = "전자기기")
        private String type;

        @Schema(description = "판매 상태", example = "SELLING", implementation = ProductSellingStatus.class)
        private ProductSellingStatus sellStatus;

        @Schema(description = "재고 수량", example = "100")
        private long quantity;

        private MultipartFile thumbNailImage;

        private List<MultipartFile> detailImages;

        private Register(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
        }

        public static Register of(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity) {
            return new Register(name, price, description, type, sellStatus, quantity);
        }


        public ProductCriteria.Register toCriteria() {
            return ProductCriteria.Register.of(
                    name, price, description, type, sellStatus, quantity, thumbNailImage, detailImages
            );
        }
    }

}
