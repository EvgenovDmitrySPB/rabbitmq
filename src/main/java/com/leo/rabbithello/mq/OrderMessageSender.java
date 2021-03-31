package com.leo.rabbithello.mq;

import com.leo.rabbithello.config.RabbitConfig;
import com.leo.rabbithello.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order){
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, order);
    }
}
