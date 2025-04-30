package com.example.atv.Repository;

import com.example.atv.Entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    }

