package com.victormorais.personalplanner.domain.repository;

import com.victormorais.personalplanner.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByUsername(String username);

}
