package com.gsmarin.orderms.listener;

import com.gsmarin.orderms.Entity.OrderEvent;
import com.gsmarin.orderms.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.gsmarin.orderms.config.RabbitMqConfig.ORDER_QUEUE;

@Component
@AllArgsConstructor
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);
    private OrderService orderService;

    @RabbitListener(queues = ORDER_QUEUE)
    public void listen(Message<OrderEvent> message){
        logger.info("Message consumed {}",message.getPayload());
        try {
            orderService.saveOrder(message.getPayload());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //
    }
}
