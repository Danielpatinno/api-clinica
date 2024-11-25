package com.example.clinica.medico;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "medicos")
@Entity(name = "medicos")
@Getter
@Setter
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String nome;
    @Setter
    private String conselho;
    @Setter
    private String estado;

    public void setId(long id) {
        this.id = id;
    }

}
