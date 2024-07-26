package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NonNull
        @Future//indica que a data precisa ser no futuro
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        Especialidade especialidade
) {
}
