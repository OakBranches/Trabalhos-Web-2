package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.services.spec.IPropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/proposta")
public class PropostaController {
    @Autowired
    private IPropostaService service;

    @GetMapping("/create")
    public String formClient(Model model) {
        model.addAttribute("Proposta", new Proposta());
        return "formProposta";
    }

    @GetMapping("/edit/{id}")
    public String formEditClient(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("Proposta", service.buscaPorId(id));
        return "formProposta";
    }


    @PostMapping("/edit")
    public String editClient(@Valid Proposta Proposta, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "formProposta";
        }

        service.salvar(Proposta);
        attr.addFlashAttribute("sucess", "client.edit.sucess");

        return "redirect:/Proposta/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Proposta Proposta, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "formProposta";
        }

        service.salvar(Proposta);
        attr.addFlashAttribute("sucess", "client.create.sucess");

        return "redirect:/Proposta/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("propostas",service.buscarTodos());
        return "PainelCliente";
    }
}
