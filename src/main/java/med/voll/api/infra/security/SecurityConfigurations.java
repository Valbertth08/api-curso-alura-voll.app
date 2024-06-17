package med.voll.api.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//indica que a classe é uma classe de configuração do Spring.
@EnableWebSecurity// indico que eu personalizo as configurações de segurança(personalizar e controlar os aspectos de segurança na sua aplicação Spring)
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;
    @Bean //expoe o retorno de um metodo( devolvo um objeto para o spring)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //HttpSecurity é o proprio spring que fornece esse parametro
        //metodo que muda o processo de autentincação padrão (statefull(seção) para stales)
        //esse tipo do metodo é usado para configurar coisas relacionadas ao processo de autorização e
        return http.csrf(csrf -> csrf.disable())//desabilito essa proteção contra ataque, porque por padrão token  ja vem com essa protação
                //indico que a sessão vai ter a politica de Stateless e não Statefful(que aparece o form, e bloqueia as requisições), desabilito a estrategia padrão
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(req->{
                    req.requestMatchers("/login").permitAll();//estou dando permissão de acesso sem um token, somente para login
                    req.anyRequest().authenticated();//indico   que em qualquer outra requisição a pessoa precisa esta autenticada//estou indicando que quero chamar meu filtro personalizado primeiro do padrão de segurança.
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean//utilizamos o bean para ensinar para o spring como ele criar um objetoo( ou seja, para ele saber inejtar o AuthenticationManager ele usa essa classe)
    //Esse metodo é para retornar um Authentication
    // anage, porque o spring por padrão não sabe criar a AuthenticationManage
    //Utilizamos o AuthenticationConfiguration,porque ele sabe como criar um Authentication Manager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();//esse metodo sabe criar um AutenticationManager
    }
    @Bean//indico para o projeto que as senhas estão sendo armazenadas com Bcripty ( ensino pro spring que ele usa esse algoritmo de senha)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //esse metodo ele é chamado porque implementamos o metodo UserDetails no usuario, então ele conhece como codificar igual a senha que esta no banco 
    }
}
