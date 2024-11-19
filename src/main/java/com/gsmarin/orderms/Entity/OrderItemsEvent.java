package com.gsmarin.orderms.Entity;

import java.math.BigDecimal;

public record OrderItemsEvent(String produto,
                              Integer quantidade,
                              BigDecimal preco) {
}
