package com.wipi.inferfaces.controller.product;

import com.wipi.app.product.ProductCriteria;
import com.wipi.domain.product.ProductSellingStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    @Schema(description = "신규 상품등록 요청 dto")
    public static class Register {
        @Schema(description = "상품명", example = "초코 케이크")
        private String name;
        @Schema(description = "가격 (원)", example = "25000")
        private long price;
        @Schema(description = "상품 설명", example = "달콤한 초코 케이크입니다.")
        private String description;
        @Schema(description = "상품 타입", example = "CAKE")
        private String type;
        @Schema(description = "판매 상태", example = "SELLING")
        private ProductSellingStatus sellStatus;
        @Schema(description = "재고 수량", example = "10")
        private long quantity;
        @Schema(description = "썸네일 이미지 파일 (binary)", type = "string", format = "binary")
        private MultipartFile thumbNailImage;
        @ArraySchema(
                schema = @Schema(
                        description = "상세 이미지 파일 목록 (binary)",
                        type = "string", format = "binary"
                )
        )
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
