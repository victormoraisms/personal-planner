package com.victormorais.personalplanner.domain.repository;

import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObjetivoRepository extends JpaRepository<Objetivo, UUID> {

    List<Objetivo> findAllByUsuario(Usuario usuario);

}
