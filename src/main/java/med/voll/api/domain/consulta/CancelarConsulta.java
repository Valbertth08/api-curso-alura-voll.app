package med.voll.api.domain.consulta;


import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CancelarConsulta {
    @Autowired
    private  ConsultaRepository consultaRepository;

    public void  cancelar(DadosCancelarConsulta dados){
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new  ValidacaoException("consulta não encontrado");
        }
        var consulta= consultaRepository.getReferenceById(dados.idConsulta());
        if(consulta.getStatus() == StatusConsulta.CANCELADA){
            throw new  ValidacaoException("consulta não encontrado");
        }
        verificarHora(consulta);
    }
  public void verificarHora( Consulta consulta){
      Duration duration= Duration.between(LocalDateTime.now(),consulta.getData());
      if(duration.toHours()<24){
          throw new ValidacaoException("A consulta não pode ser cancelada");
      }
      consulta.excluir();
    }
}
