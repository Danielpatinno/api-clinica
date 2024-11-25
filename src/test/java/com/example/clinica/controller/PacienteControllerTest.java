package com.example.clinica.controller;

import com.example.clinica.paciente.Paciente;
import com.example.clinica.paciente.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Paciente paciente;

    @BeforeEach
    void setup() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João Silva");
        paciente.setCpf("123.456.789-00");
        paciente.setTelefone("99999-9999");
        paciente.setEmail("joao@example.com");
        paciente.setEndereco("Rua A, 123");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldReturnAllPacientes() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João Silva");
        paciente.setCpf("123.456.789-00");
        paciente.setTelefone("99999-9999");
        paciente.setEmail("joao@example.com");
        paciente.setEndereco("Rua A, 123");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        when(repository.findAll()).thenReturn(Collections.singletonList(paciente));

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[0].cpf").value("123.456.789-00"))
                .andExpect(jsonPath("$[0].telefone").value("99999-9999"))
                .andExpect(jsonPath("$[0].email").value("joao@example.com"))
                .andExpect(jsonPath("$[0].endereco").value("Rua A, 123"))
                .andExpect(jsonPath("$[0].data_nascimento").value("01/01/1990"));
    }

    @Test
    void shouldCreatePaciente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João Silva");
        paciente.setCpf("123.456.789-00");
        paciente.setTelefone("99999-8888");
        paciente.setEmail("joao.silva@email.com");
        paciente.setEndereco("Rua ABC, 123");
        paciente.setDataNascimento(LocalDate.of(1990, 5, 15));

        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"))
                .andExpect(jsonPath("$.telefone").value("99999-8888"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"))
                .andExpect(jsonPath("$.endereco").value("Rua ABC, 123"))
                .andExpect(jsonPath("$.data_nascimento").value("15/05/1990"));
    }



    @Test
    void shouldDeletePaciente() throws Exception {
        doNothing().when(repository).deleteById(1L);

        mockMvc.perform(delete("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Paciente excluido."));

        verify(repository, times(1)).deleteById(1L);
    }
}
