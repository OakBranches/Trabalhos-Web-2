package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.json.simple.JSONObject;

@CrossOrigin
@RestController
public class ClienteController {

    @Autowired
    private IClienteService service;

    @Autowired
    private BCryptPasswordEncoder encoder;


//    @PostMapping("/salvar")
//    public String salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
//
//        if(!service.emailIsValid(cliente)){
//            result.rejectValue("email", "Unique.usuario.email");
//        }
//        if(!cpfIsValid(cliente.getCpf(), cliente.getId())){
//            result.rejectValue("cpf", "Unique.cliente.CPF");
//        }
//
//        if (result.hasErrors()) {
//            System.out.println(result.getAllErrors());
//            attr.addFlashAttribute("fail", "client.create.fail");
//            return "adm/formCliente";
//        }
//
//        System.out.println("senha = " + cliente.getSenha());
//
//        cliente.setSenha(encoder.encode(cliente.getSenha()));
//        cliente.setPapel(3);
//        service.salvar(cliente);
//        attr.addFlashAttribute("sucess", "client.create.sucess");
//
//        return "redirect:/cliente/listar";
//    }
//
//    @GetMapping("/listar")
//    public String listar(ModelMap model, Locale locale) {
//        model.addAttribute("clientes",service.buscarTodosClientes());
//        model.addAttribute("locale", locale);
//        return "adm/listarClientes";
//    }
//
    public boolean cpfIsValid(String cpf, Long Id){
        Usuario loja = service.buscaPorCpf(cpf);
        return cpf.length() == 14 && (loja == null || Objects.equals(loja.getId(), Id));
    }

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private void parse(@Valid Cliente cliente, JSONObject map) throws ParseException {

        Object id = map.get("id");
        if (id != null){
            System.out.println(id);
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            } else {
                cliente.setId((Long) id);
            }
        }

        cliente.setPapel(3);
        cliente.setCpf((String) map.get("cpf"));
        cliente.setSexo((String) map.get("sexo"));
        cliente.setEmail((String) map.get("email"));
        cliente.setNome((String) map.get("nome"));
        cliente.setSenha((String) map.get("senha"));
        cliente.setNascimento(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("nascimento")));

        if (!cpfIsValid(cliente.getCpf(), cliente.getId()) || !service.emailIsValid(cliente)){
            throw new ParseException("Cliente já cadastrado", 1);
        }

    }


    @GetMapping(path = "/clientes")
    public ResponseEntity<List<Cliente>> lista() {
        List<Cliente> lista = service.buscarTodosClientes();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> lista(@PathVariable("id") long id) {
        Cliente cliente = service.buscaPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(path = "/clientes")
    @ResponseBody
    public ResponseEntity<Cliente> cria(@RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = new Cliente();
                String senha = (String) json.get("senha");
                json.replace("id", null);
                if (senha != null)
                    json.replace("senha", encoder.encode(senha));
                parse(cliente, json);
                System.out.println(cliente);
                service.salvar(cliente);
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = service.buscaPorId(id);
                if (cliente == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    // Campos imutáveis
                    json.replace("senha", cliente.getSenha());
                    json.replace("email", cliente.getEmail());
                    json.replace("id", id);

                    parse(cliente, json);
                    service.salvar(cliente);
                    return ResponseEntity.ok(cliente);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/clientes/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

        Cliente Cliente = service.buscaPorId(id);

        if (Cliente == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.excluirPorId(id);
            return ResponseEntity.noContent().build();
        }
    }
}
