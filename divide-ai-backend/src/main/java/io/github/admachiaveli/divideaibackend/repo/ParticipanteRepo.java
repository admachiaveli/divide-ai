package io.github.admachiaveli.divideaibackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.admachiaveli.divideaibackend.model.Participante;
import java.util.List;

public interface ParticipanteRepo extends JpaRepository<Participante, Long>{
    
    List<Participante> findAllByContaIdConta(Long idConta);
    
}
