package com.victormorais.personalplanner.controller;

import com.victormorais.personalplanner.domain.entities.Evento;
import com.victormorais.personalplanner.domain.model.EventoAttDTO;
import com.victormorais.personalplanner.domain.model.EventoDTO;
import com.victormorais.personalplanner.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody @Valid EventoDTO eventoDTO,
                                              @RequestHeader("UsuarioLogado") UUID idUsuarioLogado) {
        Evento eventoCriado = eventoService.criarEvento(eventoDTO, idUsuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable UUID id, @RequestBody EventoAttDTO eventoDTO) {
        Evento eventoAtualizado = eventoService.atualizarEvento(id, eventoDTO);
        if (eventoAtualizado != null) {
            return ResponseEntity.ok(eventoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Evento>> obterEventos( @RequestHeader("UsuarioLogado") UUID idUsuarioLogado) {
        List<Evento> eventos = eventoService.obterEventos(idUsuarioLogado);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> obterEventosFiltro(@RequestBody EventoAttDTO eventoDTO,
                                                      @RequestHeader("UsuarioLogado") UUID idUsuarioLogado) {
        List<Evento> eventos = eventoService.obterEventosFiltro(eventoDTO, idUsuarioLogado);
        return ResponseEntity.ok(eventos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarEvento(@PathVariable UUID id) {
        String msg = eventoService.deletarEvento(id);
        return ResponseEntity.ok(msg);
    }

}