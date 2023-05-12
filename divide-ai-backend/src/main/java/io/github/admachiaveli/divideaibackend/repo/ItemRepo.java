package io.github.admachiaveli.divideaibackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.admachiaveli.divideaibackend.model.Item;
import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long>{

    List<Item> findAllByParticipanteIdParticipante(Long idParticipante);
    List<Item> findAllByParticipanteContaIdConta(Long idConta);
    
}
