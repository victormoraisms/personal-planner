package com.victormorais.personalplanner.domain.repository;

import com.victormorais.personalplanner.domain.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {
}
