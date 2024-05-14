package com.victormorais.personalplanner.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventoDTO {

    @NotNull
    private String nome;
    @NotNull
    private String tipo;
    @NotNull
    private LocalDateTime dataEvento;
    private LocalDateTime hrInicio;
    private LocalDateTime hrFim;
    private Integer tempoAviso;
    private String descricao;
    private Integer prioridade;
    private String notas;
    private String status;
    private UUID idObjetivo;

}
