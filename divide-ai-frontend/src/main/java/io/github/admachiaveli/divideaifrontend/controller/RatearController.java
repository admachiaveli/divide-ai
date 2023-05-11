package io.github.admachiaveli.divideaifrontend.controller;

import io.github.admachiaveli.divideaifrontend.model.Conta;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RatearController {
    
    private UtilController util;
    private Conta contaAtiva;
    
    
    public RatearController(UtilController util){
        this.util = util;
    }
    
    @GetMapping("ratear")
    public String indexRatear(Model model, Locale locale) {
        contaAtiva = util.getContaPorId(util.getIdContaAtiva());
        model.addAttribute("conta", contaAtiva);
        model.addAttribute("participantes", util.getParticipantesPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("itens", util.getItensPorConta(new Long(contaAtiva.getIdConta())));
        model.addAttribute("adicionais", util.getValoresAdicionaisPorConta(new Long(contaAtiva.getIdConta())));
        if (util.getVERSION().startsWith("build")) {
            model.addAttribute("version", util.getVERSION());
        }
        return "ratear";
    }
    
//    @GetMapping("ratear")
//    public String indexRatear(@PathVariable Long idConta, Model model) {
//        model.addAttribute("conta", util.getConta());
//        model.addAttribute("participantes", util.getParticipantesPorConta(new Long(util.getConta().getIdConta())));
//        model.addAttribute("itens", util.getItens());
//        if (util.getVERSION().startsWith("build")) {
//            model.addAttribute("version", util.getVERSION());
//        }
//        return "ratear";
//    }
    
    public Conta getContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(Conta contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

}
