package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/pagar")
public class PagarController {

    @Autowired
    private ParticipanteRepo participanteRepo;

    @Autowired
    private ContaRepo contaRepo;

    @GetMapping("{idConta}")
    public List<Participante> findAll(@PathVariable Long idConta) {
        ratear(idConta);
        return participanteRepo.findAllByContaIdConta(idConta);
    }

    public void ratear(Long idConta) {
        Conta conta = contaRepo.findById(idConta).get();
        conta.atualizarValores();

        //Trazendo lista de participantes
        List<Participante> participantes = participanteRepo.findAllByContaIdConta(idConta);

        //Calcula o que cada participante deve pagar proporcionalmente ao valor dos itens que pediu
        for (Participante part : participantes) {
            part = calculaValorPagarParticipante(part, conta);
        }

        participanteRepo.saveAll(participantes);
    }

    public Participante calculaValorPagarParticipante(Participante part, Conta conta) {

        part.setPercentual(part.getValorTotal().multiply(new BigDecimal(100)).divide(conta.getSubTotal(), 2, RoundingMode.HALF_UP));
        part.setValorPagar(part.getPercentual().multiply(conta.getTotal()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        part.setValorTotal(part.getValorTotal());
        part.setQtdItens(part.getQtdItens());

        return part;
    }

}
