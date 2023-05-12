package io.github.admachiaveli.divideaibackend.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Long idConta;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE)
    private Set<Participante> participantes;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE)
    private Set<ValorAdicional> valoresAdicionais;

    @Transient
    private BigDecimal subTotal = new BigDecimal(0);

    @Transient
    private BigDecimal total = new BigDecimal(0);

    public Conta() {
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public BigDecimal getSubTotal() {
        return participantes != null && !participantes.isEmpty()
                ? participantes.stream().map(participante -> participante.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotal() {

        if (participantes != null && !participantes.isEmpty()) {
            BigDecimal subotal = participantes.stream().map(participante -> participante.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = subotal;

            //verifica se existem valores adicionais
            if (valoresAdicionais != null && !valoresAdicionais.isEmpty()) {

                //Verifica valores em 'espécie'
                List<ValorAdicional> valoresAdicionaisReal = valoresAdicionais
                        .stream()
                        .filter(valor -> valor.getTipoValor().getSigla().equals("R$") == true)
                        .collect(Collectors.toList());
                if (!valoresAdicionaisReal.isEmpty()) {
                    total = total.add(valoresAdicionais.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add));
                }

                //Verifica valores em porcentagem
                /*
                Para simplificar, valores em porcentagem são considerados "taxas"
                por isso o cálculo incide sobre o valor dos itens (Subtotal), não sendo levado em contas possíveis descontos
                 */
                List<ValorAdicional> valoresAdicionaisPercent = valoresAdicionais
                        .stream()
                        .filter(valor -> valor.getTipoValor().getSigla().equals("%") == true)
                        .collect(Collectors.toList());
                if (!valoresAdicionaisPercent.isEmpty()) {
                    BigDecimal totalPercent = valoresAdicionaisPercent.stream().map(valor -> valor.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    total = total.add(subotal.multiply(totalPercent).divide(new BigDecimal(100))).setScale(2);
                }

                return total;
            }
            return total;
        } else {
            return BigDecimal.ZERO;
        }

    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
