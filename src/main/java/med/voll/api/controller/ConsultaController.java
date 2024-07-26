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

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsulta dados){
       var dto= agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping
    @Transactional
    public  ResponseEntity cancelar(@RequestBody @Valid DadosCancelarConsulta dados){
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
