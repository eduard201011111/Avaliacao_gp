package com.example.atv.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Professor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;

    @ManyToMany(mappedBy = "professor")
    @JsonIgnore
    private Set<Turma> turmas;

    public Professor(Long id, String nome, String sobrenome){
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }
}
