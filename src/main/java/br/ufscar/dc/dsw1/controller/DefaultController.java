package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class DefaultController {
    @Autowired
    ICarroService service;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("carros", service.buscarTodos());
        return "index";
    }

    @GetMapping("/painel")
    public String painel(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();

        switch (user.getPapel()){
            case 2:
                return "redirect:/carro/listar";
            case 3:
                return "redirect:/proposta/listar";
            case 1:
                return "redirect:/loja/listar";
            default:
                return "redirect:/home";
        }
    }
}
