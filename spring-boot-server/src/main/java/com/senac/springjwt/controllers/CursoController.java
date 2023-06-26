package com.senac.springjwt.controllers;

import com.senac.springjwt.models.Curso;
import com.senac.springjwt.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    public Curso create(@Valid @RequestBody Curso entity) {
        return repository.save(entity);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Curso> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Curso getById(@PathVariable(value = "id") Long id) {

        Curso entity = repository.findById(id)
                .orElse(new Curso());

        return entity;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Curso update(@PathVariable(value = "id") Long id,
                        @RequestBody Curso entity) {
        Curso entityFind = repository.findById(id).orElse(null);
        if (entityFind != null) {
            entityFind.setId(entityFind.getId());
            entityFind.setNome(entity.getNome());
            return repository.save(entityFind);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        Curso entity = repository.findById(id)
                .orElse(null);

        if (entity != null) {
            repository.delete(entity);
        }

        return ResponseEntity.noContent().build();
    }

}
