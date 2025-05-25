package com.wipi.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommand {

    @Getter
    public static class Register {
        private final String name;
        private final long price;
        private final String description;
        private final String type;
        private final ProductSellingStatus sellStatus;
        private final long quantity;

        private Register(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
        }

        public static Register of(String name, long price, String description, String type,
                                  ProductSellingStatus sellStatus, long quantity) {
            return new Register(name, price, description, type, sellStatus, quantity);
        }
    }

    @Getter
    public static class RegisterThumbnailImage{
        private final Long productId;
        private final IsThumbnail isThumbnail;
        private final MultipartFile thumbnailImage;

        private RegisterThumbnailImage(Long productId,IsThumbnail isThumbnail, MultipartFile thumbnailImage) {
            this.productId = productId;
            this.isThumbnail = isThumbnail;
            this.thumbnailImage = thumbnailImage;
        }

        public static RegisterThumbnailImage of(Long productId,IsThumbnail isThumbnail, MultipartFile thumbnailImage) {
            return new RegisterThumbnailImage(productId,isThumbnail, thumbnailImage);
        }

    }

    @Getter
    public static class RegisterDetailImages{
        private final Long productId;
        private final List<MultipartFile> detailImages;
        private final IsThumbnail isThumbnail;

        public RegisterDetailImages(Long productId, List<MultipartFile> detailImages, IsThumbnail isThumbnail) {
            this.productId = productId;
            this.detailImages = detailImages;
            this.isThumbnail = isThumbnail;
        }

        public static RegisterDetailImages of(List<MultipartFile> detailImages, IsThumbnail isThumbnail, Long productId) {
            return new RegisterDetailImages(productId,detailImages, isThumbnail);
        }
    }

    @Getter
    public static class CalculateProductsPrice{
        private final Long productId;
        private final Long quantity;

        public CalculateProductsPrice(Long productId, Long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public static CalculateProductsPrice of(Long productId, Long quantity) {
            return new CalculateProductsPrice(productId,quantity);
        }
    }

    @Getter
    public static class DeductStock{
        private final Long productId;
        private final Long quantity;

        private DeductStock(Long productId, Long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public static DeductStock of(Long productId, Long quantity) {
            return new DeductStock(productId,quantity);
        }
    }



}
