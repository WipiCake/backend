package com.wipi.inferfaces.api.controller.product;

import com.wipi.app.product.ProductCriteria;
import com.wipi.domain.product.ProductSellingStatus;
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
        private String name;
        private long price;
        private String description;
        private String type;
        private ProductSellingStatus sellStatus;
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
