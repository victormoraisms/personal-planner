package com.victormorais.personalplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotNull
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$")
    private String username;
    @NotNull
    private String senha;
    private String dicaSenha;
    @JsonProperty
    private boolean isAdmin;

}
