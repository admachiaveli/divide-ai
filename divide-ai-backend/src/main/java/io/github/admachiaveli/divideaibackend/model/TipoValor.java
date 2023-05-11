package io.github.admachiaveli.divideaibackend.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_valor")
public class TipoValor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_valor")
    private Long idTipoValor;

    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    @Column(name = "sigla", nullable = false)
    private String sigla;

    
    public TipoValor() {
    }

    public Long getIdTipoValor() {
        return idTipoValor;
    }

    public void setIdTipoValor(Long idTipoValor) {
        this.idTipoValor = idTipoValor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String nome) {
        this.descricao = nome;
    }
    
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
