package med.voll.api.domain.consulta;


import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("id do paciente informado não existe");
        }
        //se o medico for diferente de null e ele não existir no banco de dados
        if(dados.idMedico()!=null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("id do medico informado não existe");
        }
        var paciente=pacienteRepository.getReferenceById(dados.idPaciente());
        var medico= escolherMedico(dados);
        var consulta=new Consulta(null,medico,paciente,dados.data(),StatusConsulta.ATIVA);
        consultaRepository.save(consulta);
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
}
