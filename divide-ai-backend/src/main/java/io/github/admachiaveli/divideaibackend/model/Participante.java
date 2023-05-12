package io.github.admachiaveli.divideaibackend.model;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "participantes")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante")
    private Long idParticipante;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor_total", precision = 20, scale = 2, nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "valor_pagar", precision = 20, scale = 2, nullable = false)
    private BigDecimal valorPagar = BigDecimal.ZERO;

    @Column(name = "qtd_itens")
    private int qtdItens = 0;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.REMOVE)
    private Set<Item> itens;

    @Transient
    private BigDecimal percentual;

    public Participante() {
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
    }

    public BigDecimal getValorTotal() {
        return itens != null && !itens.isEmpty() ? itens.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getQtdItens() {
        return itens != null && !itens.isEmpty() ? itens.size() : 0;
    }

    public void setQtdItens(int qtdItens) {
        this.qtdItens = qtdItens;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

}
