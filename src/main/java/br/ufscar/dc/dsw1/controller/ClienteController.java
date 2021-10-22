package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String deleteClient(@PathVariable("id") Long id, ModelMap model) {

        if (service.clienteTemPropostas(id)) {
            model.addAttribute("fail", "client.delete.fail");
        } else {
            service.excluirPorId(id);
            model.addAttribute("sucess", "client.delete.sucess");
        }
        System.out.println("redor");
        return "redirect:/cliente/listar";
    }

    @PostMapping("/edit")
    public String editClient(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "formCliente";
        }

        service.salvar(cliente);
        attr.addFlashAttribute("sucess", "client.edit.sucess");

        return "redirect:/cliente/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "formCliente";
        }

        System.out.println("senha = " + cliente.getSenha());

        cliente.setSenha(encoder.encode(cliente.getSenha()));

        service.salvar(cliente);
        attr.addFlashAttribute("sucess", "client.create.sucess");

        return "redirect:/cliente/listar";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("clientes",service.buscarTodos());
        return "listarClientes";
    }

}
