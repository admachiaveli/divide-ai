package io.github.admachiaveli.divideaibackend.repo;

import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ValorAdicionalRepo extends JpaRepository<ValorAdicional, Long>{
    
    List<ValorAdicional> findAllByContaIdConta(Long idConta);
    
    List<ValorAdicional> findAllByContaIdContaAndTipoValorSigla(Long idConta, String sigla);
    
//    @Query(
//            value = 
//            " select "
//            + "va "
//            + "from ValorAdicional va "
//            + "join TipoValor tpo "
////            + "where tpo.sigla = '%:sigla_valor%'"
//           )
//    List<ValorAdicional> findyAllByContaTipoValor(@Param("id_conta") Long idConta, @Param("sigla_valor") String siglaValor);

}
