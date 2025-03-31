package com.consumer.consumers;

import com.consumer.ConsumerApplication;
import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Auditoria {

    @RabbitListener(queues = ConsumerApplication.QUEUE)
    public void lerMensagens(String mensagem) throws Exception {
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        System.out.println("Auditoria: [DATA DA SOLICITAÇÃO:" + consulta.getDataSolicitacao() +
                "] [NOME DO PACIENTE: " +consulta.getPaciente() + "] [ESPECIALIDADE: " + consulta.getEspecialidade()
                + "] [DATA DA CONSULTA: " + consulta.getDataConsulta() + "]");
    }
}
