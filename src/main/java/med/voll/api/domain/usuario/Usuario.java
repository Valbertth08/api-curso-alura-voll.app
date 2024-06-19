package med.voll.api.domain.usuario;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.usuario.enums.UsuarioRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    public Usuario(DadoCadastroUsuarioDto dados, BCryptPasswordEncoder bc) {
        this.login=dados.login();
        this.senha=bc.encode(dados.senha());
        this.role=dados.role();
    }
    public  Usuario(String login,String senha){
        this.login=login;
        this.senha=senha;
    }
    @Override//usado para controle de permissão, ou seja, tenho varios tipos de permissão dependendo do perfil(admin,usuario etc..)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role== UsuarioRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));//usado para simular, um perfi, porque o spring precisa de um objeto valido
    }
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
