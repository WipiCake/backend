package com.wipi.app.pick;

import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductSellingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PickResult {

    @Getter
    public static class GetUserPicks {
        private final Long productId;
        private final String name;
        private final long price;
        private final String description;
        private final String type;
        private final ProductSellingStatus sellStatus;
        private final long quantity;
        private final String thumbnail;

        private GetUserPicks(Long productId, String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.description = description;
            this.type = type;
            this.sellStatus = sellStatus;
            this.quantity = quantity;
            this.thumbnail = thumbnail;
        }

        public static GetUserPicks of(Long productId, String name, long price, String description, String type, ProductSellingStatus sellStatus, long quantity, String thumbnail) {
            return new GetUserPicks(productId, name, price, description, type, sellStatus, quantity, thumbnail);
        }

        public static List<GetUserPicks> from(List<ProductInfo.GetPickProducts> products) {
            return products.stream()
                    .map(p -> GetUserPicks.of(
                            p.getProductId(),
                            p.getName(),
                            p.getPrice(),
                            p.getDescription(),
                            p.getType(),
                            p.getSellStatus(),
                            p.getQuantity(),
                            p.getThumbnail()
                    ))
                    .toList();
        }
    }

}
