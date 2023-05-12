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

import io.github.admachiaveli.divideaibackend.model.TipoValor;
import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import io.github.admachiaveli.divideaibackend.repo.TipoValorRepo;
import io.github.admachiaveli.divideaibackend.repo.ValorAdicionalRepo;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/valor-adicional")
public class ValorAdicionalController {

    @Autowired
    private ContaRepo contaRepo;

    @Autowired
    private ValorAdicionalRepo valorAdicionalRepo;

    @Autowired
    private TipoValorRepo tipoValorRepo;

    @GetMapping
    public List<ValorAdicional> findAll() {
        return valorAdicionalRepo.findAll();
    }

    @GetMapping("{idConta}")
    public List<ValorAdicional> findAll(@PathVariable Long idConta) {
        return valorAdicionalRepo.findAllByContaIdConta(new Long(idConta));
    }

    @PostMapping("{idConta}")
    public ResponseEntity<ValorAdicional> save(@PathVariable Long idConta, @RequestBody ValorAdicional valorAdicional) throws ValidationException {

        if (idConta == null || idConta == 0) {
            throw new ValidationException("A conta é obrigatória");
        }
        if (valorAdicional.getDescricao() == null || valorAdicional.getDescricao().equals("")) {
            throw new ValidationException("A descrição do valor é obrigatória");
        }
        if (valorAdicional.getValor() == null) {
            throw new ValidationException("O valor é obrigatório");
        }
        if (valorAdicional.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new ValidationException("O valor deve ser diferente de zero");
        }
        if (valorAdicional.getIdTipoValor() == 0) {
            throw new ValidationException("O tipo de valor é obrigatório");
        }

        //Busca as entidades para salvar
        Conta conta = contaRepo.findById(new Long(idConta)).get();
        TipoValor tipoValor = tipoValorRepo.findById(new Long(valorAdicional.getIdTipoValor())).get();

        //Verifica se o tipo de valor informado é válido
        if (tipoValor == null) {
            throw new ValidationException("Ocorreu um erro ao salvar o tipo de valor escolhido, por favor tente novamente");
        } else {
            valorAdicional.setTipoValor(tipoValor);
        }

        if (conta == null) {
            throw new ValidationException("Ocorreu um erro ao salvar, por favor tente novamente");
        } else {
            valorAdicional.setConta(conta);
        }

        ValorAdicional saved = valorAdicionalRepo.save(valorAdicional);
        return new ResponseEntity<ValorAdicional>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idValorAdicional}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idValorAdicional) {
        valorAdicionalRepo.deleteById(idValorAdicional);
    }
}
