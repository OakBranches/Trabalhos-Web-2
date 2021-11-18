package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.FileEntity;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import br.ufscar.dc.dsw1.services.spec.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DefaultController {
    @Autowired
    ICarroService service;

    @Autowired
    IFileService fileservice;


//    @GetMapping("/home")
//    public String home(Model model){
//        model.addAttribute("carros", service.buscarTodos());
//        return "index";
//    }
//
//    @GetMapping("/painel")
//    public String painel(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
//
//        switch (user.getPapel()){
//            case 2:
//                return "redirect:/carro/listar";
//            case 3:
//                return "redirect:/proposta/listar";
//            case 1:
//                return "redirect:/loja/listar";
//            default:
//                return "redirect:/home";
//        }
//    }
//
//    @GetMapping(value = "/download/{id}")
//    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
//        FileEntity entity = fileservice.buscarPorId(id);
//
//        // set content type
//        response.setContentType(entity.getType());
//
//        try {
//            // copies all bytes to an output stream
//            response.getOutputStream().write(entity.getData());
//
//            // flushes output stream
//            response.getOutputStream().flush();
//        } catch (IOException e) {
//            System.out.println("Error :- " + e.getMessage());
//        }
//    }
}
