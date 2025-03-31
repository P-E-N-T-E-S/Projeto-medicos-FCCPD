package com.consumer.consumers;

import com.consumer.ConsumerApplication;
import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Pediatra {

    private final Map<LocalDateTime, SolicitacaoConsulta> consultas = new ConcurrentHashMap<>();

    @RabbitListener(queues = ConsumerApplication.PEDIATRIA_QUEUE)
    public void receberSolicitacao(String mensagem) throws Exception {
        System.out.println("PEDIATRIA: Recebida solicitação - " + mensagem);
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        agendarConsulta(consulta);
    }

    private void agendarConsulta(SolicitacaoConsulta consulta) {
        if (consultas.containsKey(consulta.getDataConsulta())) {
            System.out.println("PEDIATRIA: Horário já ocupado: " + consulta.getDataConsulta());
        }
        else {
            consultas.put(consulta.getDataConsulta(), consulta);
            System.out.println("PEDIATRIA: Consulta confirmada para + " + consulta.getDataConsulta());
        }
    }
}
