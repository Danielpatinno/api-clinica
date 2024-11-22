package com.example.clinica.controller;

import com.example.clinica.consulta.Consulta;
import com.example.clinica.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;

    @GetMapping
    public List<Consulta> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Consulta create(@RequestBody Consulta consulta) {
        return repository.save(consulta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(agenda -> ResponseEntity.ok().body(agenda))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}