package com.consumer;

import com.consumer.utils.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {
	public static void main(String[] args) {
		RabbitMQConfig.exibirMenu();
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
