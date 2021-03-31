package com.leo.rabbithello.controller;

import com.leo.rabbithello.model.Order;
import com.leo.rabbithello.mq.OrderMessageSender;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RabbitMqController {

    private static Logger log = LogManager.getLogger();

    private OrderMessageSender orderMessageSender;

    @Autowired
    public RabbitMqController(OrderMessageSender orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    @ApiOperation("Send message")
    @GetMapping("sendmessage/{message}")
    @ResponseBody
    public void sendMessaget1(@PathVariable(value = "message", required = true) String message) {
        Order order = new Order(1, message, 1200);
        orderMessageSender.sendOrder(order);
    }

}
