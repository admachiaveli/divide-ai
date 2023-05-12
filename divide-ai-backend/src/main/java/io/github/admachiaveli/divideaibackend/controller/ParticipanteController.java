package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value ="/participante")
public class ParticipanteController {

	@Autowired
	private ParticipanteRepo participanteRepo;
        
        @Autowired
	private ContaRepo contaRepo;
	
	@GetMapping
	public List<Participante> findAll() {
		return participanteRepo.findAll();
	}
        
        @GetMapping("{idConta}")
	public List<Participante> findAll(@PathVariable Long idConta) {
		return participanteRepo.findAllByContaIdConta(idConta);
	}
        
        @PostMapping("{idConta}")
	public ResponseEntity<Participante> save(@PathVariable Long idConta, @RequestBody Participante participante) throws ValidationException {
                Conta conta = new Conta();
            
                if(idConta == null) {
			throw new ValidationException("A conta associada ao participante é obrigatória");
		}
               
                if(!contaExiste(idConta)){
                    throw new ValidationException("A conta associada ao participante não existe");
                }
                
		if(participante.getNome() == null || participante.getNome().equals("")) {
			throw new ValidationException("O nome do participante é obrigatório");
		}
                
                participante.setConta(bucaConta(idConta));
		Participante saved = participanteRepo.save(participante);
		return new ResponseEntity<Participante>(saved, HttpStatus.CREATED);
	}
        
        @DeleteMapping("/{idParticipante}")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        public void delete(@PathVariable Long idParticipante){
            participanteRepo.deleteById(idParticipante);
        }
        
        public Conta bucaConta(Long idConta){
            return contaRepo.findById(idConta).get();
        }
        
        public Boolean contaExiste(Long idConta){
            return contaRepo.existsById(idConta);
        }
}
