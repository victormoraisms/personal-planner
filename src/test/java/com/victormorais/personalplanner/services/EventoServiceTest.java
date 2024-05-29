package com.victormorais.personalplanner.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.victormorais.personalplanner.domain.entities.Evento;
import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.entities.Usuario;
import com.victormorais.personalplanner.domain.model.EventoAttDTO;
import com.victormorais.personalplanner.domain.model.EventoDTO;
import com.victormorais.personalplanner.domain.repository.EventoRepository;
import com.victormorais.personalplanner.domain.repository.ObjetivoRepository;
import com.victormorais.personalplanner.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.InvalidPropertyException;

public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private ObjetivoRepository objetivoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EventoService eventoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCriarEvento_Success() {
        UUID idUsuarioLogado = UUID.randomUUID();
        UUID idObjetivo = UUID.randomUUID();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setIdObjetivo(idObjetivo);
        eventoDTO.setNome("Evento Teste");
        eventoDTO.setTipo("MEETING");
        eventoDTO.setDataEvento(LocalDateTime.now());
        eventoDTO.setHrInicio(LocalDateTime.now().plusHours(1));
        eventoDTO.setHrFim(LocalDateTime.now().plusHours(2));

        Objetivo objetivo = new Objetivo();
        objetivo.setIdObjetivo(idObjetivo);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuarioLogado);

        when(objetivoRepository.findById(idObjetivo)).thenReturn(Optional.of(objetivo));
        when(usuarioRepository.findById(idUsuarioLogado)).thenReturn(Optional.of(usuario));
        when(eventoRepository.save(any(Evento.class))).thenReturn(new Evento());

        Evento evento = eventoService.criarEvento(eventoDTO, idUsuarioLogado);

        assertNotNull(evento);
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    public void testCriarEvento_ObjetivoNotFound() {
        UUID idUsuarioLogado = UUID.randomUUID();
        UUID idObjetivo = UUID.randomUUID();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setIdObjetivo(idObjetivo);

        when(objetivoRepository.findById(idObjetivo)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> {
            eventoService.criarEvento(eventoDTO, idUsuarioLogado);
        });
    }

    @Test
    public void testCriarEvento_UsuarioNotFound() {
        UUID idUsuarioLogado = UUID.randomUUID();
        UUID idObjetivo = UUID.randomUUID();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setIdObjetivo(idObjetivo);

        Objetivo objetivo = new Objetivo();
        objetivo.setIdObjetivo(idObjetivo);

        when(objetivoRepository.findById(idObjetivo)).thenReturn(Optional.of(objetivo));
        when(usuarioRepository.findById(idUsuarioLogado)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> {
            eventoService.criarEvento(eventoDTO, idUsuarioLogado);
        });
    }

    @Test
    public void testAtualizarEvento_Success() {
        UUID idEvento = UUID.randomUUID();
        EventoAttDTO eventoDTO = new EventoAttDTO();
        eventoDTO.setNome("Evento Atualizado");
        eventoDTO.setStatus("EP");

        Evento eventoExistente = new Evento();
        eventoExistente.setIdEvento(idEvento);

        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoExistente));
        when(eventoRepository.save(any(Evento.class))).thenReturn(new Evento());

        Evento eventoAtualizado = eventoService.atualizarEvento(idEvento, eventoDTO);

        assertNotNull(eventoAtualizado);
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    public void testAtualizarEvento_NotFound() {
        UUID idEvento = UUID.randomUUID();
        EventoAttDTO eventoDTO = new EventoAttDTO();

        when(eventoRepository.findById(idEvento)).thenReturn(Optional.empty());

        Evento eventoAtualizado = eventoService.atualizarEvento(idEvento, eventoDTO);

        assertNull(eventoAtualizado);
        verify(eventoRepository, never()).save(any(Evento.class));
    }
}
