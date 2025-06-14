package com.wipi.domain.order;

import com.wipi.infra.order.OrderItemJpaRepository;
import com.wipi.support.aop.DistributedLock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderTransaction orderTransaction;

    @DistributedLock(key = "'order:user:' + #command.userId", waitTime = 3, leaseTime = 5)
    public Long createOrder(OrderCommand.CreateOrder command) {
        return orderTransaction.createOrder(command);
    }

    public OrderInfo.GetOrder getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다. orderId=" + orderId));
        return OrderInfo.GetOrder.of(
                order.getId(),
                order.getOrderStatus(),
                order.getProductTotalPrice()
        );
    }

    public OrderInfo.GetOrderItems getOrderItemByOrderId(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        return OrderInfo.GetOrderItems.of(
                items.stream()
                        .map(item -> OrderInfo.OrderProduct.of(item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("해당 주문이 존재하지 않습니다")
        );
        order.updateStatus(orderStatus);

        orderRepository.save(order);
    }


}
