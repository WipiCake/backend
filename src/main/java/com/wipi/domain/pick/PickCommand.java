package com.wipi.domain.pick;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PickCommand {

    @Getter
    public static class Save{
        private Long productId;
        private String userId;

        private Save(Long productId, String userId) {
            this.productId = productId;
            this.userId = userId;
        }

        public static PickCommand.Save of(Long productId, String userId) {
            return new Save(productId, userId);
        }
    }

}
