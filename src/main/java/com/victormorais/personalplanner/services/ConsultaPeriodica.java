package com.victormorais.personalplanner.services;

import com.victormorais.personalplanner.domain.entities.Evento;
import com.victormorais.personalplanner.domain.entities.Status;
import com.victormorais.personalplanner.domain.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ConsultaPeriodica {

    @Autowired
    EventoRepository eventoRepository;

    @Scheduled(fixedRate = 30000) // Executa a cada minuto
    public void consultarEventos() {

        List<Evento> eventos = eventoRepository.findAll();

        if (!eventos.isEmpty()) {

            LocalDate currentDate = LocalDate.now();
            LocalDateTime now = LocalDateTime.now();
            now = now.withSecond(0).withNano(0); // Configura os segundos e nanossegundos para 0

            for(Evento evento : eventos){
                if (evento.getStatus().equals(Status.ASF)){
                    if(evento.getHrInicio() == null && evento.getDataEvento().toLocalDate().isEqual(currentDate)){
                        System.out.println(evento);
                        evento.setStatus(Status.EP);
                        eventoRepository.save(evento);
                    }else if(evento.getHrInicio() != null && evento.getHrInicio().withSecond(0).withNano(0).equals(now)){
                        System.out.println(evento);
                        evento.setStatus(Status.EP);
                        eventoRepository.save(evento);
                    }
                }
            }
        }
    }


}