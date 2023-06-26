package com.senac.springjwt.repository;

import com.senac.springjwt.models.Aluno;
import com.senac.springjwt.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}