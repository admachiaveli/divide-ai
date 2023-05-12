package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Conta;
import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ContaRepo;
import io.github.admachiaveli.divideaibackend.repo.ParticipanteRepo;
import io.github.admachiaveli.divideaibackend.utils.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ParticipanteControllerTest {

    @Mock
    private ParticipanteRepo participanteRepo;

    @Mock
    private ContaRepo contaRepo;

    @InjectMocks
    private ParticipanteController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarParticipanteSemConta() {
        Participante part = new Participante();
        part.setNome("Participante via teste unitário");

        try {
            controller.save(null, part);
        } catch (ValidationException ex) {
            Assert.assertEquals("A conta associada ao participante é obrigatória", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarParticipanteComContaInvalida() {
        Participante part = new Participante();
        part.setNome("Participante via teste unitário");

        Mockito.when(controller.contaExiste(new Long(1))).thenReturn(Boolean.TRUE);

        try {
            controller.save(new Long(10), part);
        } catch (ValidationException ex) {
            Assert.assertEquals("A conta associada ao participante não existe", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarParticipanteSemNome() {
        Participante part = new Participante();
        part.setNome("");

        Mockito.when(controller.contaExiste(new Long(1))).thenReturn(Boolean.TRUE);

        try {
            controller.save(new Long(1), part);
        } catch (ValidationException ex) {
            Assert.assertEquals("O nome do participante é obrigatório", ex.getMessage());
        }
    }

    @Test
    public void deveSalvarParticipanteComSucesso() throws ValidationException {
        Participante part = new Participante();
        part.setNome("Participante via teste unitário");

        Conta conta = new Conta();
        conta.setIdConta(new Long(1));
        conta.setDescricao("Conta teste unitário");
        conta.setSubTotal(new BigDecimal(0));
        conta.setTotal(new BigDecimal(0));

        Mockito.when(controller.contaExiste(new Long(1))).thenReturn(Boolean.TRUE);
        Mockito.when(contaRepo.findById(anyLong())).thenReturn(Optional.ofNullable(conta));

        controller.save(new Long(1), part);
    }
}
