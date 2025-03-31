package com.consumer;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

	static final String TOPIC_EXCHANGE_NAME = "topic-exchange";

	public static final String QUEUE = "fila-conjunta";
	public static final String CARDIOLOGIA_QUEUE = "fila-cardiologia";
	public static final String PEDIATRIA_QUEUE = "fila-pediatria";
	public static final String CLINICO_GERAL_QUEUE = "fila-clinico-geral";

	public static final String FILA_CONJUNTA_KEY = "agendamento.#";
	public static final String CARDIOLOGIA_KEY = "agendamento.cardiologia";
	public static final String PEDIATRIA_KEY = "agendamento.pediatria";
	public static final String CLINICO_GERAL_KEY = "agendamento.clinico_geral";

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME, false, false);
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE, true);
	}

	@Bean
	Queue cardiologiaQueue() {
		return new Queue(CARDIOLOGIA_QUEUE, true);
	}

	@Bean
	Queue pediatriaQueue() {
		return new Queue(PEDIATRIA_QUEUE, true);
	}

	@Bean
	Queue clinicoGeralQueue() {
		return new Queue(CLINICO_GERAL_QUEUE, true);
	}

	@Bean
	Binding queueBinding() {
		return BindingBuilder.bind(queue())
				.to(exchange())
				.with(FILA_CONJUNTA_KEY);
	}

	@Bean
	Binding cardiologiaBinding() {
		return BindingBuilder.bind(cardiologiaQueue())
				.to(exchange())
				.with(CARDIOLOGIA_KEY);
	}

	@Bean
	Binding pediatriaBinding() {
		return BindingBuilder.bind(pediatriaQueue())
				.to(exchange())
				.with(PEDIATRIA_KEY);
	}

	@Bean
	Binding clinicoGeralBinding() {
		return BindingBuilder.bind(clinicoGeralQueue())
				.to(exchange())
				.with(CLINICO_GERAL_KEY);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}