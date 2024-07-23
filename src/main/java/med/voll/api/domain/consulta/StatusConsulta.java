package med.voll.api.domain.consulta;

public enum StatusConsulta {

    ATIVA("ativa"),
    CANCELADA("cancelada");
    private String status;

    StatusConsulta(String status) {
        this.status = status;
    }
    String getStatus(){
        return this.status;
    }
}
