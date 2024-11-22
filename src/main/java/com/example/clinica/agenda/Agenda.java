package com.example.clinica.agenda;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Table(name = "agendas")
@Entity(name = "agendas")
@Getter
public class Agenda {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer  medico_id;
    private String dia_semana;
    private LocalTime horario_inicio;

}
