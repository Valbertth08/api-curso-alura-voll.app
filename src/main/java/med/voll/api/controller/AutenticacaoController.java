package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    //essa classe por padrão é quem dispara o processo de autenticaçao
    //Essa classe precisa ser configurada para poder ser reconhecida pelo o spring, e poder ser injetada no nosso controller
    @Autowired
    private AuthenticationManager manager;//essa classe dispara o processo de autenticação para  nossa outra classe Autenticação service,
    //não chamamos de forma direta ela no spring(por baixo dos panos a nossa classe que criamos que efetua essa autenticação)
    //Precisamos criar um bear para o AuthenticationManager
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());//esse UserNamePasswor.. funciona como um DTO autenticador utilizado pelo spring-security
            var authentication = manager.authenticate(authenticationTokentoken);//esse metodo devolve o usuario autenticado no sistama(ele chama a classe AUtentication personalizada automaticamente)
            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
            //o authentication.getPrincipal, ele pega o usario logado, por padrão esse metodo recebe um object, então precisamos fazer um casting;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
