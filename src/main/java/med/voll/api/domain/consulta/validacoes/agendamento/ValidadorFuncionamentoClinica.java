package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){

        var dataConsulta=dados.data();

        var domingo=dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);//estou verificando se a data que esta chegando é um domingo
        var antesDaAberturaDaClinica=dataConsulta.getHour()<7;
        var depoisDoEncerramnento=dataConsulta.getHour()>18;

        if(domingo || antesDaAberturaDaClinica || depoisDoEncerramnento){
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }
}
