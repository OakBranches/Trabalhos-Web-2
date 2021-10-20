package br.ufscar.dc.dsw1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Hello {

    @GetMapping("/hello")
    public String index(Model model) {
        return "index";
    }

}