package com.victormorais.personalplanner.domain.repository;

import com.victormorais.personalplanner.domain.entities.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ObjetivoRepository extends JpaRepository<Objetivo, UUID> {
}
