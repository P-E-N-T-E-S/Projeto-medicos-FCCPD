package com.consumer.consumers;

import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClinicoGeralCons {

    private final Map<LocalDateTime, SolicitacaoConsulta> clinicoConsultas = new ConcurrentHashMap<>();

    @RabbitListener(queues = "#{clinicoGeralQueue.name}")
    public void receberSolicitacao(String mensagem) throws Exception {
        System.out.println("CLÍNICO GERAL: Recebida solicitação - " + mensagem);
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        agendarConsulta(consulta);
    }

    private void agendarConsulta(SolicitacaoConsulta consulta) {
        if (clinicoConsultas.containsKey(consulta.getDataConsulta())) {
            System.out.println("CLÍNICO GERAL: Horário já ocupado: " + consulta.getDataConsulta());
        }
        else {
            clinicoConsultas.put(consulta.getDataConsulta(), consulta);
            System.out.println("CLÍNICO GERAL: Consulta confirmada para + " + consulta.getDataConsulta());
        }
    }
}