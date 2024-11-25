package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.agenda.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AgendaControllerTest {

    @Mock
    private AgendaRepository repository;

    @InjectMocks
    private AgendaController controller;

    private Agenda agenda;

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        agenda.setId(1L);
        agenda.setMedicoId(1);
        agenda.setDiaDaSemana("Segunda-feira");
        agenda.setHorarioInicio(LocalTime.of(9, 0));
    }

    @Test
    public void testCreateAgendaSuccess() {
        agenda.setDiaDaSemana("Segunda-feira");

        when(repository.existsByMedicoIdAndDiaSemanaAndHorarioInicio(agenda.getMedicoId(), agenda.getDiaDaSemana(), agenda.getHorarioInicio()))
                .thenReturn(false);
        when(repository.save(agenda)).thenReturn(agenda);

        ResponseEntity<Object> response = controller.create(agenda);

        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Agenda cadastrada com sucesso!"));
    }

    @Test
    public void testCreateAgendaConflict() {
        // Mocking the behavior of the repository
        when(repository.existsByMedicoIdAndDiaSemanaAndHorarioInicio(agenda.getMedicoId(), agenda.getDiaDaSemana(), agenda.getHorarioInicio()))
                .thenReturn(true);

        ResponseEntity<Object> response = controller.create(agenda);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Já cadastrado."));
    }

    @Test
    public void testGetAllAgendas() {
        // Mocking the behavior of the repository
        when(repository.findAll()).thenReturn(List.of(agenda));

        List<Agenda> agendas = controller.getAll();

        assertNotNull(agendas);
        assertEquals(1, agendas.size());
        assertEquals(agenda, agendas.get(0));
    }

    @Test
    public void testDeleteAgenda() {
        // Mocking the behavior of the repository
        doNothing().when(repository).deleteById(agenda.getId());

        ResponseEntity<Object> response = controller.delete(agenda.getId());

        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Agenda cancelada."));
    }
}
