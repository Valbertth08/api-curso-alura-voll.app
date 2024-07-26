package med.voll.api.domain.consulta;

public enum MotivoCancelamento {

    PACIENTE_DESISTIU("paciente desistiu"),
    MEDICO_CANCELOU("medico cancelou"),
    OUTROS("outro");
    private String motivo ;

    MotivoCancelamento(String status) {
        this.motivo = status;
    }
    String getStatus(){
        return this.motivo;
    }
}
