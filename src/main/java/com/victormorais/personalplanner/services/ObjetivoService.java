package com.victormorais.personalplanner.services;

import com.victormorais.personalplanner.domain.entities.Objetivo;
import com.victormorais.personalplanner.domain.entities.Status;
import com.victormorais.personalplanner.domain.entities.Usuario;
import com.victormorais.personalplanner.domain.model.ObjetivoDTO;
import com.victormorais.personalplanner.domain.repository.ObjetivoRepository;
import com.victormorais.personalplanner.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjetivoService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Objetivo criarObjetivo(ObjetivoDTO objetivoDTO, String idUsuarioLogado){

        Optional<Usuario> usuario = usuarioRepository.findById(UUID.fromString(idUsuarioLogado));

        Objetivo objetivo = new Objetivo();
        objetivo.setUsuario(usuario.get());
        objetivo.setNome(objetivoDTO.getNome());
        if(objetivoDTO.getDescricao() != null) objetivo.setDescricao(objetivoDTO.getDescricao());
        if(objetivo.getDataLimite() != null) objetivo.setDataLimite(objetivoDTO.getDataLimite());

        Integer prioridade = objetivoDTO.getPrioridade() != null ? objetivoDTO.getPrioridade() : 1;
        objetivo.setPrioridade(prioridade);
        objetivo.setStatus(Status.ASF);

        return objetivoRepository.save(objetivo);

    }

    public Objetivo atualizarObjetivo(UUID idObjetivo, ObjetivoDTO objetivoDTO) {

        Objetivo objetivoExistente = objetivoRepository.findById(idObjetivo).orElse(null);
        if (objetivoExistente == null) {
            return null;
        }

        if (objetivoDTO.getNome() != null) objetivoExistente.setNome(objetivoDTO.getNome());
        if (objetivoDTO.getDescricao() != null) objetivoExistente.setDescricao(objetivoDTO.getDescricao());
        if (objetivoDTO.getDataLimite() != null) objetivoExistente.setDataLimite(objetivoDTO.getDataLimite());
        if (objetivoDTO.getPrioridade() != null) objetivoExistente.setPrioridade(objetivoDTO.getPrioridade());
        if (objetivoDTO.getStatus() != null) objetivoExistente.setStatus(objetivoDTO.getStatus());

        return objetivoRepository.save(objetivoExistente);
    }

    public List<Objetivo> obterObjetivos() { return objetivoRepository.findAll(); }

    public String deletarObjetivo(UUID idObjetivo) {
        Optional<Objetivo> objetivo = objetivoRepository.findById(idObjetivo);
        if (objetivo.isPresent() ) {
            objetivoRepository.delete(objetivo.get());
            return "Objetivo deletado com sucesso.";
        }else{
            return "Objetivo não encontrado!";
        }
    }

}