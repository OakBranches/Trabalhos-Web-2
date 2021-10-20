package br.ufscar.dc.dsw1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loja")
public class LojaController {

    @GetMapping("/c")
    public String index(Model model) {
        return "index";
    }

}
