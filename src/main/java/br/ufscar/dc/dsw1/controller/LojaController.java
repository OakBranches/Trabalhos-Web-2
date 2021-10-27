package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import br.ufscar.dc.dsw1.services.spec.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    private ILojaService service;

    @Autowired
    private IUsuarioService usrservice;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/create")
    public String formLoja(Model model) {
        model.addAttribute("Loja", new Loja());
        return "formLoja";
    }

    @GetMapping("/edit/{id}")
    public String formEditLoja(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("Loja", service.buscaPorId(id));
        return "formLoja";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoja(ModelMap model, @PathVariable("id") Long id) {

        if (service.LojaTemCarros(id)) {
            model.addAttribute("fail", "client.delete.fail");
        } else {
            service.excluirPorId(id);
            model.addAttribute("sucess", "client.delete.sucess");
        }
        return "redirect:/loja/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("Loja") @Valid Loja Loja, BindingResult result, RedirectAttributes attr) {

        System.out.println(Loja.getId());
        if(!cnpjIsValid(Loja.getCnpj(), Loja.getId())){
            result.rejectValue("cnpj", "Unique.loja.CNPJ");
        }
        if(!emailIsValid(Loja.getEmail(), Loja.getId())){
            result.rejectValue("email", "Unique.usuario.email");
        }

        if (result.hasErrors()) {

            return "formLoja";
        }

        System.out.println("senha = " + Loja.getSenha());

        Loja.setSenha(encoder.encode(Loja.getSenha()));

        service.salvar(Loja);
        attr.addFlashAttribute("sucess", "client.create.sucess");

        return "redirect:/loja/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("lojas",service.buscarTodos());
        return "listarLojas";
    }
    public boolean isValid(Usuario loja, Long Id) {
        boolean teste = Objects.equals(loja.getId(), Id);
        return loja == null || teste;
    }
    public boolean cnpjIsValid(String cnpj, Long Id){
        return  isValid(service.buscaPorCnpj(cnpj), Id);
    }
    public boolean emailIsValid(String email, Long Id){
        return  isValid(usrservice.buscaPorEmail(email), Id);
    }
}
