package com.victormorais.personalplanner.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID idEvento;

    @ManyToOne
    @JoinColumn(name = "idObjetivo")
    private Objetivo objetivo;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime dataEvento;

    @Column
    private LocalDateTime hrInicio;

    @Column
    private LocalDateTime hrFim;

    @Column
    private Integer tempoAviso;

    @Column
    private String descricao;

    @Column
    private Integer prioridade;

    @Column
    private String notas;

    @Column
    private Status status;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime dhCriacao;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime dhAtualizacao;
}
