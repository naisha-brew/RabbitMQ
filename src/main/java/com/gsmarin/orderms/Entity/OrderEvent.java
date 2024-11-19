package com.gsmarin.orderms.Entity;

import java.util.List;

public record OrderEvent(Long codigoPedido,
                         Long codigoCliente,
                         List<OrderItemsEvent> itens) {
}
