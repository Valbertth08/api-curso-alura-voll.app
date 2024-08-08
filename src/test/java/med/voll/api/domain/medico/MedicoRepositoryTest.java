package med.voll.api.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//estou indicando para não substituir as minhas configurações para utilizar o banco de dados em memoria
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Test
    void escolherMedicoAletorioLivreNaData() {

    }
}