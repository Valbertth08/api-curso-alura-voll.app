package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginiacao);
    @Query("""
            select m from Medico m
            where
            m.ativo=true
            and m.especialidade= :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where c.data= :data
            )
            order by rand()
            limit 1
            """)
    //o :especilaidade e :data (indica que é o parametro do metodo)
    //o m.id not in() estou indicando que não é pra trazer os ids que não estão dentro desse subselect, isso
    //foi feito para fazer a filtragem dos que estejam agendato nessa data, assim na consulta principal pega só medicos que
    //estejam livres, não tendo nenhum agendamento na data mostrada.
    Medico escolherMedicoAletorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
