package com.wipi.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {

    HOLD("보류"),
    SELLING("판매중"),
    STOP_SELLING("중지");

    private final String description;




}