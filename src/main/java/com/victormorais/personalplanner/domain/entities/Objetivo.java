package com.victormorais.personalplanner.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OBJETIVO")
@EntityListeners(AuditingEntityListener.class)
public class Objetivo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID idObjetivo;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column
    private LocalDateTime dataLimite;

    @Column
    private Integer prioridade;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime dhCriacao;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime dhAtualizacao;
}