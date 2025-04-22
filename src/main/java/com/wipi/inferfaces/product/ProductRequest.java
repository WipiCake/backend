package com.wipi.inferfaces.product;

import com.wipi.domain.product.IsThumbnail;
import com.wipi.domain.product.ProductCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    @Getter
    @NoArgsConstructor
    public static class Register {

        @NotBlank(message = "상품명은 필수입니다.")
        private String name;

        @Positive(message = "가격은 0보다 커야 합니다.")
        private long price;

        @NotBlank(message = "상품 설명은 필수입니다.")
        private String description;

        @NotBlank(message = "상품 유형은 필수입니다.")
        private String type;

        @Getter
        @NotBlank(message = "판매 상태는 필수입니다.")
        private String sellStatus;

        @NotNull(message = "이미지 목록은 필수입니다.")
        @Size(min = 1, message = "최소 1개의 이미지를 등록해야 합니다.")
        private List<@Valid RegisterImage> imageList;

    }

    @Getter
    @NoArgsConstructor
    public static class RegisterImage {

        @NotBlank(message = "썸네일 여부는 필수입니다.")
        private String isThumbnail;

        public ProductCommand.RegisterImage toCommand(String imageName, String imagePath) {
            return ProductCommand.RegisterImage.of(
                    imageName,
                    imagePath,
                    IsThumbnail.valueOf(isThumbnail.toUpperCase())
            );
        }
    }
}
