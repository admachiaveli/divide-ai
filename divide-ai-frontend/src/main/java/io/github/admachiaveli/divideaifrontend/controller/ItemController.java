package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import io.github.admachiaveli.divideaifrontend.model.Item;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ItemController {

    @Autowired
    UtilController util;

    private Conta contaAtiva;

    @GetMapping("itens")
    public String index(Model model) {
        model.addAttribute("itens", util.getItens());
        if (util.getVERSION().startsWith("build")) {
            model.addAttribute("version", util.getVERSION());
        }
        return "indexItem";
    }

    @GetMapping("addItem")
    public String add(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("participantes", util.getParticipantesPorConta(util.getIdContaAtiva()));
        return "addItem";
    }

    @PostMapping("saveItem")
    public String save(Item item, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    util.getBackendURL() + "/divide-ai-backend/item/" + util.getIdContaAtiva(),
                    item, Object.class
            );
            model.addAttribute("sucess", "Item salvo!");

            contaAtiva = util.getContaPorId(util.getIdContaAtiva());
            model.addAttribute("conta", contaAtiva);
            model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
            model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta())));
            model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));

            return "ratear";
        } catch (Exception e) {
            Pattern compile = Pattern.compile("message\":\"(.*)\",");
            Matcher m = compile.matcher(e.getMessage());
            m.find();
            model.addAttribute("error", m.group(1));
            model.addAttribute("item", item);
            return "addItem";
        } finally {
            model.addAttribute("participantes", util.getParticipantesPorConta(util.getIdContaAtiva()));
        }
    }

    @GetMapping("deleteItem/{idItem}")
    public String delete(@PathVariable Long idItem, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(util.getBackendURL() + "/divide-ai-backend/item/" + idItem);
        model.addAttribute("success", "Item excluído!");

        contaAtiva = util.getContaPorId(util.getIdContaAtiva());
        model.addAttribute("conta", contaAtiva);
        model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));
        return "ratear";
    }

    public Conta getContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(Conta contaAtiva) {
        this.contaAtiva = contaAtiva;
    }
    
}
