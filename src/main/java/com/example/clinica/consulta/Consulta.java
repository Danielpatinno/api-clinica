package com.example.clinica.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "consultas")
@Entity(name = "consultas")
@Getter
@Setter
public class Consulta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer paciente_id;
    private Long agenda_id;

    public void setAgendaId(Long agenda_id) {
        this.agenda_id = agenda_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPacienteId(Integer paciente_id) {
        this.paciente_id = paciente_id;
    }

    public Long getAgendaId() {
        return this.agenda_id;
    }

    public Integer getPacienteId() {
        return this.paciente_id;
    }
}
