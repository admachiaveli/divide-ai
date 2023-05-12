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

import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/conta")
public class ContaController {

    @Autowired
    private ContaRepo contaRepo;

    @GetMapping
    public List<Conta> findAll() {
        return contaRepo.findAll();
    }

    @GetMapping("{idConta}")
    public Conta findById(@PathVariable Long idConta) {
        //return calculaValoresConta(contaRepo.findById(idConta).get());
        return contaRepo.findById(idConta).get();
    }

    @PostMapping
    public ResponseEntity<Conta> save(@RequestBody Conta conta) throws ValidationException {

        if (conta.getDescricao() == null || conta.getDescricao().equals("")) {
            throw new ValidationException("A descrição da conta é obrigatória");
        }

        Conta saved = contaRepo.save(conta);
        return new ResponseEntity<Conta>(saved, HttpStatus.CREATED);
    }

}
