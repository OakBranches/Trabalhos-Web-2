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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

//import javax.jms.MessageProducer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

@Controller
@RequestMapping("/proposta")
public class PropostaController {
    @Autowired
    private IPropostaService service;

    @Autowired
    private ICarroService carservice;

    @Autowired
    private IClienteService cliservice;

    @Autowired
    private JavaMailSender emailservice;

    @GetMapping("/create/{id}")
    public String formClient(@PathVariable("id") Long id, Model model, RedirectAttributes attr) {
        Carro carro = carservice.buscaPorId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();

        if (carro ==null || cliservice.clienteTemPropostasAbertasNoCarro(user.getId(),carro.getId())){
            attr.addFlashAttribute("fail", "proposta.existe");
            System.out.println("Não foi possivel criar, pois o cliente já tem uma proposta aberta para esse veiculo ou o veiculo não existe");
            return "redirect:/home";
        }

        PropostaForm proposta =  new PropostaForm();
        proposta.setCli_id(user.getId().intValue());
        proposta.setCar_id(id.intValue());
        model.addAttribute("proposta",proposta);
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
        Proposta proposta = service.buscaPorId(id);
        if(setStatus(proposta, 1, attr)){
            attr.addFlashAttribute("sucess", "proposta.accept.sucess");
            if(!acceptEmail(proposta, form)) attr.addFlashAttribute("fail", "email.fail");
        }
        return "redirect:/carro/listar";
    }

    @PostMapping("/reject/{id}")
    public String reject(@ModelAttribute("form") FormReject form, @PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {

        Proposta proposta = service.buscaPorId(id);
        if(setStatus(proposta, 2, attr)){
            attr.addFlashAttribute("sucess", "proposta.reject.sucess");
            if(!rejectEmail(proposta, form)) attr.addFlashAttribute("fail", "email.fail");
        }

        return "redirect:/carro/listar";
    }

    public boolean setStatus(Proposta proposta, int status, RedirectAttributes attr) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        if (!Objects.equals(proposta.getCarro().getLoja().getId(), user.getId())){
            attr.addFlashAttribute("sucess", "proposta.reject.fail");
            return false;
        }
        proposta.setStatus(status);
        service.salvar(proposta);
        return true;
    }


    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("proposta") @Valid PropostaForm propostaForm, BindingResult result, RedirectAttributes attr, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("proposta", propostaForm);
            attr.addFlashAttribute("fail", "proposta.create.fail");
            System.out.println("Houve um erro ao registrar a proposta");
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
    public String listar(ModelMap model, Locale locale) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        model.addAttribute("propostas",service.buscarTodosPorClienteId(user.getId()));
        model.addAttribute("locale", locale);

        return "PainelCliente";
    }


    boolean acceptEmail(Proposta proposta, FormAccept form){
        String subject, body;
        subject = String.format("Olá %s,sua proposta foi aceita!",proposta.getCliente().getNome());
        body = String.format("Olá %s,sua proposta de R$ %f no carro de modelo %s foi aceita!\nA reunião será %s no link %s\nO vendedor deixou a mensagem %s",proposta.getCliente().getNome(), proposta.getValor().floatValue(), proposta.getCarro().getModelo(), form.getData(), form.getLink(), form.getMensagem());
        return sendEmail(proposta, subject, body);
    }

    boolean rejectEmail(Proposta proposta, FormReject form){
        String subject, body;
        subject = String.format("Olá %s,sua proposta foi recusada :(",proposta.getCliente().getNome());
        body = String.format("Olá %s,sua proposta de R$ %f no carro de modelo %s foi recusada.\n",proposta.getCliente().getNome(), proposta.getValor(), proposta.getCarro().getModelo());
        if (form.getMensagem() != null && !form.getMensagem().isBlank()){
            body += "\nO vendedor deixou a mensagem: "+form.getMensagem();
        }
        if (form.getCondPag() != null && !form.getCondPag().isBlank()){
            body += "\nO vendedor deixou uma contraProposta: "+form.getCondPag();
        }
        if (form.getValor() != null && !form.getValor().isBlank()){
            body += "\nO vendedor fez uma contraProposta de R$ "+form.getValor();
        }
        return sendEmail(proposta, subject, body);
    }


    boolean sendEmail(Proposta proposta, String subject, String body){
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(proposta.getCliente().getEmail());
            msg.setFrom("Katchau");
            msg.setSubject(subject);
            msg.setText(body);

            emailservice.send(msg);
        } catch(Exception e){
            System.out.println("não foi possivel enviar o email");
            return false;
        }
        return true;
    }

}
