package med.voll.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.usuario.enums.UsuarioRole;

public record DadoCadastroUsuarioDto(
        @NotBlank
        String login,
        @NotBlank
        String senha,
        @NotNull
        UsuarioRole role) {
}
