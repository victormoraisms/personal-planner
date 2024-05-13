package com.victormorais.personalplanner.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventoDTO {

    private String nome;
    private String tipo;
    private LocalDateTime dataEvento;
    private LocalDateTime hrInicio;
    private LocalDateTime hrFim;
    private Integer tempoAviso;
    private String descricao;
    private Integer prioridade;
    private String notas;
    private String status;

}
