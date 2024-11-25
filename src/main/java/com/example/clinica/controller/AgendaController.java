package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.agenda.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("agendas")
public class AgendaController {

    @Autowired
    private AgendaRepository repository;

    @GetMapping
    public List<Agenda> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Agenda agenda) {
        boolean horarioExistente = repository.existsByMedicoIdAndDiaSemanaAndHorarioInicio(
                agenda.getMedicoId(), agenda.getDiaDaSemana(), agenda.getHorarioInicio()
        );

        if (horarioExistente) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Já cadastrado.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Agenda savedAgenda = repository.save(agenda);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Agenda cadastrada com sucesso!");
        successResponse.put("agendaId", savedAgenda.getId().toString());

        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Agenda cancelada.");

        return ResponseEntity.ok(successResponse);
    }

}