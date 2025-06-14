package com.wipi.domain.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderTransaction {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long createOrder(OrderCommand.CreateOrder command) {
        if (command.getOrderProducts() == null || command.getOrderProducts().isEmpty()) {
            throw new RuntimeException("상품이 비어 있습니다");
        }

        Order order = Order.create(
                command.getUserId(),
                command.getProductTotalAmount()
        );
        orderRepository.save(order);
        List<OrderItem> items = command.getOrderProducts().stream()
                .map(ci -> {
                    OrderItem item = OrderItem.of(ci.getProductId(), (long) ci.getQuantity().intValue());
                    item.setOrderId(order.getId());
                    return item;
                })
                .toList();

        orderItemRepository.saveAll(items);

        return order.getId();
    }



}
