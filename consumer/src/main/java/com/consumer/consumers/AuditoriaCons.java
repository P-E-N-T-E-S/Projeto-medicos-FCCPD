package com.consumer.consumers;

import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaCons {

    @RabbitListener(queues = "#{geralQueue.name}")
    public void auditarTodasMensagens(String mensagem) throws Exception {
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        System.out.println("Auditoria: [DATA DA SOLICITAÇÃO:" + consulta.getDataSolicitacao() +
                "] [NOME DO PACIENTE: " +consulta.getPaciente() + "] [ESPECIALIDADE: " + consulta.getEspecialidade()
                + "] [DATA DA CONSULTA: " + consulta.getDataConsulta() + "]");
    }
}