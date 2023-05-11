package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.model.Item;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.repo.ItemRepo;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import io.github.admachiaveli.divideaibackend.repo.ValorAdicionalRepo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value ="/pagar")
public class PagarController {

	@Autowired
	private ParticipanteRepo participanteRepo;
        
        @Autowired
	private ContaRepo contaRepo;

        @Autowired
	private ItemRepo itemRepo;
        
        @Autowired
	private ValorAdicionalRepo valorAdicionalRepo;
       
        @GetMapping("{idConta}")
	public List<Participante> findAll(@PathVariable Long idConta) {
                ratear(idConta);
		return participanteRepo.findAllByContaIdConta(idConta);
	}
        
        public void ratear(Long idConta){
            Conta conta = contaRepo.findById(idConta).get();
            
            conta = calculaValoresConta(conta);
            
            //Trazendo lista de participantes
            List<Participante> participantes = participanteRepo.findAllByContaIdConta(idConta);
            
            //Calcula o que cada participante deve pagar proporcionalmente ao valor dos itens que pediu
            for(Participante part : participantes){
                part.setPercentual(part.getValorTotal().multiply(new BigDecimal(100)).divide(conta.getSubTotal(), 2, RoundingMode.HALF_UP));
                part.setValorPagar(part.getPercentual().multiply(conta.getTotal()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
            }
            
           participanteRepo.saveAll(participantes);
            
        }
        
        //Temporário, melhorar para atualizar sempre o valor
        public Conta calculaValoresConta(Conta conta){
            
            List<Item> itens = itemRepo.findAllByParticipanteContaIdConta(conta.getIdConta());
            BigDecimal subTotal = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            
            if(!itens.isEmpty()){
                subTotal = itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
                total = itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            
            //verifica se existem valores adicionais
            //Verifica valores em 'espécie'
            List<ValorAdicional> valoresAdicionais = valorAdicionalRepo.findAllByContaIdContaAndTipoValorSigla(conta.getIdConta(), "R$");
            if(!valoresAdicionais.isEmpty()){
               total = total.add(valoresAdicionais.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add)); 
            }
            
            //Verifica valores em porcentagem
            /*
            Para simplificar, valores em porcentagem são considerados "taxas"
            por isso o cálculo incide sobre o valor dos itens (Subtotal), não sendo levado em contas possíveis descontos
            */
            List<ValorAdicional> valoresAdicionaisPercent = valorAdicionalRepo.findAllByContaIdContaAndTipoValorSigla(conta.getIdConta(), "%");
            if(!valoresAdicionaisPercent.isEmpty()){
                BigDecimal totalPercent = valoresAdicionaisPercent.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
                total = total.add(subTotal.multiply(totalPercent).divide(new BigDecimal(100))).setScale(2);
            }
            
            conta.setSubTotal(subTotal);
            conta.setTotal(total);
            return conta;
        }
        
}
