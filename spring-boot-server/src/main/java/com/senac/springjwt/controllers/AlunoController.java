package com.senac.springjwt.controllers;

import com.senac.springjwt.models.Aluno;
import com.senac.springjwt.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @PostMapping
    public Aluno create(@Valid @RequestBody Aluno entity) {
        return repository.save(entity);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Aluno> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aluno getById(@PathVariable(value = "id") Long id) {

        Aluno entity = repository.findById(id)
                .orElse(new Aluno());

        return entity;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aluno update(@PathVariable(value = "id") Long id,
                        @RequestBody Aluno entity) {
        Aluno entityFind = repository.findById(id).orElse(null);
        if (entityFind != null) {
            entityFind.setId(entityFind.getId());
            entityFind.setNome(entity.getNome());
            entityFind.setDocumento(entity.getDocumento());
            return repository.save(entityFind);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        Aluno entity = repository.findById(id)
                .orElse(null);

        if (entity != null) {
            repository.delete(entity);
        }

        return ResponseEntity.noContent().build();
    }

}
