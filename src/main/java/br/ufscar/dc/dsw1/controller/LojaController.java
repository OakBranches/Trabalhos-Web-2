package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
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
    private BCryptPasswordEncoder encoder;

//    @GetMapping("/create")
//    public String formLoja(Model model) {
//        model.addAttribute("Loja", new Loja());
//        return "adm/formLoja";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String formEditLoja(ModelMap model, @PathVariable("id") Long id) {
//        model.addAttribute("Loja", service.buscaPorId(id));
//        return "adm/formLoja";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteLoja(ModelMap model, @PathVariable("id") Long id, RedirectAttributes attr) {
//
//        if (service.LojaTemCarros(id)) {
//            attr.addFlashAttribute("fail", "loja.delete.fail");
//            System.out.println("Não foi possivel deletar");
//        } else {
//            service.excluirPorId(id);
//            attr.addFlashAttribute("sucess", "loja.delete.sucess");
//        }
//        return "redirect:/loja/listar";
//    }
//
//    @PostMapping("/salvar")
//    public String salvar(@ModelAttribute("Loja") @Valid Loja Loja, BindingResult result, RedirectAttributes attr) {
//
//        System.out.println(Loja.getId());
//        if(!cnpjIsValid(Loja.getCnpj(), Loja.getId())){
//            result.rejectValue("cnpj", "Unique.loja.CNPJ");
//        }
//        if(!service.emailIsValid(Loja)){
//            result.rejectValue("email", "Unique.usuario.email");
//        }
//
//        if (result.hasErrors()) {
//            attr.addFlashAttribute("fail", "loja.create.fail");
//            System.out.println("Não foi possivel criar");
//            return "adm/formLoja";
//        }
//
//        System.out.println("senha = " + Loja.getSenha());
//
//        Loja.setSenha(encoder.encode(Loja.getSenha()));
//        Loja.setPapel(2);
//        service.salvar(Loja);
//        attr.addFlashAttribute("sucess", "loja.create.sucess");
//
//        return "redirect:/loja/listar";
//    }
//
//    @GetMapping("/listar")
//    public String listar(ModelMap model) {
//        model.addAttribute("lojas",service.buscarTodasLojas());
//        return "adm/listarLojas";
//    }
//
//    public boolean cnpjIsValid(String cnpj, Long Id){
//        Usuario loja = service.buscaPorCnpj(cnpj);
//        return loja == null ||  Objects.equals(loja.getId(), Id);
//    }
}
