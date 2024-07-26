package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.DadosCancelarConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelarConsulta dados);
}
