package med.voll.api.infra.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//indica que a classe é uma classe de configuração do Spring.
@EnableWebSecurity// indico que eu personalizo as configurações de segurança(ersonalizar e controlar os aspectos de segurança na sua aplicação Spring)
public class SecurityConfigurations {
    @Bean //expoe o retorno de um metodo( devolvo um objeto para o spring)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //metodo que muda o processo de autentincação padrão (statefull(seção) para stales)
        //esse tipo do metodo é usado para configurar coisas relacionadas ao processo de autorização e
        return http.csrf(csrf -> csrf.disable())//desabilito essa proteção contra ataque, porque por padrão o security ja vem com essa protação
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//indico que a sessão vai ter a politica de Stateless e não Statefful, desabilito a estrategia padrão
                .build();
    }
    @Bean
    //Esse metodo é para retornar um AuthenticationManage, porque o spring por padrão não sabe criar a AuthenticationManage
    //Utilizamos o AuthenticationConfiguration,porque ele sabe como criar um Authentication Manager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();//esse metodo
    }

    @Bean//transformo a senha em hash
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
