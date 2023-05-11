package io.github.admachiaveli.divideaibackend.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "valor_adicional")
public class ValorAdicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor_adicional")
    private Long idValorAdicional;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "valor", precision = 20, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "categoria", nullable = false)
    private String categoria;
    
    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "id_tipo_valor")
    private TipoValor tipoValor;

    @Transient
    private int idTipoValor;
    
    @Transient
    private String valorFormatado;

    public ValorAdicional() {
    }

    public Long getIdValorAdicional() {
        return idValorAdicional;
    }

    public void setIdValorAdicional(Long idValorAdicional) {
        this.idValorAdicional = idValorAdicional;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String nome) {
        this.descricao = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoValor getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(TipoValor tipoValor) {
        this.tipoValor = tipoValor;
    }

    public int getIdTipoValor() {
        if(idTipoValor == 0){
            if(tipoValor != null){
                return tipoValor.getIdTipoValor().intValue();
            }
        }
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
    
    public String getValorFormatado() {
        if(valor != null){
            if(tipoValor != null){
                DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
                symbols.setCurrencySymbol(""); 
                formatter.setDecimalFormatSymbols(symbols);
                String valorFormatado =  formatter.format(valor);
                return tipoValor.getIdTipoValor() == 1 ? "R$ " + valorFormatado : valorFormatado + " %";
            }
        }

        return valorFormatado;
    }

    public void setValorFormatado(String valorFormatado) {
        this.valorFormatado = valorFormatado;
    }
}
