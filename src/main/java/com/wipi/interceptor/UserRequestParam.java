package com.wipi.interceptor;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
public class UserRequestParam {
    long startTime = System.currentTimeMillis();
    AtomicInteger requestCount = new AtomicInteger(0);
}
