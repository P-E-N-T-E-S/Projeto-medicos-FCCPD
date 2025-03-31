package com.fccpd.exemploaulaProd;

import java.util.Scanner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext context;

    public Runner(RabbitTemplate rabbitTemplate,
                  ConfigurableApplicationContext context) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner ler = new Scanner(System.in);

        while(true) {
            System.out.println("Digite a mensagem:");
            String msg;
            msg = ler.nextLine();

            if(msg.contains("sair"))
                break;


            rabbitTemplate.convertAndSend(ExemploaulaProdApplication.directExchangeName, ExemploaulaProdApplication.routingKey, msg);

        }
        context.close();
    }

}