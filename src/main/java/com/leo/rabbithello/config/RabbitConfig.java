package com.leo.rabbithello.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

//    private static final String topicExchangeName = "spring-boot-exchange";
//    private static final String queueName = "spring-boot";

    public static final String CLIENT_QUEUE = "client-queue";
    public static final String ORDER_QUEUE = "order-queue";
    public static final String QUEUE_DEAD_ORDERS = "order-dead-queue";
    public static final String BUSINESS_EXCHANGE = "business-exchange";

    //Announcing three queue
    @Bean
    public Queue clientQueue() {
        return QueueBuilder.durable(CLIENT_QUEUE).build();
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDERS)
                .withArgument("x-message-ttl", 20*1000)    //move to QUEUE_DEAD_ORDERS. if message is not used in last 20 seconds
                .build();
    }

    @Bean
    public Queue orderDeadQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_ORDERS).build();
    }

    //Announcing exchange
    @Bean
    public TopicExchange businessExchange(){
        return ExchangeBuilder.topicExchange(BUSINESS_EXCHANGE).build();
    }

    //Binding the exchange with queue's
    @Bean
    public Binding bindingOrder(Queue orderQueue, TopicExchange businessExchange){
        return BindingBuilder.bind(orderQueue).to(businessExchange).with(ORDER_QUEUE);
    }

    @Bean
    public Binding bindingClient(Queue clientQueue, TopicExchange businessExchange){
        return BindingBuilder.bind(clientQueue).to(businessExchange).with(CLIENT_QUEUE);
    }

    //Defining the serialization process to json
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //Defining the serialization process to json at the listener
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
