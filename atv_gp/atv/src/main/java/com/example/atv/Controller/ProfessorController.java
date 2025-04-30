package com.example.atv.Controller;

import com.example.atv.DTO.AlunoDTO;
import com.example.atv.DTO.TurmaDTO;
import com.example.atv.DTO.ProfessorDTO;
import com.example.atv.Entity.Aluno;
import com.example.atv.Entity.Professor;
import com.example.atv.Service.AlunoService;
import com.example.atv.Service.ProfessorService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")

public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAll(@RequestParam(required = false) String nome){
        if(nome != null && !nome.isEmpty()){
            return professorService.getAllByName(nome);
        }
        return professorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getById(@PathVariable Long id){
        Optional<ProfessorDTO> professorDTOOptional = professorService.getProfessorById(id);
        if(professorDTOOptional.isPresent()){
            return ResponseEntity.ok(professorDTOOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> create(@RequestBody ProfessorDTO professorDTO){
        ProfessorDTO professorDTOSave = professorService.createProfessor(professorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorDTOSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> update(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO){
        Optional<ProfessorDTO> professorDTOOptional = professorService.updateProfessor(id, professorDTO);
        if(professorDTOOptional.isPresent()){
            return ResponseEntity.ok(professorDTOOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(professorService.deleteProfessor(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}

