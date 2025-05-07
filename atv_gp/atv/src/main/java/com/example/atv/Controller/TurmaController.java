package com.example.atv.Controller;

import com.example.atv.DTO.TurmaDTO; //importar as classes
import com.example.atv.Entity.Turma;  //importar as classes
import com.example.atv.Service.TurmaService; //importar as classes
import org.springframework.beans.factory.annotation.Autowired;  //importar o @Autowired, para pegar as dependencias automaticas
import org.springframework.http.HttpStatus; //retornar respostas http que são personalizadas
import org.springframework.http.ResponseEntity;  //retornar respostas http que são personalizadas
import org.springframework.web.bind.annotation.*;  //retornar respostas http que são personalizadas

import java.util.List;   //permite mostrar listas de objeto
import java.util.Optional; //encapsulamento de valores

@RestController //vai indicar que essa classe é um rest, retornando em json
@RequestMapping("/turma")  //aqui é a configuração de como os endpoints vão ser chamados no caso, /turma
public class TurmaController{ //criação da classe
    @Autowired //colocar o serviço da turma, que vai conter a lógica
    private TurmaService turmaService; //instancia da TurmaService, possuindo a 'lógica'

    //Vai listar as turmas, se o nome que colocar ser dado como um parametro, vai filtrar pelo nome
    @GetMapping //é um endpoint para listar as turmas, se o nome que colocar ser dado como um parametro, vai filtrar pelo nome
    public List<Turma> getAll(@RequestParam(required = false) String nome){
        if(nome != null && !nome.isEmpty()){
            return turmaService.getAllTurmasByNome(nome); //se fornecer ''nome'' irá retornar as turmas filtradas pelo nome
        }
        return turmaService.getAllTurmas(); //caso não aconteça, vai retornar todas as turmas
    }

    //É a busca da turma por id, e também se encontrar vai retornar um ok e se caso não encontre seria não encontrado.
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getById(@PathVariable Long id){ //tenta buscar a turma com id
        Optional<TurmaDTO> turmaDTOOptional = turmaService.getById(id); //tenta buscar a turma com id
        if(turmaDTOOptional.isPresent()){ //se a turma existir vai retornar a busca como sucesso
            return ResponseEntity.ok(turmaDTOOptional.get()); //se a turma existir vai retornar a busca como sucesso
        }else {
            return ResponseEntity.notFound().build(); // e aqui se não existir vai retornar o famoso not found
        }
    }

    //Criação de uma nova turma utilizando um endpoint POST
    //o json será convertido para turmaDTO
    //e se criar a turma retornará meio que um ok(que deu certo a criação)
    @PostMapping
    public ResponseEntity<TurmaDTO> create(@RequestBody TurmaDTO turmaDTO){
        TurmaDTO turmaDTOSave = turmaService.createTurma(turmaDTO); //chamar o turmaService para criar a turma
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTOSave); //vai retornar os dados da turma criada
    }

    //criação do endpoint PUT
    //Atualizar a turma pelo id
    //Pode retornar se deu certo a busca, e se não der certo retorna o erro
    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO){
        Optional<TurmaDTO> turmaDTOOptional = turmaService.updateTurma(id, turmaDTO); //chamar o turmaService para atualizar a turma
        if(turmaDTOOptional.isPresent()){
            return ResponseEntity.ok(turmaDTOOptional.get()); //se a atualização da turma der certo vai retornar ok
        }else{
            return ResponseEntity.notFound().build(); //se não encontrar vai retornar erro
        }
    }

    //criação do endpoint PUT Adicionar um aluno a turma
    @PutMapping("/{id}/aluno-add/{idAluno}")
    public ResponseEntity<String> addAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){
        if(turmaService.addAlunoTurma(id, idAluno)){ //é a tentativa de adicionara o aluno a turma
            return ResponseEntity.ok("Aluno adicionado!"); //e aqui retorna um ok caso consiga
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor ou Aluno não encontrado"); //aqui seria senão conseguir, retornando o erro
        }
    }

    //criação do endpoint PUT para Remover um aluno da turma
    @PutMapping("/{id}/aluno-remove/{idAluno}")
    public ResponseEntity<String> removeAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){
        if(turmaService.removeAlunoTurma(id, idAluno)){ //onde vai remover o aluno da turma
            return ResponseEntity.ok("Aluno retirado da turma"); //se caso der certo, vai retornar a frase 'Aluno retirado da Turma'
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao retirar aluno da turma"); //se der erro, vai retornar a frase 'Erro ao retirar aluno da turma'
        }
    }

    //Criação do DELETE para excluir uma turma
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(turmaService.delete(id)){  //tenta fazer a remoção da turma pelo id que vai ser fornecido
            return ResponseEntity.noContent().build(); //aqui é caso der certo
        }else {
            return ResponseEntity.notFound().build(); //já aqui é a apresentação da mensagem de erro caso não consiga excluir a turma
        }
    }
}


