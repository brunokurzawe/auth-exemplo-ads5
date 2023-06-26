package com.senac.springjwt.models;

import javax.persistence.*;

@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "I_ALUNOS", referencedColumnName = "ID")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "I_CURSOS", referencedColumnName = "ID")
    private Curso curso;

    public Aula() {
    }

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
