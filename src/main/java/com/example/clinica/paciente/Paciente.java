package com.example.clinica.paciente;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private LocalDate data_nascimento;

}
