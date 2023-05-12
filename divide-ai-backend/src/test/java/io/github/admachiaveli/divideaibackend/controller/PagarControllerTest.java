/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import io.github.admachiaveli.divideaibackend.repo.TipoValorRepo;
import io.github.admachiaveli.divideaibackend.repo.ValorAdicionalRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PagarControllerTest {

    @Mock
    private ContaRepo contaRepo;

    @Mock
    private ParticipanteRepo participanteRepo;
    
    @Mock
    private Conta conta;
    
    @InjectMocks
    private PagarController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveRetornarDivisaoCorreta() throws ValidationException {

        //Cria conta com os valores escolhidos
        Conta conta = new Conta();
        conta.setIdConta(new Long(1));
        conta.setDescricao("Conta via teste unitário");
        conta.setSubTotal(new BigDecimal(50.00));
        conta.setTotal(new BigDecimal(38.00));

        //Cria participantes com os valores escolhidos
        Participante part1 = new Participante();
        part1.setNome("Eu");
        part1.setIdParticipante(new Long(1));
        part1.setValorTotal(new BigDecimal(42.00));
        part1.setQtdItens(2);

        Participante part2 = new Participante();
        part2.setNome("Amigo");
        part2.setIdParticipante(new Long(2));
        part2.setValorTotal(new BigDecimal(8.00));
        part2.setQtdItens(1);

        Mockito.when(contaRepo.findById(anyLong())).thenReturn(Optional.ofNullable(conta));

        Participante part1Result = controller.calculaValorPagarParticipante(part1, conta);
        Participante part2Result = controller.calculaValorPagarParticipante(part2, conta);
        
        //Verificando os resultados
        Assert.assertEquals(new BigDecimal(31.92).doubleValue(), part1Result.getValorPagar().doubleValue(), 0.0001);
        Assert.assertEquals(new BigDecimal(6.08).doubleValue(), part2Result.getValorPagar().doubleValue(), 0.0001);
    
    }

}
