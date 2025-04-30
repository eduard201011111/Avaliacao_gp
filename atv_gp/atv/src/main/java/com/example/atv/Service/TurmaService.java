package com.example.atv.Service;

import com.example.atv.DTO.TurmaDTO;
import com.example.atv.Entity.Aluno;
import com.example.atv.Entity.Turma;
import com.example.atv.Repository.AlunoRepository;
import com.example.atv.Repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public List<Turma> getAllTurmas() {
        return turmaRepository.findAll();
    }

    public List<Turma> getAllTurmasByNome(String nome){
        return turmaRepository.getAllByNome(nome);
    }

    public Optional<TurmaDTO> getById(Long id){
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if(turmaOptional.isPresent()){
            TurmaDTO turmaDTO = new TurmaDTO();
            return Optional.of(turmaDTO.fromTurma(turmaOptional.get()));
        }else {
            return Optional.empty();
        }
    }

    public TurmaDTO createTurma(TurmaDTO turmaDTO){
        Turma turma = turmaDTO.toTurma();
        turma = turmaRepository.save(turma);
        return turmaDTO.fromTurma(turma);
    }

    public Optional<TurmaDTO> updateTurma(Long id, TurmaDTO turmaDTO){
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if(turmaOptional.isPresent()){
            Turma turma = turmaOptional.get();
            turma.setSigla(turmaDTO.getSigla());
            turma.setNumeroSala(turmaDTO.getNumeroSala());
            turma.setNome(turmaDTO.getNome());
            turma.setProfessor(turmaDTO.getProfessor());

            turma = turmaRepository.save(turma);

            return Optional.of(turmaDTO.fromTurma(turma));
        }else{
            return Optional.empty();
        }
    }

    public boolean addAlunoTurma(Long id, Long idAluno){

        Optional<Turma> optionalTurma = turmaRepository.findById(id);
        if(!optionalTurma.isPresent()){
            return false;
        }


        Optional<Aluno> optionalAluno = alunoRepository.findById(idAluno);
        if(!optionalAluno.isPresent()){
            return false;
        }


        Turma turma = optionalTurma.get();
        Aluno aluno = optionalAluno.get();

        aluno.setTurma(turma);
        alunoRepository.save(aluno);
        return true;
    }

    public boolean removeAlunoTurma(Long id, Long idAluno){
        Optional<Aluno> optionalAluno = alunoRepository.findById(idAluno);
        if(!optionalAluno.isPresent()){
            return false;
        }
        Aluno aluno = optionalAluno.get();
        if (aluno.getTurma() != null && aluno.getTurma().getClass().equals(id)) {
            aluno.setTurma(null);
            alunoRepository.save(aluno);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if(turmaRepository.existsById(id)){
            turmaRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}