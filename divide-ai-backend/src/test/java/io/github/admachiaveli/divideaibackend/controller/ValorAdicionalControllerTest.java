package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.controller.ValorAdicionalController;
import io.github.admachiaveli.divideaibackend.model.ValorAdicional;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.repo.TipoValorRepo;
import io.github.admachiaveli.divideaibackend.repo.ValorAdicionalRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ValorAdicionalControllerTest {

    @Mock
    private ContaRepo contaRepo;

    @Mock
    private ValorAdicionalRepo valorAdicionalRepo;

    @Mock
    private TipoValorRepo tipoValorRepo;

    @InjectMocks
    private ValorAdicionalController controller;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void naoDeveSalvarValorAdicionalSemInformarConta(){
        ValorAdicional valor = new ValorAdicional();
        valor.setDescricao("Testes unitários");
        valor.setValor(new BigDecimal(10));
        valor.setIdTipoValor(1);
        
        try {
            controller.save(null, valor);
        } catch (ValidationException ex) {
            Assert.assertEquals("A conta é obrigatória", ex.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarValorAdicionalSemDescricao(){
        ValorAdicional valor = new ValorAdicional();
        valor.setValor(new BigDecimal(10));
        valor.setIdTipoValor(1);
        
        try {
            controller.save(new Long(1), valor);
        } catch (ValidationException ex) {
            Assert.assertEquals("A descrição do valor é obrigatória", ex.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarValorAdicionalSemValor(){
        ValorAdicional valor = new ValorAdicional();
        valor.setDescricao("Testes unitários");
        valor.setIdTipoValor(1);
        
        try {
            controller.save(new Long(1), valor);
        } catch (ValidationException ex) {
            Assert.assertEquals("O valor é obrigatório", ex.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarValorAdicionalComValorZero(){
        ValorAdicional valor = new ValorAdicional();
        valor.setDescricao("Testes unitários");
        valor.setValor(new BigDecimal(0));
        valor.setIdTipoValor(1);
        
        try {
            controller.save(new Long(1), valor);
        } catch (ValidationException ex) {
            Assert.assertEquals("O valor deve ser diferente de zero", ex.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarValorAdicionalSemTipoValor(){
        ValorAdicional valor = new ValorAdicional();
        valor.setDescricao("Testes unitários");
        valor.setValor(new BigDecimal(10));
        valor.setIdTipoValor(0);
        
        try {
            controller.save(new Long(1), valor);
        } catch (ValidationException ex) {
            Assert.assertEquals("O tipo de valor é obrigatório", ex.getMessage());
        }
    }
    
}
