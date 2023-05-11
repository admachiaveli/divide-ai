package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.controller.ContaController;
import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContaControllerTest {
    
    @Mock
    private ContaRepo contaRepo;
    
    @InjectMocks
    private ContaController controller;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void NaoDeveSalvarContaSemDescricao() {
        Conta conta = new Conta();
        try {
            controller.save(conta);
        } catch (ValidationException ex) {
            Assert.assertEquals("A descrição da conta é obrigatória", ex.getMessage());
        }
    }
    
    @Test
    public void deveSalvarContaComSucesso() throws ValidationException{
        Conta conta = new Conta();
        conta.setDescricao("Testes unitários");
        controller.save(conta);
    }
}
