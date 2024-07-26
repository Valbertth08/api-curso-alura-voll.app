package med.voll.api.infra.exception;


import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros  {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400( MethodArgumentNotValidException ex){
        var erros= ex.getFieldErrors();//pega todos os erros
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
        //converto a a stream em uma lista de DadosErroValidacao
    }
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio( ValidacaoException ex){
      return ResponseEntity.badRequest().body(ex.getMessage());
    }
    private record DadosErroValidacao(String campo, String mensagem){
        //DTO responsavel por pegar somente o campo e mensagem do campo
        public DadosErroValidacao(FieldError ex){
            this(ex.getField(),ex.getDefaultMessage());
                //pega o campo     //pega mensagem
        }
    }


}
