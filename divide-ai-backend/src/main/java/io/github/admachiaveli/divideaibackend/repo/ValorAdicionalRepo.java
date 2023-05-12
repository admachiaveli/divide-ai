package io.github.admachiaveli.divideaibackend.repo;

import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorAdicionalRepo extends JpaRepository<ValorAdicional, Long> {

    List<ValorAdicional> findAllByContaIdConta(Long idConta);

    List<ValorAdicional> findAllByContaIdContaAndTipoValorSigla(Long idConta, String sigla);

}
