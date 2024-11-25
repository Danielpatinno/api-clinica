package com.example.clinica.controller;

import com.example.clinica.medico.Medico;
import com.example.clinica.medico.MedicosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicosRepository medicosRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Medico medico;

    @BeforeEach
    void setUp() {
        medico = new Medico();
        medico.setNome("Dr. Daniel");
        medico.setConselho("CRM");
        medico.setEstado("SP");
    }

    @Test
    public void testCreateMedicoSuccess() throws Exception {
        when(medicosRepository.save(Mockito.any(Medico.class))).thenReturn(medico);

        mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "nome": "Dr. Daniel",
                                "conselho": "CRM",
                                "estado": "SP"
                            }
                        """))
                .andExpect(status().isCreated()) // Verifica se o status é 201 (Created)
                .andExpect(jsonPath("$.message").value("Medico Cadastrado!"));
    }

    @Test
    public void testGetAllMedicos() throws Exception {
        Medico medico = new Medico();
        medico.setNome("Dr. Daniel");
        medico.setConselho("CRM");
        medico.setEstado("SP");

        when(medicosRepository.findAll()).thenReturn(List.of(medico));

        mockMvc.perform(get("/medicos"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteMedico() throws Exception {
        medico.setId(1L);
        when(medicosRepository.findById(1L)).thenReturn(Optional.of(medico));

        mockMvc.perform(delete("/medicos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Medico excluido"));

        verify(medicosRepository).deleteById(1L);
    }
}
