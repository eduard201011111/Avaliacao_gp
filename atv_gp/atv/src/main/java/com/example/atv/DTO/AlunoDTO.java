package com.example.atv.DTO;

import com.example.atv.Entity.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlunoDTO {

    private Long id;
    private String nome;
    private String cpf;


    public Aluno toAluno(){
        return new Aluno(
                this.id,
                this.nome,
                this.cpf
        );
    }

    public AlunoDTO fromAluno(Aluno aluno){
        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCpf()
        );
    }
}

