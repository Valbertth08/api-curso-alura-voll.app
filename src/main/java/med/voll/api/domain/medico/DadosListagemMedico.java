package med.voll.api.domain.medico;
public record DadosListagemMedico(Long id,String nome, String email,String crm, Especialidade especialidade) {
    public DadosListagemMedico(Medico medico){
        this(medico.getId(),medico.getNome(),medico.getEmail(),medico.getCrm(), medico.getEspecialidade());
        //esses dados estão sendo estraidos para serem utilizado no outro construtor acima(ou seja, fazendo referencia ao outro construtor "this")
    }
}
