package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.dao.IClienteDAO;
import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.forms.FormAccept;
import br.ufscar.dc.dsw1.forms.FormReject;
import br.ufscar.dc.dsw1.forms.PropostaForm;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import br.ufscar.dc.dsw1.services.spec.IPropostaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping("/proposta")
public class PropostaController {
    @Autowired
    private IPropostaService service;

    @Autowired
    private ICarroService carservice;

    @Autowired
    private IClienteService cliservice;

    @GetMapping("/create/{id}")
    public String formClient(@PathVariable("id") Long id, Model model, RedirectAttributes attr) {
        Carro carro = carservice.buscaPorId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();

        if (cliservice.clienteTemPropostasAbertasNoCarro(user.getId(),carro.getId())){
            attr.addFlashAttribute("fail", "proposta.existe");
            return "redirect:/home";
        }

        Cliente cli = cliservice.buscaPorId(user.getId());
        Proposta proposta =  new Proposta();
        proposta.setCliente(cli);
        proposta.setCarro(carro);
        System.out.println("\n" + proposta.toString());
        model.addAttribute("proposta",proposta);
        System.out.println(proposta.toString());
        return "formProposta";
    }

    @GetMapping("/accept/{id}")
    public String acceptForm(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("id", id);
        model.addAttribute("form", new FormAccept());
        return "formAceito";
    }

    @GetMapping("/reject/{id}")
    public String rejectForm(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("id", id);
        model.addAttribute("form", new FormReject());
        return "formRecusa";
    }

    @PostMapping("/accept/{id}")
    public String accept(@ModelAttribute("form") @Valid FormAccept form, BindingResult result, RedirectAttributes attr,  @PathVariable("id") Long id, ModelMap model) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("fail", "proposta.accept.fail");
            System.out.println("Campos devem ser preenchidos");
            return "formAceito";
        }
        attr.addFlashAttribute("sucess", "proposta.accept.sucess");
        return setStatus(id, 1);
    }

    @PostMapping("/reject/{id}")
    public String reject(@ModelAttribute("form") FormReject form, @PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {


        attr.addFlashAttribute("sucess", "proposta.reject.sucess");
        return setStatus(id, 2);
    }

    public String setStatus(@PathVariable("id") Long id,int status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        Proposta proposta = service.buscaPorId(id);
        if (!Objects.equals(proposta.getCarro().getLoja().getId(), user.getId())){
            return "redirect:/carro/listar";
        }
        proposta.setStatus(status);
        service.salvar(proposta);
        return "redirect:/carro/listar";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid PropostaForm propostaForm, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("sucess", "proposta.create.fail");
            return "formProposta";
        }
        Proposta proposta = new Proposta();
        proposta.setCondPag(propostaForm.getCondPag());
        proposta.setData(new Date());
        proposta.setValor(BigDecimal.valueOf(propostaForm.getValor()));
        Cliente cli = cliservice.buscaPorId((long)propostaForm.getCli_id());
        Carro car = carservice.buscaPorId((long) propostaForm.getCar_id());
        proposta.setCliente(cli);
        proposta.setCarro(car);
        service.salvar(proposta);
        attr.addFlashAttribute("sucess", "proposta.create.sucess");

        return "redirect:/proposta/listar";
    }


    @GetMapping("/listar")
    public String listar(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        model.addAttribute("propostas",service.buscarTodosPorClienteId(user.getId()));

        return "PainelCliente";
    }


}
