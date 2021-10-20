package br.ufscar.dc.dsw1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carro")
public class CarroController {

    @GetMapping("/list")
    public String index(Model model) {
        return "index";
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
