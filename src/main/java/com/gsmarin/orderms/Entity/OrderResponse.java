package com.gsmarin.orderms.Entity;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal totalPrice) {

    public static OrderResponse fromEntity(OrderEntity orderEntity) {
        return new OrderResponse(orderEntity.getOrderId(),
                orderEntity.getCustomerId(),
                orderEntity.getTotalPrice());
    }
}
