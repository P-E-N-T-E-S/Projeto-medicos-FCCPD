package com.consumer.utils;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE_NAME = "agendamento_exchange";
    public static final String ROUTING_KEY = "agendamento.#";
    public static final String CLINICO_GERAL_KEY = "agendamento.clinico_geral";
    public static final String CARDIOLOGIA_KEY = "agendamento.cardiologia";
    public static final String PEDIATRIA_KEY = "agendamento.pediatria";

    private static String filaSelecionada;

    public static void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========= SISTEMA DE AGENDAMENTO MÉDICO (Assistente) =========");
        System.out.println("Selecione o médico que você atende:");
        System.out.println("1. Clínico Geral");
        System.out.println("2. Cardiologia");
        System.out.println("3. Pediatria");
        System.out.println("4. Auditoria");
        System.out.print("Digite o número da opção (1-4): ");

        filaSelecionada = scanner.nextLine();
        while (!filaSelecionada.matches("[1-4]")) {
            System.out.print("Opção inválida! Digite novamente (1-4): ");
            filaSelecionada = scanner.nextLine();
        }
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(TopicExchange topicExchange, Queue queue) {
        String routingKey = switch (filaSelecionada) {
            case "1" -> CLINICO_GERAL_KEY;
            case "2" -> CARDIOLOGIA_KEY;
            case "3" -> PEDIATRIA_KEY;
            case "4" -> ROUTING_KEY;
            default -> throw new IllegalStateException("Opção inválida!");
        };

        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(routingKey);
    }

    public static String getFilaSelecionada() {
        return filaSelecionada;
    }
}