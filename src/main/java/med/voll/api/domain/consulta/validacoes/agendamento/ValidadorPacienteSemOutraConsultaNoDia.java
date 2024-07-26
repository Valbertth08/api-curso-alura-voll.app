package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository pacienteRepository;
    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario=dados.data().withHour(7);
        var ultimoHorario=dados.data().withHour(18);
        var pacientePossuirOutraConsultaNoDia=pacienteRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario,ultimoHorario);
        if(pacientePossuirOutraConsultaNoDia){
            throw new ValidacaoException("Consulta não pode ser agendada nesse dia");
        }
    }

}
