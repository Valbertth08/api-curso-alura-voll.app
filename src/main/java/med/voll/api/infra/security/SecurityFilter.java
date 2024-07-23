package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component//indico que ela é um componente/generico (não é algo especifico)
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService token;
    @Autowired
    private UsuarioRepository repository;
    @Override//essa metodo é executada uma vez para cada requisição
    //esse metodo é executado antes de chegar no controller
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject=token.getSubject(tokenJWT);
            var usuario= repository.findByLogin(subject);
            var authentication= new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        System.out.println("chegou o di filter");
        filterChain.doFilter(request, response);//isso é usado para chamar o proximo filtro
    }
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeeader= request.getHeader("Authorization");
        if(authorizationHeeader!=null){
            return authorizationHeeader.replace("Bearer ","");
        }
        else{
            return  null;
        }
    }
}
