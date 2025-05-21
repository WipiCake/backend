package com.wipi.domain.pick;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PickInfo {

    @Getter
    public static class GetUserPicks {
        private final Long productId;
        private final String userId;

        private GetUserPicks(Long productId, String userId) {
            this.productId = productId;
            this.userId = userId;
        }

        public static GetUserPicks of(Long productId, String userId) {
            return new GetUserPicks(productId, userId);
        }

    }

}
