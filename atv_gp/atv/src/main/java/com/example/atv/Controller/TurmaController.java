package com.example.atv.Controller;

import com.example.atv.DTO.TurmaDTO;
import com.example.atv.Entity.Turma;
import com.example.atv.Service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turma")
public class TurmaController{
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public List<Turma> getAll(@RequestParam(required = false) String nome){
        if(nome != null && !nome.isEmpty()){
            return turmaService.getAllTurmasByNome(nome);
        }
        return turmaService.getAllTurmas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getById(@PathVariable Long id){
        Optional<TurmaDTO> turmaDTOOptional = turmaService.getById(id);
        if(turmaDTOOptional.isPresent()){
            return ResponseEntity.ok(turmaDTOOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> create(@RequestBody TurmaDTO turmaDTO){
        TurmaDTO turmaDTOSave = turmaService.createTurma(turmaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTOSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO){
        Optional<TurmaDTO> turmaDTOOptional = turmaService.updateTurma(id, turmaDTO);
        if(turmaDTOOptional.isPresent()){
            return ResponseEntity.ok(turmaDTOOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/aluno-add/{idAluno}")
    public ResponseEntity<String> addAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){
        if(turmaService.addAlunoTurma(id, idAluno)){
            return ResponseEntity.ok("Aluno adicionado!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor ou Aluno não encontrado");
        }
    }

    @PutMapping("/{id}/aluno-remove/{idAluno}")
    public ResponseEntity<String> removeAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){
        if(turmaService.removeAlunoTurma(id, idAluno)){
            return ResponseEntity.ok("Aluno retirado da turma");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao retirar aluno da turma");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(turmaService.delete(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}


