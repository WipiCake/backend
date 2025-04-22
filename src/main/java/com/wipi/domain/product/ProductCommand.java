package com.wipi.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        private final List<RegisterImage> registerImageList;

        private Register(String name, long price, String description, String type, ProductSellingStatus sellStatus, List<RegisterImage> registerImageList) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.registerImageList = registerImageList;
        }

        public static Register of(String name, long price, String description, String type, ProductSellingStatus sellStatus, List<RegisterImage> registerImageList) {
            return new Register(name, price, description, type, sellStatus, registerImageList);
        }
    }

    @Getter
    public static class RegisterImage {
        private final String imageName;
        private final String imagePath;
        private final IsThumbnail isThumbnail;

        private RegisterImage(String imageName, String imagePath, IsThumbnail isThumbnail) {
            this.imageName = imageName;
            this.imagePath = imagePath;
            this.isThumbnail = isThumbnail;
        }

        public static RegisterImage of(String imageName, String imagePath, IsThumbnail isThumbnail) {
            return new RegisterImage(imageName, imagePath, isThumbnail);
        }
    }


}
