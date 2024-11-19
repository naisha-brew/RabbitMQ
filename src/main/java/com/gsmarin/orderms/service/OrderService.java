package com.gsmarin.orderms.service;

import com.gsmarin.orderms.Entity.OrderEntity;
import com.gsmarin.orderms.Entity.OrderEvent;
import com.gsmarin.orderms.Entity.OrderItems;
import com.gsmarin.orderms.Entity.OrderResponse;
import com.gsmarin.orderms.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public void saveOrder(OrderEvent order) {
        var entity = new OrderEntity();
        entity.setOrderId(order.codigoPedido());
        entity.setCustomerId(order.codigoCliente());
        entity.setItemsList(getOrderItems(order));
        entity.setTotalPrice(getTotalPrice(order));

        orderRepository.save(entity);

    }

    private BigDecimal getTotalPrice(OrderEvent order) {
        return order.itens().stream().map(
                i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade()))
        ).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private static List<OrderItems> getOrderItems(OrderEvent order) {
        return order.itens().stream()
                .map(i -> new OrderItems(
                        i.produto(),
                        i.quantidade(),
                        i.preco()
                )).toList();
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId,
                                                   PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalPriceByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                Aggregation.match(Criteria.where("customerId").is(customerId)),
                group().sum("totalPrice").as("total")
        );
        var response = mongoTemplate.aggregate(aggregations, "tb_orders",
                Document.class);
        return new BigDecimal(Objects.requireNonNull(response.getUniqueMappedResult()).get("total").toString());
    }
}
