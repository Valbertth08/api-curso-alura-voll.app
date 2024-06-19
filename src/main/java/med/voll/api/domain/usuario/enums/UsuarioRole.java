package med.voll.api.domain.usuario.enums;

public enum UsuarioRole {
    USER("user"),
    ADMIN("admin");
    private String role;
    UsuarioRole(String user) {

        this.role=user;
    }
    public String getRole() {

        return role;
    }
}
