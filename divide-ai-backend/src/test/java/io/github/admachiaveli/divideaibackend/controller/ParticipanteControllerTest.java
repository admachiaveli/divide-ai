package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.controller.ParticipanteController;
import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class ParticipanteControllerTest {
    
//    @Mock
//    private ParticipanteRepo participanteRepo;
    
    @Mock
    private ContaRepo contaRepo;
    
    @Mock 
    private Conta conta;
    
    @InjectMocks
    private ParticipanteController controller;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void naoDeveSalvarParticipanteSemConta(){
        Participante part = new Participante();
        part.setNome("Participante via teste unitário");
        
        try {
            controller.save(null, part);
        } catch (ValidationException ex) {
            Assert.assertEquals("A conta associada ao participante é obrigatória", ex.getMessage());
        }
    }
    
    @Test
    public void naoDeveSalvarParticipanteComContaInvalida(){
        Participante part = new Participante();
        part.setNome("Participante via teste unitário");
        
        try {
            controller.save(new Long(10000), part);
        } catch (ValidationException ex) {
            Assert.assertEquals("A conta associada ao participante não existe", ex.getMessage());
        }
    }
    
//    @Test
//    public void naoDeveSalvarParticipanteSemNome() {
//        conta.setIdConta(new Long(1));
//        conta.setDescricao("Teste unitário");
//        
//        Participante part = new Participante();
//        part.setNome("");
//        
//        Mockito.when(controller.contaExiste(new Long(1))).thenReturn(true);
//        
//        try {
//            controller.save(conta.getIdConta(), part);
//        } catch (ValidationException ex) {
//            Assert.assertEquals("O nome do participante é obrigatório", ex.getMessage());
//        }
//    }
    
    @Test
    public void deveSalvarParticipanteComSucesso() throws ValidationException {

    }
}
