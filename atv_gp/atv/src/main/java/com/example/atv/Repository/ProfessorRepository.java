package com.example.atv.Repository;


import com.example.atv.Entity.Turma;
import com.example.atv.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {

        List<Professor> findAllByNome(String nome);

    }

