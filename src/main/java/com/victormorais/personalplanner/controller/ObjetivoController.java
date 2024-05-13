package com.victormorais.personalplanner.controller;

import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.model.ObjetivoDTO;
import com.victormorais.personalplanner.services.ObjetivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/objetivos")
public class ObjetivoController {

    @Autowired
    private ObjetivoService objetivoService;

    @PostMapping
    public ResponseEntity<Objetivo> criarObjetivo(@RequestBody ObjetivoDTO objetivoDTO,
                                                  @RequestHeader("UsuarioLogado") String idUsuarioLogado) {

        Objetivo objetivoCriado = objetivoService.criarObjetivo(objetivoDTO, idUsuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(objetivoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizarObjetivo(@PathVariable UUID id, @RequestBody ObjetivoDTO objetivoDTO) {
        Objetivo objetivoAtualizado = objetivoService.atualizarObjetivo(id, objetivoDTO);
        if (objetivoAtualizado != null) {
            return ResponseEntity.ok(objetivoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Objetivo>> obterObjetivos() {
        List<Objetivo> objetivos = objetivoService.obterObjetivos();
        return ResponseEntity.ok(objetivos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarObjetivo(@PathVariable UUID id) {
        objetivoService.deletarObjetivo(id);
        return ResponseEntity.noContent().build();
    }

}
