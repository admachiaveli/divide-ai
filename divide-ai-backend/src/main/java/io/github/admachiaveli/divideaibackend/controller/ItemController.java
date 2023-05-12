package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Item;
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
import io.github.admachiaveli.divideaibackend.repo.ItemRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ParticipanteRepo participanteRepo;

    @GetMapping
    public List<Item> findAll() {
        return itemRepo.findAll();
    }

    @GetMapping("{idConta}")
    public List<Item> findAll(@PathVariable Long idConta) {
        //Retorna todos os itens de participantes pertencentes a conta
        return itemRepo.findAllByParticipanteContaIdConta(idConta);
    }

    @PostMapping("{idParticipante}")
    public ResponseEntity<Item> save(@PathVariable Long idParticipante, @RequestBody Item item) throws ValidationException {

        if (item.getDescricao() == null || item.getDescricao().equals("")) {
            throw new ValidationException("A descrição do item é obrigatória");
        }
        if (item.getValor() == null) {
            throw new ValidationException("O valor do item é obrigatório");
        }
        if (item.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O valor do item deve ser maior que zero");
        }
        if (idParticipante == null || idParticipante == 0) {
            throw new ValidationException("O participante é obrigatório");
        }

        Participante participante = participanteRepo.findById(new Long(item.getIdParticipante())).get();

        if (participante == null) {
            throw new ValidationException("O participante é inválido");
        } else {
            item.setParticipante(participante);
        }

        Item saved = itemRepo.save(item);

        return new ResponseEntity<Item>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idItem}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idItem) {
        itemRepo.deleteById(idItem);
    }

}
