package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")//isso serve para indicar que eu estou lendo uma variavel do aplication.properties
    private String secret;//como esse atributo é uma confiruação do projeto, ele pode ser colocado no aplication.properties
    //então definimos ela la.
    //obs: eu consigo ler uma propriedade do aplication.properties em uma classe java
    public String gerarToken(Usuario usuario){
        try {
            var algoritimo = Algorithm.HMAC256(secret);//esse metodo recebe uma  chave secreta para fazer a geração do token
            return JWT.create()
                    .withIssuer("API voll.med")//identifico a ferramenta que é dona, resposavel pela criação do token(empresa0
                    .withSubject(usuario.getLogin())//passo o usuario que é responsavel por esse token
                    //pegando o id do usuario e passando para ir no token (isso é um dicionario, chave valor)
                    .withExpiresAt(dataExpiracao())//metodo responsavel por expirar o tokem (determinar um tempo pra ele)
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar o token",exception);
        }
    }
    public String getSubject(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API voll.med")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token ivalido ou inspirado");
        }
    }
    private Instant dataExpiracao() {//esse metodo é resposavel por pegar a data atual e somar mais 2 horas(esse sera
        //o tempo de expiração do token
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
