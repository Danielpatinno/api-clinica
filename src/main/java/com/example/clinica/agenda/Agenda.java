package com.example.clinica.agenda;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Table(name = "agendas")
@Entity(name = "agendas")
@Getter
@Setter
public class Agenda {

    //Setters para teste
    @Getter
    @Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "medico_id")
    private Integer medicoId;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Setter
    @Getter
    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;
    @Getter
    @Setter
    private Boolean ocupado = false;

    public String getDiaDaSemana() {
        return this.diaSemana;
    }

    public void setDiaDaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}
