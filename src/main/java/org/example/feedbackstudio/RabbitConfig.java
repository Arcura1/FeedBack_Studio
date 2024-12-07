package org.example.feedbackstudio;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.messaging.converter.MessageConverter;


@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello-queue");
    }
    public static final String NOTE_NAME = "note-queue";

    // Queue'yu tanımla
    @Bean
    public Queue queuea() {
        return new Queue(NOTE_NAME, false);
    }

    // Jackson2JsonMessageConverter ile JSON dönüşümü ayarla
    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jacksonJmsMessageConverter() {
        return (org.springframework.amqp.support.converter.MessageConverter) new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate'i konfigüre et
    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return rabbitTemplate;
    }
}
