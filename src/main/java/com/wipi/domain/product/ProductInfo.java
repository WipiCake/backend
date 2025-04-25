package com.wipi.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {

    @Getter
    public static class ListSelling {
        private final String name;
        private final long price;
        private final String description;
        private final String type;
        private final ProductSellingStatus sellStatus;
        private final long quantity;
        private final String thumbnail;
        private final List<String> detailImages;

        private ListSelling(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail, List<String> detailImages) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
            this.thumbnail = thumbnail;
            this.detailImages = detailImages;
        }

        public static ListSelling of(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail, List<String> detailImages) {
            return new ListSelling(name, price, description, type, sellStatus, quantity, thumbnail, detailImages);
        }

    }

}
