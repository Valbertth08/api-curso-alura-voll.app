package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consulta")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private CancelarConsulta cancelarConsulta;
    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsulta dados){
        agenda.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta( null,null,null,null));
    }
    @DeleteMapping
    @Transactional
    public  ResponseEntity cancelar(@RequestBody @Valid DadosCancelarConsulta dados){
        cancelarConsulta.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
