package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.agenda.AgendaRepository;
import com.example.clinica.consulta.Consulta;
import com.example.clinica.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private AgendaRepository repositoryAgenda;

    @GetMapping
    public List<Consulta> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Consulta consulta) {
        if (consulta.getAgendaId() == null) {
            return ResponseEntity.badRequest().body("ID da agenda não pode ser nulo.");
        }

        Agenda agenda = repositoryAgenda.findById(consulta.getAgendaId())
                .orElse(null);

        if (agenda == null) {
            return ResponseEntity.badRequest().body("Agenda não encontrada.");
        }

        if (Boolean.TRUE.equals(agenda.getOcupado())) {
            return ResponseEntity.badRequest().body("Horário já está ocupado.");
        }

        agenda.setOcupado(true);
        repositoryAgenda.save(agenda);

        repository.save(consulta);

        return ResponseEntity.status(HttpStatus.CREATED).body("Consulta criada e horário marcado como ocupado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Consulta consulta = repository.findById(id)
                .orElse(null);

        if (consulta == null) {
            return ResponseEntity.badRequest().body("Consulta não encontrada.");
        }

        Agenda agenda = repositoryAgenda.findById(consulta.getAgendaId())
                .orElse(null);

        if (agenda != null) {
            agenda.setOcupado(false);
            repositoryAgenda.save(agenda);
        }

        repository.deleteById(id);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Consulta cancelada e horário liberado.");

        return ResponseEntity.ok(successResponse);
    }
}
