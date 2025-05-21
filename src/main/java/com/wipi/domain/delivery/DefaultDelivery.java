package com.wipi.domain.delivery;

public enum DefaultDelivery {
    TRUE,
    FALSE;

    public boolean isTrue() {
        return this == TRUE;
    }

    public boolean isFalse() {
        return this == FALSE;
    }

    public static DefaultDelivery fromBoolean(boolean value) {
        return value ? TRUE : FALSE;
    }

    public boolean toBoolean() {
        return this == TRUE;
    }
}
