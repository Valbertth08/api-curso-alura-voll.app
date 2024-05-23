package med.voll.api.domain.usuario;

import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //esse metodo é chamado automaticamente quando o usuario fizer login no projeto(vai buscar a classe e chamar esse metodo)
        return  repository.findByLogin(username) ;

    }
    //a implementação dessa interface indica que essa classe vai implementar o serviço de autenticação
    //o proprio spring automaticamente vai chamar essa classe





}
