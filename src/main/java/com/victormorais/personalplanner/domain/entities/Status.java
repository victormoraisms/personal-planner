package com.victormorais.personalplanner.domain.entities;

public enum Status {
    ASF("A ser feito"),
    EP("Em progresso"),
    B("Bloqueado"),
    F("Feito");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}
