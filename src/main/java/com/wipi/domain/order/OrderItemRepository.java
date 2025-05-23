package com.wipi.domain.order;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findByOrderId(Long orderId);
    void saveAll(List<OrderItem> items);
}
