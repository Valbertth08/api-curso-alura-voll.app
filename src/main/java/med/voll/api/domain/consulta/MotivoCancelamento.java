package med.voll.api.domain.consulta;

public enum MotivoCancelamento {
    PACIENTE_DESISTIU("paciente desistiu"),
    MEDICO_CANCELOU("medico cancelou");
    private  String motivo;

    MotivoCancelamento(String motivo) {
        this.motivo = motivo;
    }
    public String getMotivo() {
        return motivo;
    }
}
