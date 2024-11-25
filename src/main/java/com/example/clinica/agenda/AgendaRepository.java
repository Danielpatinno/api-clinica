package com.example.clinica.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsByMedicoIdAndDiaSemanaAndHorarioInicio(Integer medicoId, String diaSemana, LocalTime horarioInicio);
}
