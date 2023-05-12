package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContaController {
    
    UtilController util;
    Conta contaAtiva;
    
    public ContaController(UtilController util){
        this.util = util;
    }
    
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("contas", util.getContas());

        return "index";
    }

    @GetMapping("")
    public String add(Model model) {
        model.addAttribute("conta", new Conta());
        return "addConta";
    }
    
    @GetMapping("selectConta")
    public String add(@RequestParam Long idConta, Model model) {
        util.setIdContaAtiva(idConta);
        contaAtiva = util.getContaPorId(util.getIdContaAtiva());
        model.addAttribute("conta", contaAtiva);
        model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));

        return "ratear";
    }

    @PostMapping("saveConta")
    public String save(Conta conta, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Conta> responseEntityPerson = restTemplate.postForEntity(
                    util.getBackendURL() + "/divide-ai-backend/conta",
                    conta, Conta.class
            );
            assertNotNull(responseEntityPerson.getBody());
            
            util.setIdContaAtiva(new Long(responseEntityPerson.getBody().getIdConta()));
            contaAtiva = util.getContaPorId(util.getIdContaAtiva());
            model.addAttribute("conta", conta);
            model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
            model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta()))); 
            model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));
            
            //model.addAttribute("sucess", "Sucess!");
            return "ratear";
        } catch (Exception e) {
            Pattern compile = Pattern.compile("message\":\"(.*)\",");
            Matcher m = compile.matcher(e.getMessage());
            m.find();
            model.addAttribute("error", m.group(1));
            model.addAttribute("conta", conta);
            return "addConta";
        } 
    }
    
}
