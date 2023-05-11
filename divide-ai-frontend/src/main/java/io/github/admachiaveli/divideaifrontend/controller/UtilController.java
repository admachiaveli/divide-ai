package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import io.github.admachiaveli.divideaifrontend.model.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import io.github.admachiaveli.divideaifrontend.model.Participante;
import io.github.admachiaveli.divideaifrontend.model.TipoValor;
import io.github.admachiaveli.divideaifrontend.model.ValorAdicional;
import org.springframework.stereotype.Component;

@Component
public class UtilController {

    @Value("${backend.host}")
    private String BACKEND_HOST;

    @Value("${backend.port}")
    private String BACKEND_PORT;

    @Value("${app.version}")
    private String VERSION;
    
    private Long idContaAtiva;

    public String getBackendURL() {
        return "http://" + getBACKEND_HOST() + ":" + getBACKEND_PORT();
    }

    @SuppressWarnings("unchecked")
    public List<Participante> getParticipantes() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/participante", List.class);
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItens() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/item", List.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<Participante> getParticipantesPorConta(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/participante/" + id, List.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<Item> getItensPorConta(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/item/" + id, List.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<ValorAdicional> getValoresAdicionaisPorConta(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/valor-adicional/" + id, List.class);
    }
  
    @SuppressWarnings("unchecked")
    public List<Conta> getContas() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/conta", List.class);
    }
    
    @SuppressWarnings("unchecked")
    public Conta getContaPorId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/conta/" + id, Conta.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<TipoValor> getTiposValor() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/tipo-valor/", List.class);
    }
   
        @SuppressWarnings("unchecked")
    public List<Participante> getParticipantesPorContaPagar(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/divide-ai-backend/pagar/" + id, List.class);
    }
    
    public String getBACKEND_HOST() {
        return BACKEND_HOST;
    }

    public void setBACKEND_HOST(String BACKEND_HOST) {
        this.BACKEND_HOST = BACKEND_HOST;
    }

    public String getBACKEND_PORT() {
        return BACKEND_PORT;
    }

    public void setBACKEND_PORT(String BACKEND_PORT) {
        this.BACKEND_PORT = BACKEND_PORT;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }
    
    
    public Long getIdContaAtiva() {
        return idContaAtiva;
    }

    public void setIdContaAtiva(Long idContaAtiva) {
        this.idContaAtiva = idContaAtiva;
    }
}
