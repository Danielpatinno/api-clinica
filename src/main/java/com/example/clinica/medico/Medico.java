package com.example.clinica.medico;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "medicos")
@Entity(name = "medicos")
@Getter
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String conselho;
    private String estado;

}
