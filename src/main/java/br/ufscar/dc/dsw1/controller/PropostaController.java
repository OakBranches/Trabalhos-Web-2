package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.dao.IClienteDAO;
import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.domain.Usuario;
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
    public String formClient(@PathVariable("id") Long id, Model model) {
        Carro carro = carservice.buscaPorId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();

        if (cliservice.clienteTemPropostasAbertasNoCarro(user.getId(),carro.getId())){
            return "redirect:/home";
        }

        Cliente cli = cliservice.buscaPorId(user.getId());
        Proposta proposta =  new Proposta();
        proposta.setCliente(cli);
        proposta.setCarro(carro);

        model.addAttribute("proposta",proposta);
        return "formProposta";
    }

    @GetMapping("/edit/{id}")
    public String formEditClient(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("Proposta", service.buscaPorId(id));
        return "formProposta";
    }

    @GetMapping("/accept/{id}")
    public String acceptForm(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("id", id);
        return "formAceito";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable("id") Long id, ModelMap model) {
        return setStatus(id, model, 2);
    }

    public String setStatus(@PathVariable("id") Long id, ModelMap model,int status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        Proposta proposta = service.buscaPorId(id);
        if (!Objects.equals(proposta.getCarro().getLoja().getId(), user.getId())){
            return "redirect:/proposta/list";
        }
        proposta.setStatus(status);
        service.salvar(proposta);
        return "redirect:/proposta/list";
    }

    @PostMapping("/edit")
    public String editClient(@Valid Proposta Proposta, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "formProposta";
        }

        service.salvar(Proposta);
        attr.addFlashAttribute("sucess", "client.edit.sucess");

        return "redirect:/proposta/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid PropostaForm propostaForm, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "formProposta";
        }
        Proposta proposta = new Proposta();
        proposta.setCondPag(propostaForm.getCondPag());
        proposta.setData(propostaForm.getData());
        proposta.setValor(BigDecimal.valueOf(propostaForm.getValor()));
        Cliente cli = cliservice.buscaPorId((long)propostaForm.getCli_id());
        Carro car = carservice.buscaPorId((long) propostaForm.getCar_id());
        proposta.setCliente(cli);
        proposta.setCarro(car);
        service.salvar(proposta);
        attr.addFlashAttribute("sucess", "client.create.sucess");

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
