package com.consumer.utils;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SolicitacaoConsulta {
    private LocalDateTime dataSolicitacao;
    private String paciente;
    private String especialidade;
    private LocalDateTime dataConsulta;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public String getPaciente() { return paciente; }
    public String getEspecialidade() { return especialidade; }
    public LocalDateTime getDataConsulta() { return dataConsulta; }

    public static SolicitacaoConsulta fromJson(String jsonString) throws Exception {
        org.json.JSONObject json = new org.json.JSONObject(jsonString);
        SolicitacaoConsulta consulta = new SolicitacaoConsulta();

        consulta.dataSolicitacao = LocalDateTime.parse(json.getString("dataSolicitacao"), formatter);
        consulta.paciente = json.getString("nome");
        consulta.especialidade = json.getString("especialidade");
        consulta.dataConsulta = LocalDateTime.parse(json.getString("dataConsulta"), formatter);

        return consulta;
    }
}