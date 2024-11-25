package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.agenda.AgendaRepository;
import com.example.clinica.consulta.Consulta;
import com.example.clinica.consulta.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaRepository consultaRepository;

    @MockBean
    private AgendaRepository agendaRepository;

    private Consulta consulta;
    private Agenda agenda;

    @BeforeEach
    void setUp() {
        consulta = new Consulta();
        consulta.setAgendaId(1L);

        agenda = new Agenda();
        agenda.setId(1L);
        agenda.setOcupado(false);
    }

    @Test
    void shouldGetAllConsultas() throws Exception {
        mockMvc.perform(get("/consultas"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateConsulta() throws Exception {
        Agenda agenda = new Agenda();
        agenda.setOcupado(false);

        Consulta consulta = new Consulta();
        consulta.setPacienteId(124);
        consulta.setAgendaId(1L);

        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agenda);

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "paciente_id": 124,
                            "agenda_id": 1
                        }
                    """))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Consulta criada e horário marcado como ocupado.")))
                .andDo(print());


        verify(agendaRepository).save(agenda);

        assertTrue(agenda.getOcupado(), "A agenda deveria estar marcada como ocupada");
    }


    @Test
    void shouldReturnErrorWhenAgendaNotFound() throws Exception {
        when(agendaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "agenda_id": 1
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Agenda não encontrada.")));
    }

    @Test
    void shouldDeleteConsulta() throws Exception {
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));

        mockMvc.perform(delete("/consultas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consulta cancelada e horário liberado."));
    }
}
