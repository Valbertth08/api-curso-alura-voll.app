package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc//responsavel por permite que conseguimos injetar o MockMvc
@AutoConfigureJsonTesters//respponsavel por permitir que conseguimos injetar o JsonTester
class ConsultaControllerTest {
    //simula requisições http
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJason;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJason;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria o codigo 400 quando informações estão invalidas")
    @WithMockUser//tira a necessidade de estar logado para fazer a requisição(anula o spring security)
    void agendar_cenario1() throws Exception {
        //esse metodo performa uma requisição na api, e pego retorno da requisição
        var response= mvc.perform(post("/consulta")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        //compararo o codigo da resposta da variavel, como o codigo http em que deejo testar, pegando o seu valor
    }
    @Test
    @DisplayName("Deveria o codigo 200 quando informações estão validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade= Especialidade.CARDIOLOGIA;
        var dadosDetalhamento=new DadosDetalhamentoConsulta(null,2l,5l,data);
        //mockito independente do dados que for passado para o metodo agendar, retorne um dadosDetalhamentoConsulta
         when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);
        var response= mvc.perform(post("/consulta")
                //indico que o tipo de media enviado é um json
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJason.write(
                        new DadosAgendamentoConsulta(2l,5l,data,especialidade)
                ).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado= dadosDetalhamentoConsultaJason.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}