package com.wipi.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IsThumbnail {

    TRUE("TRUE"),
    FALSE("FALSE");

    private final String description;

}
