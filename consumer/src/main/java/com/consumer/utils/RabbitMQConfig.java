package com.consumer.utils;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE_NAME = "agendamento_exchange";

    public static final String GERAL_KEY = "agendamento.#";
    public static final String CLINICO_GERAL_KEY = "agendamento.clinico_geral";
    public static final String CARDIOLOGIA_KEY = "agendamento.cardiologia";
    public static final String PEDIATRIA_KEY = "agendamento.pediatria";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Queue geralQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue clinicoGeralQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue cardiologiaQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue pediatriaQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding geralBinding(TopicExchange topicExchange, Queue geralQueue) {
        return BindingBuilder.bind(geralQueue)
                .to(topicExchange)
                .with(GERAL_KEY);
    }

    @Bean
    public Binding clinicoGeralBinding(TopicExchange topicExchange, Queue clinicoGeralQueue) {
        return BindingBuilder.bind(clinicoGeralQueue)
                .to(topicExchange)
                .with(CLINICO_GERAL_KEY);
    }

    @Bean
    public Binding cardiologiaBinding(TopicExchange topicExchange, Queue cardiologiaQueue) {
        return BindingBuilder.bind(cardiologiaQueue)
                .to(topicExchange)
                .with(CARDIOLOGIA_KEY);
    }

    @Bean
    public Binding pediatriaQueueBinding(TopicExchange topicExchange, Queue pediatriaQueue) {
        return BindingBuilder.bind(pediatriaQueue)
                .to(topicExchange)
                .with(PEDIATRIA_KEY);
    }
}