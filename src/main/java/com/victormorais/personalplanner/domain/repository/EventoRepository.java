package com.victormorais.personalplanner.domain.repository;

import com.victormorais.personalplanner.domain.entities.Evento;
import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {

    List<Evento> findAllByUsuario(Usuario usuario);

    @Query("SELECT e FROM Evento e WHERE e.dataEvento = :y AND e.hrInicio <= :x AND e.hrFim > :x")
    List<Evento> encontrarEventosPorDataEHora(LocalDateTime x, LocalDateTime y);

}
