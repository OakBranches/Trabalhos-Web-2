package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/create")
    public String formClient(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formCliente";
    }

    @GetMapping("/edit/{id}")
    public String formEditClient(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("cliente", service.buscaPorId(id));
        return "formCliente";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {

        if (service.clienteTemPropostas(id)) {
            System.out.println("não foi possivel deletar");
            attr.addFlashAttribute("fail", "client.delete.fail");
        } else {
            service.excluirPorId(id);
            System.out.println("foi possivel deletar");
            attr.addFlashAttribute("sucess", "client.delete.sucess");
        }
        return "redirect:/cliente/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

        if(!service.emailIsValid(cliente)){
            result.rejectValue("email", "Unique.usuario.email");
        }
        if(!cpfIsValid(cliente.getCpf(), cliente.getId())){
            result.rejectValue("cpf", "Unique.cliente.CPF");
        }

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            attr.addFlashAttribute("fail", "client.create.fail");
            return "formCliente";
        }

        System.out.println("senha = " + cliente.getSenha());

        cliente.setSenha(encoder.encode(cliente.getSenha()));

        service.salvar(cliente);
        attr.addFlashAttribute("sucess", "client.create.sucess");

        return "redirect:/cliente/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model, Locale locale) {
        model.addAttribute("clientes",service.buscarTodosClientes());
        model.addAttribute("locale", locale);
        return "listarClientes";
    }

    public boolean cpfIsValid(String cpf, Long Id){
        Usuario loja = service.buscaPorCpf(cpf);
        return loja == null || Objects.equals(loja.getId(), Id);
    }
}
