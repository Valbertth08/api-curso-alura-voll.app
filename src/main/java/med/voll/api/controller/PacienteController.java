package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")//indico que eu vou utilizar o token para requisições na interface do swagger
public class PacienteController {
    @Autowired
    PacienteRepository pacienteRepository;
    @PostMapping
    @Transactional
    public ResponseEntity  cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente= new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri= uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));

    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>>  listar(Pageable paginacao){
        var pacientes=pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable  Long id){
        var paciente=pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
    @PutMapping
    @Transactional
    public  ResponseEntity  atualizar(@RequestBody @Valid DadosAtualizarPaciente dados){
        var paciente= pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico= pacienteRepository.getReferenceById(id);
        medico.excluir();
        return  ResponseEntity.noContent().build();
    }
}
