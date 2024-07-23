package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.Endereco;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    @OneToMany(mappedBy = "medico")
    private List<Consulta> lista= new ArrayList<>();

    private Boolean ativo;
    public Medico(DadosCadastroMedico dados) {
        this.ativo=true;
        this.nome=dados.nome();
        this.email= dados.email();
        this.telefone=dados.telefone();
        this.crm=dados.crm();
        this.especialidade=dados.especialidade();
        this.endereco=new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarMedico dados) {
        //se eu não fizer essa verificação de que se os campos que estão vindo da requisição, existe valor ou não, caso eles não existam será setado nulo por padrão,
        //por isso preciso saber se existe ou não valor .
        if(dados.nome() != null ){
            this.nome=dados.nome();
        }
        if(dados.telefone() != null ){
            this.nome=dados.telefone();
        }
        if(dados.endereco() != null ){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }
    public void excluir() {
        this.ativo=false;
    }
}
