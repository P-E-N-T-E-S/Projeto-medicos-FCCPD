package Projeto_medicos.ConsumidorJava.src.main.java.com.fccpd.exemploaulaCons;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExemploaulaConsApplication {

    static final String directExchangeName = "direct-exchange";
    static final String queueName = "atividade01";
	static final String routingKey = "rota-um";
   
    @Bean
    Queue queue() {
      return new Queue(queueName, false, false, true);
    }
    @Bean
	DirectExchange exchange() {
      return new DirectExchange(directExchangeName,false, true);
    }

    @Bean
    Binding binding() {
      return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }
    

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}


	public static void main(String[] args) {
		SpringApplication.run(ExemploaulaConsApplication.class, args);
	}

}
