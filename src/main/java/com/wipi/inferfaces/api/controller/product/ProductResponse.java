package com.wipi.inferfaces.api.controller.product;


import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductSellingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

import java.util.List;

@NoArgsConstructor
public class ProductResponse {

    @Getter
    public static class GetSelling {
        private final String name;
        private final long price;
        private final String description;
        private final String type;
        private final ProductSellingStatus sellStatus;
        private final long quantity;
        private final String thumbnail;
        private final List<String> detailImages;

        private GetSelling(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail, List<String> detailImages) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
            this.thumbnail = thumbnail;
            this.detailImages = detailImages;
        }

        public static GetSelling of(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail, List<String> detailImages) {
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
