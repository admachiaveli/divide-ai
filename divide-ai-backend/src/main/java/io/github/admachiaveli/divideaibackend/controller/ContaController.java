package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.model.Item;
import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
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
import io.github.admachiaveli.divideaibackend.repo.ItemRepo;
import io.github.admachiaveli.divideaibackend.repo.ValorAdicionalRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value ="/conta")
public class ContaController {

	@Autowired
	private ContaRepo contaRepo;

        @Autowired
	private ItemRepo itemRepo;
        
        @Autowired
	private ValorAdicionalRepo valorAdicionalRepo;
        
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
                
                if(conta.getDescricao() == null || conta.getDescricao().equals("")) {
			throw new ValidationException("A descrição da conta é obrigatória");
		}
            
		Conta saved = contaRepo.save(conta);
		return new ResponseEntity<Conta>(saved, HttpStatus.CREATED);
	}
        
//        public Conta calculaValoresConta(Conta conta){
//            
//            //List<Item> itens = itemRepo.findAllByParticipanteContaIdConta(conta.getIdConta());
//            BigDecimal subTotal = conta.getSubTotal();
//            BigDecimal total = conta.getSubTotal();
//            
////            if(!itens.isEmpty()){
////                subTotal = itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
////                total = itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
////            }
//            
//            //verifica se existem valores adicionais
//            //Verifica valores em 'espécie'
//            List<ValorAdicional> valoresAdicionais = valorAdicionalRepo.findAllByContaIdContaAndTipoValorSigla(conta.getIdConta(), "R$");
//            if(!valoresAdicionais.isEmpty()){
//               total = total.add(valoresAdicionais.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add)); 
//            }
//            
//            //Verifica valores em porcentagem
//            /*
//            Para simplificar, valores em porcentagem são considerados "taxas"
//            por isso o cálculo incide sobre o valor dos itens (Subtotal), não sendo levado em contas possíveis descontos
//            */
//            List<ValorAdicional> valoresAdicionaisPercent = valorAdicionalRepo.findAllByContaIdContaAndTipoValorSigla(conta.getIdConta(), "%");
//            if(!valoresAdicionaisPercent.isEmpty()){
//                BigDecimal totalPercent = valoresAdicionaisPercent.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
//                total = total.add(subTotal.multiply(totalPercent).divide(new BigDecimal(100))).setScale(2);
//            }
//            
//            //conta.setSubTotal(subTotal);
//            conta.setTotal(total);
//            return conta;
//        }
        
}
