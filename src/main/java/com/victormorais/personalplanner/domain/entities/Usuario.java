package com.victormorais.personalplanner.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column
    private String dicaSenha;
}