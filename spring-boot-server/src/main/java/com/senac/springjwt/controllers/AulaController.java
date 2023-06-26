package com.senac.springjwt.controllers;

import com.senac.springjwt.models.Aula;
import com.senac.springjwt.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaRepository repository;

    @PostMapping
    public Aula create(@Valid @RequestBody Aula entity) {
        return repository.save(entity);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Aula> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aula getById(@PathVariable(value = "id") Long id) {

        Aula entity = repository.findById(id)
                .orElse(new Aula());

        return entity;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aula update(@PathVariable(value = "id") Long id,
                        @RequestBody Aula entity) {
        Aula entityFind = repository.findById(id).orElse(null);
        if (entityFind != null) {
            entityFind.setId(entityFind.getId());
            entityFind.setAluno(entity.getAluno());
            entityFind.setCurso(entity.getCurso());
            return repository.save(entityFind);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        Aula entity = repository.findById(id)
                .orElse(null);

        if (entity != null) {
            repository.delete(entity);
        }

        return ResponseEntity.noContent().build();
    }

}
