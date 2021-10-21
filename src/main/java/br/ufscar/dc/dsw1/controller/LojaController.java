package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    private ILojaService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/create")
    public String formClient(Model model) {
        model.addAttribute("Loja", new Loja());
        return "formLoja";
    }

    @GetMapping("/edit/{id}")
    public String formEditClient(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("Loja", service.buscaPorId(id));
        return "formLoja";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, ModelMap model) {

        if (service.LojaTemCarros(id)) {
            model.addAttribute("fail", "client.delete.fail");
        } else {
            service.excluirPorId(id);
            model.addAttribute("sucess", "client.delete.sucess");
        }
        return "redirect:/Loja/listar";
    }

    @PostMapping("/edit")
    public String editClient(@Valid Loja Loja, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "formLoja";
        }

        service.salvar(Loja);
        attr.addFlashAttribute("sucess", "client.edit.sucess");

        return "redirect:/Loja/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Loja Loja, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "formLoja";
        }

        System.out.println("senha = " + Loja.getSenha());

        Loja.setSenha(encoder.encode(Loja.getSenha()));

        service.salvar(Loja);
        attr.addFlashAttribute("sucess", "client.create.sucess");

        return "redirect:/Loja/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("editoras",service.buscarTodos());
        return "listarLojas";
    }




}
