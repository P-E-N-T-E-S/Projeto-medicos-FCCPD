package com.consumer.consumers;

import com.consumer.utils.ConditionalOnSelectedMode;
import com.consumer.utils.RabbitMQConfig;
import com.consumer.utils.SolicitacaoConsulta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnSelectedMode("4")
public class AuditoriaCons {

    @RabbitListener(queues = "#{queue.name}")
    public void auditarTodasMensagens(String mensagem) throws Exception {
        SolicitacaoConsulta consulta = SolicitacaoConsulta.fromJson(mensagem);
        System.out.println("Auditoria: [DATA DA SOLICITAÇÃO:" + consulta.getDataSolicitacao() +
                "] [NOME DO PACIENTE: " +consulta.getPaciente() + "] [ESPECIALIDADE: " + consulta.getEspecialidade()
                + "] [DATA DA CONSULTA: " + consulta.getDataConsulta() + "]");
    }
}