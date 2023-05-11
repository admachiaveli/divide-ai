package io.github.admachiaveli.divideaifrontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoValor {
    
    private int idTipoValor;
    private String descricao;
    private String sigla;
    
    public TipoValor() {
    }

    public int getIdTipoValor() {
        return idTipoValor;
    }

    public void setIdTipoValor(int idTipoValor) {
        this.idTipoValor = idTipoValor;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
}
