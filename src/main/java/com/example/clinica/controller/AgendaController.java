package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.agenda.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Agenda create(@RequestBody Agenda agenda) {
        return repository.save(agenda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agenda> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(agenda -> ResponseEntity.ok().body(agenda))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}