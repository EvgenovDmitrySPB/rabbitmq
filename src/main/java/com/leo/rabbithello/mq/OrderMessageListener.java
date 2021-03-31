package com.leo.rabbithello.mq;

import com.leo.rabbithello.config.RabbitConfig;
import com.leo.rabbithello.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    private static Logger log = LogManager.getLogger();

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void process(Order order) {
        log.info("Received ...");
        log.info(order);
    }
}
