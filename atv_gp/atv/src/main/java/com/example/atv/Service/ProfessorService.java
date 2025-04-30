package com.example.atv.Service;

import com.example.atv.DTO.ProfessorDTO;
import com.example.atv.Entity.Professor;
import com.example.atv.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getAll() {
        return professorRepository.findAll();
    }


    public List<Professor> getAllByName(String nome) {
        return professorRepository.findAllByNome(nome);
    }

    public Optional<ProfessorDTO> getProfessorById(Long id) {
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if (professorOptional.isPresent()) {
            ProfessorDTO professorDTO = new ProfessorDTO();
            return Optional.of(professorDTO.fromProfessor(professorOptional.get()));
        } else {
            return Optional.empty();
        }
    }


    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        Professor professor = professorDTO.toProfessor();
        professor = professorRepository.save(professor);
        return professorDTO.fromProfessor(professor);
    }

    public Optional<ProfessorDTO> updateProfessor(Long id, ProfessorDTO professorDTO) {
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if (professorOptional.isPresent()) {
            Professor professor = professorOptional.get();
            professor.setNome(professorDTO.getNome());
            professor.setSobrenome(professorDTO.getSobrenome());
            professor = professorRepository.save(professor);
            return Optional.of(professorDTO.fromProfessor(professor));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteProfessor(Long id) {
        if (professorRepository.existsById(id)) {
            professorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
