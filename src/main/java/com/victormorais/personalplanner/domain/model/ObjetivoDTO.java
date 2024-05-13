package com.victormorais.personalplanner.domain.model;

import com.victormorais.personalplanner.domain.entities.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ObjetivoDTO {

    @NotNull
    private String nome;
    private String descricao;
    private LocalDateTime dataLimite;
    private Integer prioridade;
    private Status status;

}
