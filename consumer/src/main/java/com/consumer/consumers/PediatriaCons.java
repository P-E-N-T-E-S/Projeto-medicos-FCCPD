package com.consumer.consumers;

import com.consumer.utils.ConditionalOnSelectedMode;
import com.consumer.utils.RabbitMQConfig;
import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnSelectedMode("3")
public class PediatriaCons {

    private final Map<LocalDateTime, SolicitacaoConsulta> pediatriaConsultas = new ConcurrentHashMap<>();

    @RabbitListener(queues = "#{queue.name}")
    public void receberSolicitacao(String mensagem) throws Exception {
        System.out.println("PEDIATRIA: Recebida solicitação - " + mensagem);
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        agendarConsulta(consulta);
    }

    private void agendarConsulta(SolicitacaoConsulta consulta) {
        if (pediatriaConsultas.containsKey(consulta.getDataConsulta())) {
            System.out.println("PEDIATRIA: Horário já ocupado: " + consulta.getDataConsulta());
        }
        else {
            pediatriaConsultas.put(consulta.getDataConsulta(), consulta);
            System.out.println("PEDIATRIA: Consulta confirmada para + " + consulta.getDataConsulta());
        }
    }
}