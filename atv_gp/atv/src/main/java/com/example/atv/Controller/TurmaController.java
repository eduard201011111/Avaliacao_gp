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
    @GetMapping("/{id}") //mapeia os http que são do get para um metodo controlador o id indica que a url tera um valor variavel no caso um id que seria um número, ele é importante  pois vai definir o endpoint da API
    public ResponseEntity<TurmaDTO> getById(@PathVariable Long id){ //getById vai chamar um metodo que vai acessar o banco e buscar o Id nesse caso seria o Id da turma, a relação com o service é que ele meio que faz uma separaçõa da lógica de negócio da lógica controle que no caso vem da controller
        // tenta buscar a turma com id //pathvariable faz a busca por url
        Optional<TurmaDTO> turmaDTOOptional = turmaService.getById(id); //O optional é a representação de um valor que pode ou não pode estar presente, a vantagem dele é forçar a tratar a falta de valores que não estão sendo bem explicitos
        // tenta buscar a turma com id
        if(turmaDTOOptional.isPresent()){ //o isPresent vai verificar se o optional vai ter um valor(se encontrou uma turma), ele vai afetar o fluxo se a turma existir ele vai retornar que deu certo se não der certo ele retorna erro
            return ResponseEntity.ok(turmaDTOOptional.get()); //se a turma existir vai retornar a busca como sucesso
        }else {
            return ResponseEntity.notFound().build(); // e aqui se não existir vai retornar o famoso not found
        }
    }

    //Criação de uma nova turma utilizando um endpoint POST
    //o json será convertido para turmaDTO
    //e se criar a turma retornará meio que um ok(que deu certo a criação)
    @PostMapping
    public ResponseEntity<TurmaDTO> create(@RequestBody TurmaDTO turmaDTO){ //responseentity vai apresentar uma resposta HTTP, se utiliza bastante pois ele envolve o objeto TurmaDTO para retornar dados da turma junto com o número do código HTTP
        // requestbody faz o mapeamento das requisições http
        TurmaDTO turmaDTOSave = turmaService.createTurma(turmaDTO); //chamar o turmaService para criar a turma
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTOSave); //vai retornar os dados da turma criada
    }

    //criação do endpoint PUT
    //Atualizar a turma pelo id
    //Pode retornar se deu certo a busca, e se não der certo retorna o erro
    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO){//pathvariable faz a extraçõa da url, ele conecta o valor da url ao parametro id do método
        // requestbody faz o mapeamento das requisições http
        Optional<TurmaDTO> turmaDTOOptional = turmaService.updateTurma(id, turmaDTO); //chamar o turmaService para atualizar a turma
        if(turmaDTOOptional.isPresent()){
            return ResponseEntity.ok(turmaDTOOptional.get()); //o get vai extrair o objeto que está dentro e o .ok vai criar a resposta http com o código ok
            // se a atualização da turma der certo vai retornar ok
        }else{
            return ResponseEntity.notFound().build(); //se não encontrar vai retornar erro, a importância é que vai indicar que o recurso buscado não foi encontrado, ele retorna o código de número 404
        }
    }

    //criação do endpoint PUT Adicionar um aluno a turma
    @PutMapping("/{id}/aluno-add/{idAluno}")
    public ResponseEntity<String> addAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){//pathvariable faz a busca por url
        if(turmaService.addAlunoTurma(id, idAluno)){ //é a tentativa de adicionara o aluno a turma
            return ResponseEntity.ok("Aluno adicionado!"); //e aqui retorna um ok caso consiga
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor ou Aluno não encontrado"); //aqui seria senão conseguir, retornando o erro
        }
    }

    //criação do endpoint PUT para Remover um aluno da turma
    @PutMapping("/{id}/aluno-remove/{idAluno}")
    public ResponseEntity<String> removeAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){ //pathvariable faz a busca por url
        if(turmaService.removeAlunoTurma(id, idAluno)){ //onde vai remover o aluno da turma
            return ResponseEntity.ok("Aluno retirado da turma"); //se caso der certo, vai retornar a frase 'Aluno retirado da Turma'
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao retirar aluno da turma"); //se der erro, vai retornar a frase 'Erro ao retirar aluno da turma'
        }
    }

    //Criação do DELETE para excluir uma turma
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ //pathvariable faz a busca por url
        if(turmaService.delete(id)){  //tenta fazer a remoção da turma pelo id que vai ser fornecido
            return ResponseEntity.noContent().build(); //aqui é caso der certo
        }else {
            return ResponseEntity.notFound().build(); //já aqui é a apresentação da mensagem de erro caso não consiga excluir a turma
        }
    }
}


//as respotas já estão no código mas para facilitar coloquei aqui embaixo também

//Explique o que o @GetMapping faz e o que o "{id}" significa neste contexto. Qual é a importância dessa anotação e qual endpoint ela define para a aplicação?
//R:mapeia os http que são do get para um metodo controlador o id indica que a url tera um valor variavel no caso um id que seria um número, o Id é um identificador único como o nome diz ele é um meio de você identificar algo, a anotação é importante  pois vai definir o endpoint da API, e ela define o get como endpoint


//O que é a anotação @PathVariable? O que ela faz com o valor passado no caminho da URL (no caso, o id)? Explique a função do parâmetro id e sua relação com o método getById.
//R:pathvariable faz a extraçõa da url, ele conecta o valor da url ao parametro id do método

//O que significa o uso do Optional<ClienteDTO>? Qual a vantagem de usar Optional em vez de retornar um objeto diretamente?
//R:O optional é a representação de um valor que pode ou não pode estar presente, a vantagem dele é forçar a tratar a falta de valores que não estão sendo bem explicitos

//O que o método clienteService.getById(id) faz e como ele se relaciona com o serviço de cliente na aplicação?
//R:getById vai chamar um metodo que vai acessar o banco e buscar o Id nesse caso seria o Id da turma, a relação com o service é que ele meio que faz uma separaçõa da lógica de negócio da lógica controle que no caso vem da controller

//O que faz o método isPresent() e como ele verifica se o valor dentro do Optional está presente? Como isso afeta o fluxo do código?
//R:o isPresent vai verificar se o optional vai ter um valor(se encontrou uma turma), ele vai afetar o fluxo se a turma existir ele vai retornar que deu certo se não der certo ele retorna erro

//Explique o que acontece quando o Optional contém um valor. O que o ResponseEntity.ok() faz e qual é a importância do get()?
//R:o get vai extrair o objeto que está dentro e o .ok vai criar a resposta http com o código ok

//O que ocorre quando o Optional está vazio? O que faz ResponseEntity.notFound().build() e qual é a resposta HTTP gerada nesse caso?
//R:Se não encontrar vai retornar erro, a importância é que vai indicar que o recurso buscado não foi encontrado, ele retorna o código de número 404
