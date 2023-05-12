package io.github.admachiaveli.divideaibackend.controller;

import io.github.admachiaveli.divideaibackend.model.Item;
import io.github.admachiaveli.divideaibackend.model.Participante;
import io.github.admachiaveli.divideaibackend.repo.ItemRepo;
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

public class ItemControllerTest {

    @Mock
    private ItemRepo itemRepo;

    @Mock
    private ParticipanteRepo participanteRepo;

    @InjectMocks
    private ItemController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarItemSemDescricao() {
        Item item = new Item();
        item.setValor(new BigDecimal(10));

        try {
            controller.save(new Long(1), item);
        } catch (ValidationException ex) {
            Assert.assertEquals("A descrição do item é obrigatória", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarItemComValorNulo() {
        Item item = new Item();
        item.setDescricao("Teste unitário");
        item.setValor(null);

        try {
            controller.save(new Long(1), item);
        } catch (ValidationException ex) {
            Assert.assertEquals("O valor do item é obrigatório", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarItemComValorIgualZero() {
        Item item = new Item();
        item.setDescricao("Teste unitário");
        item.setValor(new BigDecimal(0));

        try {
            controller.save(new Long(1), item);
        } catch (ValidationException ex) {
            Assert.assertEquals("O valor do item deve ser maior que zero", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarItemComValorNegativo() {
        Item item = new Item();
        item.setDescricao("Teste unitário");
        item.setValor(new BigDecimal(-10));

        try {
            controller.save(new Long(1), item);
        } catch (ValidationException ex) {
            Assert.assertEquals("O valor do item deve ser maior que zero", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarItemSemInformarUmParticipante() {
        Item item = new Item();
        item.setDescricao("Teste unitário");
        item.setValor(new BigDecimal(10));

        try {
            controller.save(null, item);
        } catch (ValidationException ex) {
            Assert.assertEquals("O participante é obrigatório", ex.getMessage());
        }
    }

    @Test
    public void deveSalvarItemComSucesso() throws ValidationException {
        Item item = new Item();
        item.setDescricao("Teste unitário");
        item.setValor(new BigDecimal(10));

        Participante part = new Participante();
        part.setIdParticipante(new Long(1));
        part.setNome("Participante via testes unitários");

        Mockito.when(participanteRepo.findById(anyLong())).thenReturn(Optional.ofNullable(part));

        controller.save(new Long(1), item);
    }

}
