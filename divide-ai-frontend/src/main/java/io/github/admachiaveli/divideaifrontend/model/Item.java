package io.github.admachiaveli.divideaifrontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private int idItem;
    private String descricao;
    @NumberFormat(style=Style.NUMBER)
    private BigDecimal valor;
    private int idParticipante;

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }
    
    @Override
    public String toString() {
        return "Todo [idItem=" + idItem + ", descricao=" + descricao + ", valor=" + valor + "]";
    }

}
