package com.example.atv.Service;


import com.example.atv.DTO.AlunoDTO;
import com.example.atv.Entity.Aluno;
import com.example.atv.Repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Optional;

    @Service

    public class AlunoService {
        @Autowired
        private AlunoRepository alunoRepository;

        public List<Aluno> getAll() {
            return alunoRepository.findAll();
        }

        public Optional<AlunoDTO> getById(Long id){
            Optional<Aluno> alunoOptional = alunoRepository.findById(id);
            if(alunoOptional.isPresent()){
                AlunoDTO produtoDTO = new AlunoDTO();
                return Optional.of(produtoDTO.fromAluno(alunoOptional.get()));
            }else {
                return Optional.empty();
            }
        }

        public AlunoDTO create(AlunoDTO alunoDTO){
            Aluno aluno = alunoDTO.toAluno();
            aluno = alunoRepository.save(aluno);
            return alunoDTO.fromAluno(aluno);
        }

        public Optional<AlunoDTO> updateAluno(Long id, AlunoDTO alunoDTO){
            Optional<Aluno> alunoOptional = alunoRepository.findById(id);
            if(alunoOptional.isPresent()){
                Aluno aluno = alunoOptional.get();
                aluno.setNome(alunoDTO.getNome());
                aluno.setCpf(alunoDTO.getCpf());
                aluno = alunoRepository.save(aluno);
                return Optional.of(alunoDTO.fromAluno(aluno));
            }else{
                return Optional.empty();
            }
        }

        public boolean delete(Long id){
            if(alunoRepository.existsById(id)){
                alunoRepository.deleteById(id);
                return true;
            }else {
                return false;
            }
        }
    }


