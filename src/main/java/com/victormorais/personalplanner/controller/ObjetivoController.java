package com.victormorais.personalplanner.controller;

import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.model.ObjetivoAttDTO;
import com.victormorais.personalplanner.domain.model.ObjetivoDTO;
import com.victormorais.personalplanner.services.ObjetivoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Objetivo> criarObjetivo(@RequestBody @Valid ObjetivoDTO objetivoDTO,
                                                  @RequestHeader("UsuarioLogado") String idUsuarioLogado) {

        Objetivo objetivoCriado = objetivoService.criarObjetivo(objetivoDTO, idUsuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(objetivoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objetivo> atualizarObjetivo(@PathVariable UUID id, @RequestBody ObjetivoAttDTO objetivoDTO) {
        Objetivo objetivoAtualizado = objetivoService.atualizarObjetivo(id, objetivoDTO);
        if (objetivoAtualizado != null) {
            return ResponseEntity.ok(objetivoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Objetivo>> obterObjetivos(@RequestHeader("UsuarioLogado") UUID idUsuarioLogado) {
        List<Objetivo> objetivos = objetivoService.obterObjetivos(idUsuarioLogado);
        return ResponseEntity.ok(objetivos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarObjetivo(@PathVariable UUID id) {
        String msg = objetivoService.deletarObjetivo(id);
        return ResponseEntity.ok(msg);
    }

}
