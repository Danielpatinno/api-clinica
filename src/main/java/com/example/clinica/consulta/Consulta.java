package com.example.clinica.consulta;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "consultas")
@Entity(name = "consultas")
@Getter
public class Consulta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer paciente_id;
    private Integer agenda_id;

}
