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
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENTO")
@EntityListeners(AuditingEntityListener.class)
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
    @CreationTimestamp
    private LocalDateTime dhCriacao;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime dhAtualizacao;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("Evento de ").append(usuario.getUsername()).append(": \n");
        sb.append("Tipo:  ").append(tipo).append("\n");
        sb.append("Data Evento: ").append(dataEvento.format(formatter)).append("\n");

        // Validar os valores e adicionar texto ao StringBuilder
        if (hrInicio != null && hrFim != null ) {
            sb.append("Hora de inicio do evento:  ").append(hrInicio.format(formatter)).append("\n");
            sb.append("Hora de fim do evento: ").append(hrFim.format(formatter)).append("\n");
        }

        sb.append("Descricao: ").append(descricao).append("\n");
        sb.append("Notas: ").append(notas).append("\n");

        return sb.toString();
    }
}
