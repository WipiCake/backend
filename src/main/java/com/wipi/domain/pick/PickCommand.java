package com.wipi.domain.pick;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PickCommand {

    @Getter
    public static class Save{
        private final Long productId;
        private final String userId;

        private Save(Long productId, String userId) {
            this.productId = productId;
            this.userId = userId;
        }

        public static PickCommand.Save of(Long productId, String userId) {
            return new Save(productId, userId);
        }
    }


    @Getter
    public static class Delete {
        private final Long pickId;
        private final String userId;

        private Delete(Long pickId, String userId) {
            this.pickId = pickId;
            this.userId = userId;
        }

        public static PickCommand.Delete of(Long pickId, String userId) {
            return new Delete(pickId, userId);
        }

    }

    @Getter
    public static class GetUserPicks {
        private final String userId;

        private GetUserPicks(String userId) {
            this.userId = userId;
        }

        public static PickCommand.GetUserPicks of(String userId) {
            return new GetUserPicks(userId);
        }
    }
}
