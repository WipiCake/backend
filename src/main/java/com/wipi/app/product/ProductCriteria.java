package com.wipi.app.product;

import com.wipi.domain.product.IsThumbnail;
import com.wipi.domain.product.ProductCommand;
import com.wipi.domain.product.ProductSellingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCriteria {

    @Getter
    public static class Register {
        private final String name;
        private final long price;
        private final String description;
        private final String type;
        private final ProductSellingStatus sellStatus;
        private final long quantity;
        private final MultipartFile thumbNailImage;
        private final List<MultipartFile> detailImages;

        private Register(String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, MultipartFile thumbNailImage, List<MultipartFile> detailImages) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
            this.thumbNailImage = thumbNailImage;
            this.detailImages = detailImages;
        }

        public static Register of(String name, long price, String description, String type,ProductSellingStatus sellStatus,long quantity, MultipartFile thumbNailImage, List<MultipartFile> detailImages) {
            return new Register(name, price, description, type,sellStatus,quantity, thumbNailImage, detailImages);
        }

        public ProductCommand.Register toRegisterCommand(){
            return ProductCommand.Register.of(name,price,description,type,sellStatus,quantity);
        }

        public ProductCommand.RegisterThumbnailImage toRegisterThumbnailImageCommand(Long productId){
            return ProductCommand.RegisterThumbnailImage.of(productId, IsThumbnail.TRUE,thumbNailImage);
        }

        public ProductCommand.RegisterDetailImages toRegisterDetailImages(Long productId){
            return ProductCommand.RegisterDetailImages.of(detailImages,IsThumbnail.FALSE,productId);
        }

    }


}
