package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    ILojaService service;

    @GetMapping("/list")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        List<Carro> carros = service.buscaPorId(user.getId()).getCarros();
        model.addAttribute("carros", carros);
        return "PainelLoja";
    }

    @GetMapping("/create")
    public String formsCar(Model model) {
        return "formCarro";
    }

    @PostMapping("/create")
    public String createCar(Model model) {
        return "erros";
    }

}
