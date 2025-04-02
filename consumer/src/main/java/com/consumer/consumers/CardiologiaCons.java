package com.consumer.consumers;

import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CardiologiaCons {

    private final Map<LocalDateTime, SolicitacaoConsulta> cardiologiaConsultas = new ConcurrentHashMap<>();

    @RabbitListener(queues = "#{cardiologiaQueue.name}")
    public void receberSolicitacao(String mensagem) throws Exception {
        System.out.println("CARDIOLOGIA: Recebida solicitação - " + mensagem);
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        agendarConsulta(consulta);
    }

    private void agendarConsulta(SolicitacaoConsulta consulta) {
        if (cardiologiaConsultas.containsKey(consulta.getDataConsulta())) {
            System.out.println("CARDIOLOGIA: Horário já ocupado: " + consulta.getDataConsulta());
        }
        else {
            cardiologiaConsultas.put(consulta.getDataConsulta(), consulta);
            System.out.println("CARDIOLOGIA: Consulta confirmada para + " + consulta.getDataConsulta());
        }
    }
}