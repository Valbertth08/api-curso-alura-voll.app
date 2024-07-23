package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        System.out.println("chegou no controller");
        var medico=new Medico(dados);
        repository.save(medico);
        //URI: quando utilizamos UriComponentsBuilder ele cria o caminho padrão da api (http://localhost:8080) obs: isso na nossa maquina local caso seja outro enderço, é outra coisa
        var uri= uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();  //o path é o complemento da uri, para o novo recurso
        //.buildAndExpand esse metodo é responsavel por identificar o campo que esta entre chaves e associalo ao que estar sendo passado como parametro
        //toUri é responsavel por criar  o objeto ur
        return ResponseEntity.created(uri).body(new DadosDetalhadamentoMedico(medico));
        //devolve no header o caminho do recurso, ou seja o location

    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(sort = {"nome"}) Pageable paginiacao){
        //@PageableDefault: eu posso definir um padrão de paginação caso nenhum parmetro seja passado na url, quanto de paginação, ou se ordenação.
        //obs: caso os parametros sejam passados na url, irar sobrescrever o default.
        var page=  repository.findAllByAtivoTrue(paginiacao).map(DadosListagemMedico::new);
        //obs: o spring possuem um findAll que pesquisa baseado na page indicada.
        //map(DadosListagemMedico::new) === .map(medico -> new DadosListagemMedico(medico))// ambas duas formas fazem a mesma coisa
        //estou pegando a lista de medicos retornado pelo o findall, transformando essa lista em strems, passando cada
        //medico como argumento para o construtor de DadosdeListagemMedico e transformando em DadosListagemMedico e retornando ele como uma lista
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico= repository.getReferenceById(id); // esse metodo lança uma exeção caso não encontre o id
        return ResponseEntity.ok(new DadosDetalhadamentoMedico(medico));

    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico=repository.getReferenceById(dados.id());//pega a referencia do no banco, mas não carrega ele.
        medico.atualizarInformacoes(dados);
        //eu não preciso definir nem uma nova chamada de save, porque o proprio JPA atualiza a referencia, ela consegue identificar a mudança no atributo(principalmente pelo o transaction)tudo roda encima de uma transação
        return ResponseEntity.ok(new DadosDetalhadamentoMedico(medico));
        // eu preciso criar um DTO pra retorna o medico, porque não é uma boa pra dica enviar a entidade JPA na resposta da requisição
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable  Long id){
        var medico= repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();//no content não retorna do ResponseEntity, por isso uso o build que é responsavel por criar o responseEntit
    }
}
