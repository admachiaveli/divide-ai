package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import io.github.admachiaveli.divideaifrontend.model.Participante;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ParticipanteController {

    @Autowired
    UtilController util;

    private Conta contaAtiva;

    @GetMapping("participantes")
    public String index(Model model) {
        model.addAttribute("participantes", util.getParticipantes());
        if (util.getVERSION().startsWith("build")) {
            model.addAttribute("version", util.getVERSION());
        }
        return "indexParticipante";
    }

    @GetMapping("addParticipante")
    public String add(Model model) {
        model.addAttribute("participante", new Participante());
        return "addParticipante";
    }

    @PostMapping("saveParticipante")
    public String save(Participante participante, Model model) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    util.getBackendURL() + "/divide-ai-backend/participante/" + util.getIdContaAtiva(),
                    participante, Object.class
            );
            model.addAttribute("sucess", "Participante salvo!");

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
            model.addAttribute("participante", participante);
            return "addParticipante";
        }
    }

    @GetMapping("deleteParticipante/{idParticipante}")
    public String delete(@PathVariable Long idParticipante, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(util.getBackendURL() + "/divide-ai-backend/participante/" + idParticipante);
        model.addAttribute("success", "Participante excluído!");

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
