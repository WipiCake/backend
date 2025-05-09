package com.wipi.domain.pick;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wipi_pick")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickId;
    private Long productId;
    private String userId;

    private Pick(Long productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public static Pick create(Long productId,String userId) {
        return new Pick(productId,userId);
    }

    public static Pick of(Long productId, String userId) {
        return new Pick(productId,userId);
    }


}
