package com.example.clinica.controller;

import com.example.clinica.paciente.Paciente;
import com.example.clinica.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public List<Paciente> getAll() {
        List<Paciente> pacientes = repository.findAll();
        return pacientes;
    }

    @PostMapping
    public Paciente create(@RequestBody Paciente paciente) {
        return repository.save(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Paciente excluido.");

        return ResponseEntity.ok(successResponse);
    }



}