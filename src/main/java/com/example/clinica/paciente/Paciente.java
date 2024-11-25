package com.example.clinica.paciente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
@Setter
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String nome;
    @Setter
    private String cpf;
    @Setter
    private String telefone;
    @Setter
    private String email;
    @Setter
    private String endereco;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_nascimento;

    public void setId(long id) {
        this.id = id;
    }

    public void setDataNascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
