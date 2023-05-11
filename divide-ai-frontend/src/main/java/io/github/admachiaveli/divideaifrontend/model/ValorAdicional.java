package io.github.admachiaveli.divideaifrontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValorAdicional {

    private int idValorAdicional;
    private String descricao;
    @NumberFormat(style=NumberFormat.Style.NUMBER)
    private BigDecimal valor;
    private int idTipoValor;
    private String categoria;
    private String valorFormatado;

    public int getIdValorAdicional() {
        return idValorAdicional;
    }

    public void setIdValorAdicional(int idValorAdicional) {
        this.idValorAdicional = idValorAdicional;
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

    public int getIdTipoValor() {
        return idTipoValor;
    }

    public void setIdTipoValor(int idTipoValor) {
        this.idTipoValor = idTipoValor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Boolean getePercentual() {
        return idTipoValor == 2;
    }
    
    public String getValorFormatado() {
        return valorFormatado;
    }

    public void setValorFormatado(String valorFormatado) {
        this.valorFormatado = valorFormatado;
    }

}
