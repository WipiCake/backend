package com.wipi.inferfaces.controller.product;

import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductSellingStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ProductResponse {

    @Getter
    @Schema(description = "판매 중 상품정보 응답 DTO")
    public static class GetSelling {
        @Schema(description = "상품명", example = "초코 케이크")
        private final String name;

        @Schema(description = "가격 (원)", example = "25000")
        private final long price;

        @Schema(description = "상품 설명", example = "달콤한 초코 케이크")
        private final String description;

        @Schema(description = "상품 타입", example = "CAKE")
        private final String type;

        @Schema(description = "판매 상태", example = "SELLING")
        private final ProductSellingStatus sellStatus;

        @Schema(description = "재고 수량", example = "10")
        private final long quantity;

        @Schema(description = "썸네일 이미지 URL", example = "https://.../thumbnail.jpg")
        private final String thumbnail;

        @ArraySchema(
                arraySchema = @Schema(description = "상세 이미지 URL 목록"),
                schema = @Schema(type = "string", format = "uri")
        )
        private final List<String> detailImages;

        private GetSelling(
                String name,
                long price,
                String description,
                String type,
                ProductSellingStatus sellStatus,
                long quantity,
                String thumbnail,
                List<String> detailImages
        ) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
            this.thumbnail = thumbnail;
            this.detailImages = detailImages;
        }

        public static GetSelling of(
                String name,
                long price,
                String description,
                String type,
                ProductSellingStatus sellStatus,
                long quantity,
                String thumbnail,
                List<String> detailImages
        ) {
            return new GetSelling(name, price, description, type, sellStatus, quantity, thumbnail, detailImages);
        }

        public static GetSelling fromGetSellingInfo(ProductInfo.ListSelling listSelling) {
            return new GetSelling(
                    listSelling.getName(),
                    listSelling.getPrice(),
                    listSelling.getDescription(),
                    listSelling.getType(),
                    listSelling.getSellStatus(),
                    listSelling.getQuantity(),
                    listSelling.getThumbnail(),
                    listSelling.getDetailImages()
            );
        }
    }
}
