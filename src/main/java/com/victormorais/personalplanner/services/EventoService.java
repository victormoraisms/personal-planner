package com.victormorais.personalplanner.services;

import com.victormorais.personalplanner.domain.entities.Evento;
import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.entities.Status;
import com.victormorais.personalplanner.domain.entities.Usuario;
import com.victormorais.personalplanner.domain.model.EventoAttDTO;
import com.victormorais.personalplanner.domain.model.EventoDTO;
import com.victormorais.personalplanner.domain.model.ObjetivoDTO;
import com.victormorais.personalplanner.domain.repository.EventoRepository;
import com.victormorais.personalplanner.domain.repository.ObjetivoRepository;
import com.victormorais.personalplanner.domain.repository.UsuarioRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento criarEvento(EventoDTO eventoDTO, UUID idUsuarioLogado) {

        Optional<Objetivo> objetivo = objetivoRepository.findById(eventoDTO.getIdObjetivo());

        Evento evento = new Evento();

        if (objetivo.isPresent() ) {
            evento.setObjetivo(objetivo.get());
        }else{
            throw new InvalidPropertyException(EventoService.class, "objetivo", "Objetivo não encontrado");
        }

        Optional<Usuario> usuario = usuarioRepository.findById(idUsuarioLogado);

        if (usuario.isPresent() ) {
            evento.setUsuario(usuario.get());
        }else{
            throw new InvalidPropertyException(EventoService.class, "usuario", "Usuário não encontrado");
        }

        evento.setNome(eventoDTO.getNome());
        evento.setTipo(eventoDTO.getTipo());
        evento.setDataEvento(eventoDTO.getDataEvento());
        if(eventoDTO.getHrInicio() != null && eventoDTO.getHrFim() != null){
            List<Evento> eventosPorHora = eventoRepository.encontrarEventosPorDataEHora(eventoDTO.getHrInicio(), eventoDTO.getDataEvento());

            if(!eventosPorHora.isEmpty()){
                LocalDateTime hrFimEvento = eventoDTO.getHrFim();
                for(Evento eventoPorHora: eventosPorHora){
                    if(eventoPorHora.getHrFim().isAfter(hrFimEvento)){
                        hrFimEvento = eventoPorHora.getHrFim();
                    }
                }
                evento.setHrInicio(hrFimEvento.withSecond(0).withNano(0));
                Duration duration = Duration.between(eventoDTO.getHrInicio(), eventoDTO.getHrFim());
                evento.setHrFim(hrFimEvento.plus(duration));
            }else{
                evento.setHrInicio(eventoDTO.getHrInicio().withSecond(0).withNano(0));
                evento.setHrFim(eventoDTO.getHrFim().withSecond(0).withNano(0));
            }
        }
        if(eventoDTO.getTempoAviso() != null) evento.setTempoAviso(eventoDTO.getTempoAviso());
        if(eventoDTO.getDescricao() != null) evento.setDescricao(eventoDTO.getDescricao());
        if(eventoDTO.getPrioridade() != null) evento.setPrioridade(eventoDTO.getPrioridade());
        if(eventoDTO.getNotas() != null) evento.setNotas(eventoDTO.getNotas());
        evento.setStatus(Status.ASF);

        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(UUID idEvento, EventoAttDTO eventoDTO) {
        Evento eventoExistente = eventoRepository.findById(idEvento).orElse(null);
        if (eventoExistente == null) {
            return null;
        }

        if(eventoDTO.getNome() != null) eventoExistente.setNome(eventoDTO.getNome());
        if(eventoDTO.getTipo() != null) eventoExistente.setTipo(eventoDTO.getTipo());
        if(eventoDTO.getDataEvento() != null) eventoExistente.setDataEvento(eventoDTO.getDataEvento());
        if(eventoDTO.getHrInicio() != null && eventoDTO.getHrFim() != null){
            List<Evento> eventosPorHora = eventoRepository.encontrarEventosPorDataEHora(eventoDTO.getHrInicio(), eventoDTO.getDataEvento());

            if(!eventosPorHora.isEmpty()){
                LocalDateTime hrFimEvento = eventoDTO.getHrFim();
                for(Evento eventoPorHora: eventosPorHora){
                    if(eventoPorHora.getHrFim().isAfter(hrFimEvento)){
                        hrFimEvento = eventoPorHora.getHrFim();
                    }
                }
                eventoExistente.setHrInicio(hrFimEvento.withSecond(0).withNano(0));
                Duration duration = Duration.between(eventoDTO.getHrInicio(), eventoDTO.getHrFim());
                eventoExistente.setHrFim(hrFimEvento.plus(duration));
            }else{
                eventoExistente.setHrInicio(eventoDTO.getHrInicio().withSecond(0).withNano(0));
                eventoExistente.setHrFim(eventoDTO.getHrFim().withSecond(0).withNano(0));
            }
        }
        if(eventoDTO.getTempoAviso() != null) eventoExistente.setTempoAviso(eventoDTO.getTempoAviso());
        if(eventoDTO.getDescricao() != null) eventoExistente.setDescricao(eventoDTO.getDescricao());
        if(eventoDTO.getPrioridade() != null) eventoExistente.setPrioridade(eventoDTO.getPrioridade());
        if(eventoDTO.getNotas() != null) eventoExistente.setNotas(eventoDTO.getNotas());
        if(eventoDTO.getStatus() != null) eventoExistente.setStatus(Status.valueOf(eventoDTO.getStatus()));

        return eventoRepository.save(eventoExistente);
    }

    public List<Evento> obterEventos(UUID idUsuarioLogado) {
        List<Evento> eventos;

        Optional<Usuario> usuario = usuarioRepository.findById(idUsuarioLogado);

        if (usuario.isPresent() ) {
            eventos = eventoRepository.findAllByUsuario(usuario.get());
        }else{
            throw new InvalidPropertyException(EventoService.class, "usuario", "Usuário não encontrado");
        }

        return eventos;
    }

    public List<Evento> obterEventosFiltro(EventoAttDTO eventoDTO, UUID idUsuarioLogado){

        Evento evento = new Evento();


        Optional<Usuario> usuario = usuarioRepository.findById(idUsuarioLogado);

        if (usuario.isPresent() ) {
            evento.setUsuario(usuario.get());
        }else{
            throw new InvalidPropertyException(EventoService.class, "usuario", "Usuário não encontrado");
        }

        if(eventoDTO.getIdObjetivo() != null){
            Optional<Objetivo> objetivo = objetivoRepository.findById(eventoDTO.getIdObjetivo());

            if (objetivo.isPresent() ) {
                evento.setObjetivo(objetivo.get());
            }
        }

        if (eventoDTO.getStatus() != null) evento.setStatus(Status.valueOf(eventoDTO.getStatus()));
        evento.setNome(eventoDTO.getNome());
        evento.setTipo(eventoDTO.getTipo());
        evento.setDataEvento(eventoDTO.getDataEvento());
        evento.setHrInicio(eventoDTO.getHrInicio());
        evento.setHrFim(eventoDTO.getHrFim());
        evento.setTempoAviso(eventoDTO.getTempoAviso());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setPrioridade(eventoDTO.getPrioridade());
        evento.setNotas(eventoDTO.getNotas());

        List<Evento> listaEventos = eventoRepository.findAll(Example.of(evento));

        return listaEventos;

    }
    public String deletarEvento(UUID idEvento) {
        Optional<Evento> evento = eventoRepository.findById(idEvento);
        if (evento.isPresent() ) {
            eventoRepository.delete(evento.get());
            return "Evento deletado com sucesso.";
        }else{
            return "Evento não encontrado!";
        }
    }

}
