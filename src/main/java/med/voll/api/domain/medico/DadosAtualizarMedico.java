package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        //esse record serve para indicar que tipos de dados eu posso atualizar. sem a necessidade de ter que atualizar todos(
        //dessa forma consigo filtrar os dados.
        @NotNull
        Long id,
        //esse campo não pode ser nulo, porque ele que é o responsavel por identificar o objeto que precisa ser atualizado
        String nome,
        String telefone,
        @Valid DadosEndereco endereco) {

}
