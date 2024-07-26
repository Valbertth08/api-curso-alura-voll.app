package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo  implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.idMedico()==null){
            return;
        }
        var medicoEstarAtivo=repository.findAtivoById(dados.idMedico());
        if(!medicoEstarAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluido");
        }

    }
}
