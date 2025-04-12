package com.wipi.config;

import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.properties.RabbitmqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    private final RabbitmqProperties rabbitMqProperties;

    // === EMAIL 설정 ===
    @Bean
    public DirectExchange exchangeMail() {
        return new DirectExchange(RabbitmqConstants.EXCHANGE_MAIL);
    }

    @Bean
    public Queue queueMailSend() {
        return new Queue(RabbitmqConstants.QUEUE_MAIL_SEND, true);
    }

    @Bean
    public Binding bindingMailSend(Queue queueMailSend, DirectExchange exchangeMail) {
        return BindingBuilder.bind(queueMailSend)
                .to(exchangeMail)
                .with(RabbitmqConstants.ROUTING_MAIL_SEND);
    }

    // === SMS 설정 ===
    @Bean
    public DirectExchange exchangeSmsCool() {
        return new DirectExchange(RabbitmqConstants.EXCHANGE_SMS_COOL);
    }

    @Bean
    public Queue queueSmsCool() {
        return new Queue(RabbitmqConstants.QUEUE_SMS_COOL, true);
    }

    @Bean
    public Binding bindingSmsCool(Queue queueSmsCool, DirectExchange exchangeSmsCool) {
        return BindingBuilder.bind(queueSmsCool)
                .to(exchangeSmsCool)
                .with(RabbitmqConstants.ROUTING_SMS_SEND);
    }

    // === 공통 RabbitMQ 설정 ===
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMqProperties.getHost());
        connectionFactory.setPort(rabbitMqProperties.getPort());
        connectionFactory.setUsername(rabbitMqProperties.getUsername());
        connectionFactory.setPassword(rabbitMqProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
