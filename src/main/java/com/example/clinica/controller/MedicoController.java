package com.example.clinica.controller;

import com.example.clinica.agenda.Agenda;
import com.example.clinica.consulta.Consulta;
import com.example.clinica.medico.Medico;
import com.example.clinica.medico.MedicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicosRepository repository;

    @PostMapping
    public ResponseEntity<Object> createMedico(@RequestBody Medico medico) {
        Medico savedMedico = repository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Medico Cadastrado!"));
    }

    @GetMapping
    public List<Medico> getAll() {
        return repository.findAll();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Medico excluido");

        return ResponseEntity.ok(successResponse);
    }

}
