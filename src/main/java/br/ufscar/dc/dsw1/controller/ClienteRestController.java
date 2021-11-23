package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class ClienteRestController extends AbstractRestController{

    @Autowired
    private Validator validator;

    @Autowired
    private IClienteService cliservice;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @SuppressWarnings("unchecked")
    private void cliParse(@Valid Cliente cliente, JSONObject map) throws ParseException, ValidationException {

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
        cliente.setTelefone((String) map.get("telefone"));
        cliente.setSenha((String) map.get("senha"));
        cliente.setNascimento(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("nascimento")));

        if (!cpfIsValid(cliente.getCpf(), cliente.getId()) || !cliservice.emailIsValid(cliente)){
            throw new ValidationException();
        }

        if (!validator.validate(cliente).isEmpty()){
            throw new ParseException(validator.validate(cliente).toString(), 1);
        }

    }

    public boolean cpfIsValid(String cpf, Long Id){
        Usuario loja = cliservice.buscaPorCpf(cpf);
        return cpf.length() == 14 && (loja == null || Objects.equals(loja.getId(), Id));
    }


    @GetMapping(path = "/clientes")
    public ResponseEntity<List<Cliente>> lista() {
        List<Cliente> lista = cliservice.buscarTodosClientes();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> listaCli(@PathVariable("id") long id) {
        Cliente cliente = cliservice.buscaPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(path = "/clientes")
    @ResponseBody
    public ResponseEntity<Cliente> criaCli(@RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = new Cliente();
                String senha = (String) json.get("senha");
                json.replace("id", null);
                if (senha != null)
                    json.replace("senha", encoder.encode(senha));
                cliParse(cliente, json);
                System.out.println(cliente);
                cliservice.salvar(cliente);
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.badRequest().body(null);
            }

        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> atualizaCli(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = cliservice.buscaPorId(id);
                if (cliente == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    // Campos imutáveis
                    json.replace("senha", cliente.getSenha());
                    json.replace("email", cliente.getEmail());
                    json.replace("id", id);

                    cliParse(cliente, json);
                    cliservice.salvar(cliente);
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
    public ResponseEntity<Boolean> removeCli(@PathVariable("id") long id) {

        Cliente cliente = cliservice.buscaPorId(id);

        if (cliente == null ) {
            return ResponseEntity.notFound().build();
        } else if (cliservice.clienteTemPropostas(cliente.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else{
            cliservice.excluirPorId(id);
            return ResponseEntity.noContent().build();
        }
    }

}
