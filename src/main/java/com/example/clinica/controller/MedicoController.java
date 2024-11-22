package com.example.clinica.controller;

import com.example.clinica.consulta.Consulta;
import com.example.clinica.medico.Medico;
import com.example.clinica.medico.MedicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicosRepository repository;

    @GetMapping
    public List<Medico> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Medico create(@RequestBody Medico medico) {
        // Salva o novo médico no banco de dados
        return repository.save(medico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(agenda -> ResponseEntity.ok().body(agenda))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
