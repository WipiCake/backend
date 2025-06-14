package com.wipi.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    CREATED("주문생성"),
    PAID("결제완료"),
    CANCELED("주문취소");

    private final String description;
}
