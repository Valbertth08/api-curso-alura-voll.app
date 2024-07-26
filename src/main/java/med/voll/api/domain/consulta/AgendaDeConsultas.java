package med.voll.api.domain.consulta;


import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    //todos os validadores são injetados atraves dessa lista criada, isso de forma automatica
    // ou seja, todos os beans que implementam a interface seram injetodos, podendo ser (@service, @componente etc..)

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("id do paciente informado não existe");
        }
        //se o medico for diferente de null e ele não existir no banco de dados
        if(dados.idMedico()!=null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("id do medico informado não existe");
        }
        //isso vai passar por todos os validadores
        validadores.forEach(v->v.validar(dados));
        var paciente=pacienteRepository.getReferenceById(dados.idPaciente());
        var medico= escolherMedico(dados);
        if(medico==null){
            throw new ValidacaoException("Não existe medico disponivel nessa data");
        }
        var consulta=new Consulta(null,medico,paciente,dados.data(),null);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico()!=null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade()==null){
            throw  new ValidacaoException("Especialidade é obrigatoria");
        }
        return medicoRepository.escolherMedicoAletorioLivreNaData(dados.especialidade(),dados.data());
    }
    public void cancelar(DadosCancelarConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }
        validadoresCancelamento.forEach(v -> v.validar(dados));
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
