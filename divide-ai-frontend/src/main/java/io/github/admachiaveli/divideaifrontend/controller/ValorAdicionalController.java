package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import io.github.admachiaveli.divideaifrontend.model.ValorAdicional;
import java.math.BigDecimal;
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
public class ValorAdicionalController {

    @Autowired
    UtilController util;

    private Conta contaAtiva;

    @GetMapping("addValorAdicional")
    public String add(Model model) {
        ValorAdicional vlr = new ValorAdicional();
        vlr.setCategoria("taxa");
        model.addAttribute("adicional", vlr);
        model.addAttribute("tiposValor", util.getTiposValor());
        return "addValorAdicional";
    }

    @PostMapping("saveValorAdicional")
    public String save(ValorAdicional adicional, Model model) {
        try {

            //Verifica se é taxa ou desconto e seta o número positivo ou negativo
            if (adicional.getValor() != null && adicional.getValor().compareTo(BigDecimal.ZERO) > 0) {
                adicional.setValor(adicional.getCategoria().equals("taxa") == true ? adicional.getValor().abs() : adicional.getValor().negate());
            }

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    util.getBackendURL() + "/divide-ai-backend/valor-adicional/" + util.getIdContaAtiva(),
                    adicional, Object.class
            );
            model.addAttribute("sucess", "Valor salvo!");

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
            model.addAttribute("adicional", adicional);
            return "addValorAdicional";
        } finally {
            model.addAttribute("tiposValor", util.getTiposValor());
        }
    }

    public Conta getContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(Conta contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    @GetMapping("deleteValorAdicional/{idValorAdicional}")
    public String delete(@PathVariable Long idValorAdicional, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(util.getBackendURL() + "/divide-ai-backend/valor-adicional/" + idValorAdicional);
        model.addAttribute("success", "Valor excluído!");

        contaAtiva = util.getContaPorId(util.getIdContaAtiva());
        model.addAttribute("conta", contaAtiva);
        model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));

        return "ratear";
    }
}
