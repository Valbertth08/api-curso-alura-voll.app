package med.voll.api.domain.paciente;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.Endereco;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Paciente")
@Table(name = "Pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    @Embedded
    Endereco endereco;
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> lista= new ArrayList<>();

    private Boolean ativo;
    public Paciente(DadosCadastroPaciente dados) {
        this.ativo=true;
        this.nome=dados.nome();
        this.email=dados.email();
        this.cpf=dados.cpf();
        this.telefone= dados.telefone();
        this.endereco= new Endereco(dados.endereco());
    }
    public  void atualizarInformacoes( DadosAtualizarPaciente dados){
        if(dados.nome() != null){
            this.nome=dados.nome();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
        if(dados.telefone() != null){
            this.telefone=dados.telefone();
        }
    }
    public void excluir() {

        this.ativo=false;
    }
}
